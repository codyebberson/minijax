package org.minijax.persistence;

import static org.mockito.Mockito.*;

import javax.ws.rs.core.FeatureContext;

import org.junit.Test;

public class PersistenceFeatureTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNotMinijax() {
        final PersistenceFeature feature = new PersistenceFeature();
        feature.configure(mock(FeatureContext.class));
    }
}