package org.minijax.test;

import java.net.URI;

import javax.ws.rs.client.WebTarget;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.minijax.Minijax;
import org.minijax.MinijaxRequestContext;

public class MinijaxTest {
    private static Minijax server;
    protected static boolean uniqueServerPerTest;

    @BeforeClass
    public static void setUpClass() {
        server = new Minijax();
    }

    @Before
    public void setUp() {
        if (server == null) {
            server = new Minijax();
        }
    }

    @After
    public void tearDown() {
        if (uniqueServerPerTest) {
            server.getInjector().close();
            server = null;
        }
    }

    @AfterClass
    public static void tearDownClass() {
        if (server != null) {
            server.getInjector().close();
            server = null;
        }
    }

    public static Minijax getServer() {
        return server;
    }

    public static void register(final Class<?> c) {
        server.register(c);
    }

    public static void register(final Object component) {
        server.register(component);
    }

    public static void register(final Object component, final Class<?>... contracts) {
        server.register(component, contracts);
    }

    public static void packages(final String... packageNames) {
        server.packages(packageNames);
    }

    public static WebTarget target(final String uri) {
        return new MinijaxWebTarget(server, URI.create(uri));
    }

    protected static MinijaxRequestContext createRequestContext() {
        return createRequestContext("GET", "/");
    }

    protected static MinijaxRequestContext createRequestContext(final String method, final String uri) {
        return new MinijaxRequestContext(
                getServer().getDefaultApplication(),
                new MockHttpServletRequest(method, URI.create(uri)),
                null);
    }
}
