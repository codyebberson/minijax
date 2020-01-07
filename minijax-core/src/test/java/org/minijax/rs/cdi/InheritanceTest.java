package org.minijax.rs.cdi;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.junit.BeforeClass;
import org.junit.Test;
import org.minijax.rs.test.MinijaxTest;

public class InheritanceTest extends MinijaxTest {

    public static class MyResource {
    }

    public static abstract class MyBaseClass {
        @Inject MyResource baseInjected;
    }

    @Singleton
    public static class MySubClass extends MyBaseClass {
        @Inject MyResource subInjected;
    }

    public static abstract class BasePathParamClass {
        @PathParam("id")
        public String id;
    }

    public static class SubPathParamClass extends BasePathParamClass {
        @GET
        @Path("/{id}")
        public String handle() {
            return id;
        }
    }

    @BeforeClass
    public static void setUpInheritanceTest() {
        resetServer();
        register(MySubClass.class);
        register(SubPathParamClass.class);
    }

    @Test
    public void testInheritanceInject() {
        final MySubClass r = getServer().getResource(MySubClass.class);
        assertNotNull(r);
        assertNotNull(r.subInjected);
        assertNotNull(r.baseInjected);
    }

    @Test
    public void testInheritancePathParam() {
        assertEquals("foo", target("/foo").request().get(String.class));
    }
}