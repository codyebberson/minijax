package org.minijax.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;

import org.minijax.cdi.MinijaxInjector;
import org.minijax.commons.MinijaxException;
import org.minijax.rs.uri.MinijaxUriBuilder;

public class MinijaxClient implements AutoCloseable, Client {
    private final HttpClient httpClient;
    private final MinijaxInjector injector;
    private final MinijaxClientProviders providers;
    private final List<Class<? extends MessageBodyReader<?>>> readers;
    private final List<Class<? extends MessageBodyWriter<?>>> writers;
    private final List<Class<? extends ExceptionMapper<?>>> exceptionMappers;

    public MinijaxClient() {
        this(HttpClient.newBuilder()
                .version(Version.HTTP_1_1)
                .followRedirects(Redirect.NORMAL)
                .build());
    }

    public MinijaxClient(final HttpClient httpClient) {
        this.httpClient = httpClient;
        injector = new MinijaxInjector();
        providers = new MinijaxClientProviders(this);
        readers = new ArrayList<>();
        writers = new ArrayList<>();
        exceptionMappers = new ArrayList<>();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public MinijaxInjector getInjector() {
        return injector;
    }

    public <T> T getResource(final Class<T> c) {
        return getInjector().getResource(c, null);
    }

    public MinijaxClientProviders getProviders() {
        return providers;
    }

    @Override
    public MinijaxClientWebTarget target(final String uri) {
        return new MinijaxClientWebTarget(this, new MinijaxUriBuilder().uri(uri));
    }

    @Override
    public MinijaxClientWebTarget target(final URI uri) {
        return new MinijaxClientWebTarget(this, new MinijaxUriBuilder().uri(uri));
    }

    @Override
    public MinijaxClientWebTarget target(final UriBuilder uriBuilder) {
        return new MinijaxClientWebTarget(this, (MinijaxUriBuilder) uriBuilder);
    }

    @Override
    public MinijaxClientWebTarget target(final Link link) {
        return new MinijaxClientWebTarget(this, new MinijaxUriBuilder().uri(link.getUri()));
    }

    @Override
    public MinijaxClient property(final String name, final Object value) {
        // Silent ignore
        return this;
    }

    @Override
    public MinijaxClient register(final Class<?> componentClass) {
        registerImpl(componentClass);
        return this;
    }

    @Override
    public MinijaxClient register(final Class<?> componentClass, final int priority) {
        registerImpl(componentClass);
        return this;
    }

    @Override
    public MinijaxClient register(final Class<?> componentClass, final Class<?>... contracts) {
        registerImpl(componentClass);
        return this;
    }

    @Override
    public MinijaxClient register(final Class<?> componentClass, final Map<Class<?>, Integer> contracts) {
        registerImpl(componentClass);
        return this;
    }

    @Override
    public MinijaxClient register(final Object component) {
        // Silent ignore
        return this;
    }

    @Override
    public MinijaxClient register(final Object component, final int priority) {
        // Silent ignore
        return this;
    }

    @Override
    public MinijaxClient register(final Object component, final Class<?>... contracts) {
        // Silent ignore
        return this;
    }

    @Override
    public MinijaxClient register(final Object component, final Map<Class<?>, Integer> contracts) {
        // Silent ignore
        return this;
    }

    @Override
    public void close() {
        // Nothing to do
    }

    @SuppressWarnings("unchecked")
    private void registerImpl(final Class<?> c) {
        registerFeature(c);

        if (MessageBodyReader.class.isAssignableFrom(c)) {
            readers.add((Class<MessageBodyReader<?>>) c);
        }

        if (MessageBodyWriter.class.isAssignableFrom(c)) {
            writers.add((Class<MessageBodyWriter<?>>) c);
        }

        if (ExceptionMapper.class.isAssignableFrom(c)) {
            exceptionMappers.add((Class<ExceptionMapper<?>>) c);
        }
    }

    @SuppressWarnings("unchecked")
    private void registerFeature(final Class<?> c) {
        if (!Feature.class.isAssignableFrom(c)) {
            return;
        }

        final Class<? extends Feature> featureClass = (Class<? extends Feature>) c;
        try {
            getResource(featureClass).configure(new MinijaxFeatureContext(this));
        } catch (final Exception ex) {
            throw new MinijaxException(ex);
        }
    }

    /*
     * Unsupported
     */

    @Override
    public Configuration getConfiguration() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MinijaxClientInvocationBuilder invocation(final Link link) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SSLContext getSslContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        throw new UnsupportedOperationException();
    }

    /*
     * Protected accessors.
     */

    List<Class<? extends MessageBodyReader<?>>> getReaders() {
        return readers;
    }

    List<Class<? extends MessageBodyWriter<?>>> getWriters() {
        return writers;
    }

    List<Class<? extends ExceptionMapper<?>>> getExceptionMappers() {
        return exceptionMappers;
    }
}
