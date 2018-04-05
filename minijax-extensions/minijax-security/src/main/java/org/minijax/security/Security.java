package org.minijax.security;

import static javax.ws.rs.core.HttpHeaders.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.CookieParam;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.mindrot.jbcrypt.BCrypt;
import org.minijax.MinijaxProperties;
import org.minijax.util.IdUtils;


/**
 * The Security class manages logging in and out of the application.
 *
 * There should be a new unique Security instance for each request.
 *
 * It wraps the User DAO to retrieve users.
 *
 * It uses BCrypt to check passwords.
 */
@Provider
@RequestScoped
public class Security<T extends SecurityUser> implements SecurityContext {
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    public static final String COOKIE_NAME = "a";
    private static final String COOKIE_PATH = "/";
    private static final String COOKIE_DOMAIN = "";
    private static final int COOKIE_MAX_AGE = 365 * 24 * 60 * 60;
    private static final boolean COOKIE_HTTP_ONLY = true;
    private final Class<SecurityUser> userClass;
    private final SecurityDao dao;
    private final String authorization;
    private final String cookie;
    private final UserSession session;
    private final SecurityUser user;


    @Inject
    @SuppressWarnings("unchecked")
    public Security(
            final SecurityDao dao,
            @Context final Configuration configuration,
            @HeaderParam(AUTHORIZATION) final String authorization,
            @CookieParam(COOKIE_NAME) final String cookie) {

        userClass = (Class<SecurityUser>) configuration.getProperty(MinijaxProperties.SECURITY_USER_CLASS);
        this.dao = dao;
        this.authorization = authorization;
        this.cookie = cookie;
        session = initUser();
        user = session != null ? session.getUser() : null;
    }


    public Class<SecurityUser> getUserClass() {
        return userClass;
    }


    /**
     * Returns the currently logged in user for this HTTP request.
     * Returns null if not logged in.
     *
     * @return the currently logged in user.
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getUserPrincipal() {
        return (T) user;
    }


    /**
     * Returns true if a user is logged in for this HTTP request.
     *
     * @return true if user is logged in; false otherwise.
     */
    public boolean isLoggedIn() {
        return getUserPrincipal() != null;
    }


    /**
     * Requires that the user is logged in.
     */
    public void requireLogin() {
        if (!isLoggedIn()) {
            throw new NotAuthorizedException(Response.status(Status.UNAUTHORIZED).build());
        }
    }


    /**
     * Requires a cookie session (prevents API access).
     */
    private void requireCookieSession() {
        if (session.getId() == null) {
            throw new ForbiddenException();
        }
    }


    /**
     * Returns the session token.
     *
     * @return The session token.
     */
    public String getSessionToken() {
        requireLogin();
        requireCookieSession();

        return session.getId().toString();
    }


    /**
     * Validates a session token to guard against a CSRF attack.
     *
     * @param token The session token.
     */
    void validateSession(final String token) {
        if (!Objects.equals(token, getSessionToken())) {
            throw new BadRequestException("Invalid session ID");
        }
    }


    /**
     * Logs in the user with email address and password.
     * Returns the user on success.
     *
     * @param email The user's email address.
     * @param password The user's plain text password.
     * @return the user details.
     */
    public NewCookie login(final String email, final String password) {
        final SecurityUser candidate = dao.findUserByEmail(userClass, email);
        if (candidate == null) {
            throw new BadRequestException("notfound");
        }

        if (candidate.getPasswordHash() == null) {
            throw new BadRequestException("invalid");
        }

        if (!BCrypt.checkpw(password, candidate.getPasswordHash())) {
            throw new BadRequestException("incorrect");
        }

        return loginAs(candidate);
    }


    /**
     * Logs in as another user.
     *
     * This is used for SSO such as Google authentication.
     *
     * @param candidate The candidate user account.
     * @return The session details.
     */
    public NewCookie loginAs(final SecurityUser candidate) {
        final UserSession newSession = new UserSession();
        newSession.setUser(candidate);
        dao.create(newSession);

        return createCookie(newSession.getId().toString(), COOKIE_MAX_AGE);
    }


    /**
     * Logs out the user.
     */
    public NewCookie logout() {
        if (session != null) {
            dao.purge(session);
        }

        return createCookie("", 0);
    }


    /**
     * Changes the current user's password.
     *
     * @param oldPassword The old password.
     * @param newPassword The new password.
     * @param confirmNewPassword The confirmed new password.
     */
    public void changePassword(final String oldPassword, final String newPassword, final String confirmNewPassword) {
        requireLogin();

        if (user.getPasswordHash() == null) {
            throw new BadRequestException("unset");
        }

        if (!BCrypt.checkpw(oldPassword, user.getPasswordHash())) {
            throw new BadRequestException("incorrect");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new BadRequestException("mismatch");
        }

        if (newPassword.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new BadRequestException("short");
        }

        user.setPassword(newPassword);
        dao.update(user);
    }


    /**
     * Handles a request for "Forgot Password".
     *
     * See this stackoverflow article for the general design:
     * http://stackoverflow.com/a/1102817/2051724
     *
     * @param user The user.
     * @return The reset code to be sent to the user.
     */
    public String forgotPassword(final SecurityUser user) {
        final PasswordChangeRequest pcr = new PasswordChangeRequest();
        pcr.setCode(UUID.randomUUID().toString());
        pcr.setUser(user);
        dao.create(pcr);
        return pcr.getCode();
    }


    /**
     * Handles a request for "Reset Password".
     *
     * See this stackoverflow article for the general design:
     * http://stackoverflow.com/a/1102817/2051724
     *
     * @param resetId The reset ID.
     * @param newPassword The new password.
     * @param confirmNewPassword The confirmed new password.
     */
    public NewCookie resetPassword(final String resetId, final String newPassword, final String confirmNewPassword) {
        final PasswordChangeRequest pcr = dao.findPasswordChangeRequest(resetId);
        if (pcr == null) {
            throw new NotFoundException();
        }

        final Instant expiration = pcr.getCreatedDateTime().plus(24, ChronoUnit.HOURS);
        if (Instant.now().isAfter(expiration)) {
            throw new BadRequestException("expired");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new BadRequestException("mismatch");
        }

        if (newPassword.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new BadRequestException("short");
        }

        final SecurityUser resetUser = dao.read(userClass, pcr.getUserId());
        if (resetUser == null) {
            throw new NotFoundException();
        }

        resetUser.setPassword(newPassword);
        dao.update(resetUser);
        dao.purge(pcr);
        return loginAs(resetUser);
    }


    /*
     * Private helper methods.
     */


    /**
     * Initializes the user object.
     *
     * First tries the session cache.
     *
     * Second tries remember-me cookies.
     *
     * @return The user object if available; null otherwise.
     */
    private UserSession initUser() {
        final UserSession apiUser = tryGetApiUser();
        if (apiUser != null) {
            return apiUser;
        }

        final UserSession rememberedUser = trySessionCookie();
        if (rememberedUser != null) {
            return rememberedUser;
        }

        // Otherwise, not logged in.
        return null;
    }


    /**
     * Tries to retrieve a user from API key in the Authorization header.
     *
     * @return A user from API key if available; null otherwise.
     */
    private UserSession tryGetApiUser() {
        final String authCode = AuthUtils.getUsername(authorization);
        if (authCode == null) {
            return null;
        }

        final ApiKey apiKey = dao.findApiKeyByValue(authCode);
        if (apiKey == null || apiKey.getDeletedDateTime() != null) {
            // Invalid API key
            return null;
        }

        final SecurityUser apiUser = dao.read(userClass, apiKey.getUserId());
        if (apiUser == null) {
            return null;
        }

        final UserSession apiSession = new UserSession();
        apiSession.setId(null);
        apiSession.setUser(apiUser);
        return apiSession;
    }


    /**
     * Tries to login with a session cookie.
     *
     * @return A user logged-in with session cookie; null otherwise.
     */
    private UserSession trySessionCookie() {
        if (cookie == null) {
            // No cookie.
            return null;
        }

        final UUID sessionId = IdUtils.tryParse(cookie);
        if (sessionId == null) {
            // Invalid UUID
            return null;
        }

        final UserSession rememberedSession = dao.read(UserSession.class, sessionId);
        if (rememberedSession == null) {
            // No remembered session.
            return null;
        }

        final SecurityUser sessionUser = dao.read(userClass, rememberedSession.getUserId());
        if (sessionUser == null) {
            // Invalid user - This can happen on database errors.
            return null;
        }

        // Successfully logged in.
        rememberedSession.setUser(sessionUser);
        return rememberedSession;
    }


    /**
     * Returns a new cookie with specified value.
     *
     * @param value The text value of the cookie.
     * @param maxAge The max age of the cookie.
     * @return The new cookie.
     */
    private NewCookie createCookie(final String value, final int maxAge) {
        return new NewCookie(COOKIE_NAME, value, COOKIE_PATH, COOKIE_DOMAIN, "", maxAge, false, COOKIE_HTTP_ONLY);
    }


    @Override
    public boolean isUserInRole(final String role) {
        return user != null && user.hasRole(role);
    }


    @Override
    public boolean isSecure() {
        return true;
    }


    @Override
    public String getAuthenticationScheme() {
        if (session == null) {
            // Not authenticated
            return null;
        }
        if (session.getId() == null) {
            // API key
            return SecurityContext.BASIC_AUTH;
        }
        return SecurityContext.FORM_AUTH;
    }
}
