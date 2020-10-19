package org.minijax.jndi;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NameClassPair;

import org.junit.Test;

public class NameClassPairEnumerationTest {

    @Test
    public void testEnumeration() {
        final Map<Object, Object> data = new HashMap<>();
        data.put("foo", "bar");

        final MinijaxNameClassPairEnumeration e = new MinijaxNameClassPairEnumeration(data);
        assertTrue(e.hasMore());

        final NameClassPair pair = e.next();
        assertNotNull(pair);
        assertEquals("foo", pair.getName());
        assertEquals("java.lang.String", pair.getClassName());

        assertFalse(e.hasMore());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNextElement() {
        final MinijaxNameClassPairEnumeration e = new MinijaxNameClassPairEnumeration(new HashMap<>());
        e.nextElement();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testHasMoreElements() {
        final MinijaxNameClassPairEnumeration e = new MinijaxNameClassPairEnumeration(new HashMap<>());
        e.hasMoreElements();
    }
}