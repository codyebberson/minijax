package org.minijax.cdi;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InjectorTest {
    private MinijaxInjector injector;

    @Before
    public void setUp() {
        injector = new MinijaxInjector();
    }

    @After
    public void tearDown() {
        injector.close();
    }

    static class A {
        @Inject
        B b;
    }

    static class B {
        final C c;

        @Inject
        B(final C c) {
            this.c = c;
        }
    }

    static class C {
        int x;
    }

    @Test
    public void testSimple() {
        final Provider<C> provider = injector.getProvider(C.class);
        assertNotNull(provider);

        final C instance = provider.get();
        assertNotNull(instance);

        final Provider<C> provider2 = injector.getProvider(C.class);
        assertNotNull(provider2);
        assertEquals(provider, provider2);

        final C instance2 = provider2.get();
        assertNotNull(instance2);
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testParamInject() {
        final Provider<B> provider = injector.getProvider(B.class);
        assertNotNull(provider);

        final B instance = provider.get();
        assertNotNull(instance);
        assertNotNull(instance.c);
    }

    @Test
    public void testFieldInject() {
        final Provider<A> provider = injector.getProvider(A.class);
        assertNotNull(provider);

        final A instance = provider.get();
        assertNotNull(instance);
        assertNotNull(instance.b);
        assertNotNull(instance.b.c);
    }

    static class ProviderParamInjection {
        final C c;
        @Inject ProviderParamInjection(final Provider<C> provider) {
            c = provider.get();
        }
    }

    @Test
    public void testProviderParamInjection() {
        final Provider<ProviderParamInjection> providerInjectionProvider = injector.getProvider(ProviderParamInjection.class);
        assertNotNull(providerInjectionProvider);

        final ProviderParamInjection providerInjection = providerInjectionProvider.get();
        assertNotNull(providerInjection);
        assertNotNull(providerInjection.c);
    }

    static class ProviderFieldInjection {
        @Inject Provider<C> provider;
    }

    @Test
    public void testProviderFieldInjection() {
        final Provider<ProviderFieldInjection> providerInjectionProvider = injector.getProvider(ProviderFieldInjection.class);
        assertNotNull(providerInjectionProvider);

        final ProviderFieldInjection providerInjection = providerInjectionProvider.get();
        assertNotNull(providerInjection);
        assertNotNull(providerInjection.provider);

        final C instance1 = providerInjection.provider.get();
        assertNotNull(instance1);

        final C instance2 = providerInjection.provider.get();
        assertNotNull(instance2);
        assertNotEquals(instance1, instance2);
    }

    @Singleton
    private static class MySingleton {
    }

    @Test
    public void testSingleton() {
        final Provider<MySingleton> provider = injector.getProvider(MySingleton.class);
        assertNotNull(provider);

        final MySingleton instance = provider.get();
        assertNotNull(instance);

        final Provider<MySingleton> provider2 = injector.getProvider(MySingleton.class);
        assertNotNull(provider2);
        assertEquals(provider, provider2);

        final MySingleton instance2 = provider2.get();
        assertNotNull(instance2);
        assertEquals(instance, instance2);
        assertTrue(instance == instance2);
    }

    static class NoValidConstructors {
        NoValidConstructors(final int x) { }
    }

    @Test(expected = InjectException.class)
    public void testNoValidConstructors() {
        injector.getProvider(NoValidConstructors.class);
    }

    static class MultipleInjectConstructors {
        @Inject MultipleInjectConstructors(final int x) { }
        @Inject MultipleInjectConstructors(final String x) { }
    }

    @Test(expected = InjectException.class)
    public void testMultipleInjectConstructors() {
        injector.getProvider(MultipleInjectConstructors.class);
    }

    static class ParamCircularDependencyA {
        @Inject ParamCircularDependencyA(final ParamCircularDependencyB b) { }
    }

    private static class ParamCircularDependencyB {
        @Inject ParamCircularDependencyB(final ParamCircularDependencyA a) { }
    }

    @Test(expected = InjectException.class)
    public void testParamCircularDependency() {
        injector.getProvider(ParamCircularDependencyA.class);
    }

    static class FieldCircularDependencyA {
        @Inject FieldCircularDependencyB b;
    }

    static class FieldCircularDependencyB {
        @Inject FieldCircularDependencyA a;
    }

    @Test(expected = InjectException.class)
    public void testFieldCircularDependency() {
        injector.getProvider(FieldCircularDependencyA.class);
    }

    static class ExplodingConstructor {
        ExplodingConstructor() {
            throw new IllegalStateException("boom");
        }
    }

    @Test(expected = InjectException.class)
    public void testExplodingConstructor() {
        injector.getResource(ExplodingConstructor.class);
    }

    private static class MultipleStrategies {
        @Context
        @HeaderParam("a")
        String a;
    }

    @Test(expected = InjectException.class)
    public void testMultipleStrategies() {
        injector.getResource(MultipleStrategies.class);
    }

    private static class MultipleNames {
        @Named("a")
        @HeaderParam("a")
        String a;
    }

    @Test(expected = InjectException.class)
    public void testMultipleNames() {
        injector.getResource(MultipleNames.class);
    }
}
