package org.minijax.gplus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.minijax.security.Security;
import org.minijax.security.SecurityUser;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Person;

/**
 * The GoogleService class provides helpers for connecting and interacting with Google services.
 */
@Provider
@RequestScoped
public class GooglePlusService {
    private static final String APP_NAME = "Ajibot";
    private static final List<String> SCOPES = Collections.unmodifiableList(Arrays.asList(
            PlusScopes.PLUS_ME, PlusScopes.USERINFO_EMAIL, PlusScopes.USERINFO_PROFILE));

    @Context
    private UriInfo uriInfo;

    @Inject
    private Security<SecurityUser> security;

    @Inject
    private GoogleClientSecrets clientSecrets;

    @Inject
    private JsonFactory jsonFactory;

    @Inject
    private DataStoreFactory dataStoreFactory;

    @Inject
    private HttpTransport httpTransport;


    public Person getProfile() throws IOException {
        final Plus plus = getPlus(getCredential((GooglePlusUser) security.getUserPrincipal()));
        return plus == null ? null : plus.people().get("me").execute();
    }


    /**
     * Returns a URL that can be used for login.
     *
     * @return A login URL.
     */
    public String getLoginUrl() throws IOException {
        return initializeFlow().newAuthorizationUrl()
                .setRedirectUri(getRedirectUrl())
                .build();
    }


    /**
     * Initializes a Google OAuth code flow.
     */
    public GoogleAuthorizationCodeFlow initializeFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, SCOPES)
                .setDataStoreFactory(dataStoreFactory).build();
    }


    /**
     * Returns the redirect URL.
     *
     * @return The redirect URL.
     */
    public String getRedirectUrl() throws MalformedURLException {
        // See https://console.developers.google.com
        // Go to "Credentials" page
        // Go to "Authorized redirect URIs" section
        final String currentUrl = uriInfo.getRequestUri().toString();
        final URL url = new URL(currentUrl);
        return url.getProtocol() + "://" + url.getAuthority() + "/googlecallback";
    }


    /**
     * Returns a Google+ client.
     *
     * WARNING:  Only call this once per "session" (either page view or worker iteration).
     * This is a heavy operation.  The calendar object can be reused for as long as the
     * access token is valid.
     *
     * @param credential The user credential.
     * @return A Google+ client.
     */
    public Plus getPlus(final Credential credential) {
        return new Plus.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APP_NAME)
                .build();
    }


    /**
     * Removes the user credential from the StoredCredential DataStore, and returns
     * it as a serialized string.
     *
     * This is used to move a credential from ajibot-web to ajibot-worker.
     * Credentials cannot be used simultaneously in multiple processes because it
     * breaks the refresh mechanics.
     *
     * @param userId The user ID.
     * @return The serialized credential.
     * @throws IOException
     */
    public String extractUserCredential(final UUID userId) throws IOException {
        final DataStore<StoredCredential> dataStore = getCredentialDataStore();

        final StoredCredential storedCredential = dataStore.get(userId.toString());
        if (storedCredential == null) {
            return null;
        }

        dataStore.delete(userId.toString());
        return Base64.getEncoder().encodeToString(IOUtils.serialize(storedCredential));
    }


    /**
     * Inserts the user credential into the StoredCredential DataStore.
     *
     * This is used to move a credential from ajibot-web to ajibot-worker.
     * Credentials cannot be used simultaneously in multiple processes because
     * it breaks the refresh mechanics.
     *
     * @param userId The user ID.
     * @param encodedCredential The serialized credential.
     * @throws IOException
     */
    public void insertUserCredential(final UUID userId, final String encodedCredential) throws IOException {
        final StoredCredential storedCredential = IOUtils.deserialize(Base64.getDecoder().decode(encodedCredential));
        getCredentialDataStore().set(userId.toString(), storedCredential);
    }


    /**
     * Returns the fully populated credential object for a user.
     *
     * @param user The user.
     * @return The credential object if available; null otherwise.
     */
    private Credential getCredential(final GooglePlusUser user) throws IOException {
        if (user.getGoogleCredentials() == null) {
            return null;
        }

        // Check if we have stored credentials using the Authorization Flow.
        // Note that we only check if there are stored credentials, but not if they are still valid.
        // The user may have revoked authorization, in which case we would need to go through the
        // authorization flow again, which this implementation does not handle.
        final GoogleAuthorizationCodeFlow authFlow = initializeFlow();

        // Insert the user credentials from the database into the google flow
        insertUserCredential(user.getId(), user.getGoogleCredentials());

        return authFlow.loadCredential(user.getId().toString());
    }


    /**
     * Returns the singleton StoredCredential DataStore.
     */
    private DataStore<StoredCredential> getCredentialDataStore() throws IOException {
        return StoredCredential.getDefaultDataStore(dataStoreFactory);
    }
}