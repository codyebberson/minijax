package org.minijax.client;

import java.util.Map;

import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.FeatureContext;

public class MinijaxFeatureContext implements FeatureContext {
    private final MinijaxClient client;

    public MinijaxFeatureContext(final MinijaxClient client) {
        this.client = client;
    }

    @Override
    public Configuration getConfiguration() {
        return client.getConfiguration();
    }

    @Override
    public MinijaxFeatureContext property(final String name, final Object value) {
        client.property(name, value);
        return this;
    }

    @Override
    public MinijaxFeatureContext register(final Class<?> componentClass) {
        client.register(componentClass);
        return this;
    }

    @Override
    public MinijaxFeatureContext register(final Class<?> componentClass, final int priority) {
        client.register(componentClass, priority);
        return this;
    }

    @Override
    public MinijaxFeatureContext register(final Class<?> componentClass, final Class<?>... contracts) {
        client.register(componentClass, contracts);
        return this;
    }

    @Override
    public MinijaxFeatureContext register(final Class<?> componentClass, final Map<Class<?>, Integer> contracts) {
        client.register(componentClass, contracts);
        return this;
    }

    @Override
    public MinijaxFeatureContext register(final Object component) {
        client.register(component);
        return this;
    }

    @Override
    public MinijaxFeatureContext register(final Object component, final int priority) {
        client.register(component, priority);
        return this;
    }

    @Override
    public MinijaxFeatureContext register(final Object component, final Class<?>... contracts) {
        client.register(component, contracts);
        return this;
    }

    @Override
    public MinijaxFeatureContext register(final Object component, final Map<Class<?>, Integer> contracts) {
        client.register(component, contracts);
        return this;
    }
}
