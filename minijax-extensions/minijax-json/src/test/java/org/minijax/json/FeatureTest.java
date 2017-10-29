package org.minijax.json;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.minijax.test.MinijaxTest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FeatureTest extends MinijaxTest {

    @Before
    public void setUp() {
        register(MinijaxJsonFeature.class);
    }

    @Test
    public void testFeature() {
        final ObjectMapper mapper = getServer().get(ObjectMapper.class);
        assertNotNull(mapper);
    }
}