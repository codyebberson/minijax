package org.minijax.client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebTargetTest {
    private MinijaxClient client;

    @BeforeEach
    public void setUp() {
        client = new MinijaxClient();
    }

    public MinijaxClientWebTarget target(final String uri) {
        return client.target(uri);
    }

    @Test
    public void testCtor() {
        try (final MinijaxClient client = new MinijaxClient()) {
            final MinijaxClientWebTarget target = client.target("http://foo.com");
            assertEquals(client, target.getClient());
            assertNotNull(target.getUriBuilder());
            assertEquals("http://foo.com", target.getUri().toString());
        }
    }

    @Test
    public void testPath() {
        assertEquals("http://foo.com/bar", target("http://foo.com").path("/bar").getUri().toString());
    }

    @Test
    public void testPathSlashes() {
        assertEquals("https://foo.com/a/b", target("https://foo.com").path("a").path("b").getUri().toString());
    }

    @Test
    public void testPathManualSlash() {
        assertEquals("https://foo.com/a/b", target("https://foo.com").path("a/").path("b").getUri().toString());
    }

    @Test
    public void testQueryParam() {
        assertEquals("https://foo.com?a=b", target("https://foo.com").queryParam("a", "b").getUri().toString());
    }

    @Test
    public void testQueryParamAmpersand() {
        assertEquals("https://foo.com?a=b&c=d",
                target("https://foo.com").queryParam("a", "b").queryParam("c", "d").getUri().toString());
    }

    @Test
    public void testResolveTemplate() {
        assertEquals("http://foo.com/bar",
                target("http://foo.com/{x}").resolveTemplate("x", "bar").getUri().toString());
    }

    @Test
    public void testResolveTemplateIgnoreSlashes() {
        assertEquals("http://foo.com/p1/p2",
                target("http://foo.com/{x}").resolveTemplate("x", "p1/p2", false).getUri().toString());
    }

    @Test
    public void testResolveTemplateFromEncoded() {
        assertEquals("http://foo.com/%20",
                target("http://foo.com/{x}").resolveTemplateFromEncoded("x", "%20").getUri().toString());
    }

    @Test
    public void testResolveTemplates() {
        final Map<String, Object> map = new HashMap<>();
        map.put("x", "bar");
        map.put("y", "baz");
        assertEquals("http://foo.com/bar/baz",
                target("http://foo.com/{x}/{y}").resolveTemplates(map).getUri().toString());
    }

    @Test
    public void testResolveTemplatesIgnoreSlashes() {
        final Map<String, Object> map = new HashMap<>();
        map.put("x", "bar");
        map.put("y", "p1/p2");
        assertEquals("http://foo.com/bar/p1/p2",
                target("http://foo.com/{x}/{y}").resolveTemplates(map, false).getUri().toString());
    }

    @Test
    public void testResolveTemplatesFromEncoded() {
        final Map<String, Object> map = new HashMap<>();
        map.put("x", "bar");
        map.put("y", "%20");
        assertEquals("http://foo.com/bar/%20",
                target("http://foo.com/{x}/{y}").resolveTemplatesFromEncoded(map).getUri().toString());
    }

    @Test
    public void testGetConfiguration() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").getConfiguration());
    }

    @Test
    public void testProperty() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").property("name", "value"));
    }

    @Test
    public void testRegister1() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").register(Object.class));
    }

    @Test
    public void testRegister2() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").register(Object.class, 0));
    }

    @Test
    public void testRegister3() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").register(Object.class, Object.class));
    }

    @Test
    public void testRegister4() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").register(Object.class, Collections.emptyMap()));
    }

    @Test
    public void testRegister5() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").register(new Object()));
    }

    @Test
    public void testRegister6() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").register(new Object(), 0));
    }

    @Test
    public void testRegister7() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").register(new Object(), Object.class));
    }

    @Test
    public void testRegister8() {
        assertThrows(UnsupportedOperationException.class, () -> target("/").register(new Object(), Collections.emptyMap()));
    }
}
