package org.minijax.cdi;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.UriInfo;

import org.junit.BeforeClass;
import org.junit.Test;
import org.minijax.test.MinijaxTest;

public class InjectTest extends MinijaxTest {

    @Singleton
    static class Counter {
        int count;
    }

    public static class MyBean {
        @PathParam("a") String path;
    }

    public static class InjectedResource {
        @Inject Counter counter;
        @Context UriInfo uriInfo;
        @CookieParam("a") String cookie;
        @FormParam("a") String form;
        @HeaderParam("a") String header;
        @QueryParam("a") @DefaultValue("b") String query;
        @PathParam("a") String path;
        @BeanParam MyBean bean;

        @GET
        @POST
        @Path("/inject/{a}")
        public InjectedResource get() {
            return this;
        }
    }

    @BeforeClass
    public static void setUpInjectTest() {
        resetServer();
        register(Counter.class);
        register(InjectedResource.class);
    }

    @Test
    public void testGetCounter() {
        final InjectedResource r = target("/inject/test").request().get(InjectedResource.class);
        assertNotNull(r.counter);
    }

    @Test
    public void testUriInfo() {
        final InjectedResource r = target("/inject/test").request().get(InjectedResource.class);
        assertEquals("/inject/test", r.uriInfo.getPath());
    }

    @Test
    public void testCookie() {
        final InjectedResource r = target("/inject/test").request().cookie("a", "hello").get(InjectedResource.class);
        assertNotNull(r);
        assertEquals("hello", r.cookie);
    }

    @Test
    public void testFormParam() {
        final Form form = new Form();
        form.param("a", "hello");

        final InjectedResource r = target("/inject/test").request().post(Entity.form(form), InjectedResource.class);
        assertEquals("hello", r.form);
    }

    @Test
    public void testHeader() {
        final InjectedResource r = target("/inject/test").request().header("a", "myheader").get(InjectedResource.class);
        assertEquals("myheader", r.header);
    }

    @Test
    public void testQuery() {
        final InjectedResource r = target("/inject/test?a=myquery").request().get(InjectedResource.class);
        assertEquals("myquery", r.query);
    }

    @Test
    public void testQueryDefaultValue() {
        final InjectedResource r = target("/inject/test").request().get(InjectedResource.class);
        assertEquals("b", r.query);
    }

    @Test
    public void testPath() {
        final InjectedResource r = target("/inject/mypath").request().get(InjectedResource.class);
        assertEquals("mypath", r.path);
    }

    @Test
    public void testBean() {
        final InjectedResource r = target("/inject/mypath").request().get(InjectedResource.class);
        assertEquals("mypath", r.bean.path);
    }
}
