package com.example;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.minijax.test.MinijaxTest;

public class HelloDockerTest extends MinijaxTest {

    @Before
    public void setUp() {
        register(HelloDocker.class);
    }

    @Test
    public void testHello() {
        assertEquals("Hello world!", target("/").request().get(String.class));
    }
}