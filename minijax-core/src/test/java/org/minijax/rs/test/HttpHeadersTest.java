package org.minijax.rs.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

import org.junit.Test;

public class HttpHeadersTest {

    @Test
    public void testMultipleHeaders() {
        final MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("a", "b");
        headers.add("a", "c");

        final MinijaxTestHttpHeaders httpHeaders = new MinijaxTestHttpHeaders(headers, null);
        assertEquals(Arrays.asList("b", "c"), httpHeaders.getRequestHeader("a"));
    }

    @Test
    public void testLanguageMissing() {
        final MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        final MinijaxTestHttpHeaders httpHeaders = new MinijaxTestHttpHeaders(headers, null);
        assertNull(httpHeaders.getLanguage());
    }

    @Test
    public void testLanguage() {
        final MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("Content-Language", "en-US");

        final MinijaxTestHttpHeaders httpHeaders = new MinijaxTestHttpHeaders(headers, null);
        assertEquals("en-US", httpHeaders.getLanguage().toLanguageTag());
    }

    @Test
    public void testContentLengthMissing() {
        final MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        final MinijaxTestHttpHeaders httpHeaders = new MinijaxTestHttpHeaders(headers, null);
        assertEquals(-1, httpHeaders.getLength());
    }

    @Test
    public void testContentLengthMalformed() {
        final MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("Content-Length", "x");

        final MinijaxTestHttpHeaders httpHeaders = new MinijaxTestHttpHeaders(headers, null);
        assertEquals(-1, httpHeaders.getLength());
    }

    @Test
    public void testContentLength() {
        final MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("Content-Length", "1024");

        final MinijaxTestHttpHeaders httpHeaders = new MinijaxTestHttpHeaders(headers, null);
        assertEquals(1024, httpHeaders.getLength());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDate() {
        final MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        final MinijaxTestHttpHeaders httpHeaders = new MinijaxTestHttpHeaders(headers, null);
        httpHeaders.getDate();
    }
}
