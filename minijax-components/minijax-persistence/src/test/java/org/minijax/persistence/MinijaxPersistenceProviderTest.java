package org.minijax.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitInfo;

import org.junit.Test;
import org.minijax.persistence.MinijaxPersistenceProvider;
import org.minijax.persistence.MinijaxPersistenceUnitInfo;

public class MinijaxPersistenceProviderTest {

    @Test
    public void testCreateEntityManagerFactory() {
        final MinijaxPersistenceProvider provider = new MinijaxPersistenceProvider();
        provider.createEntityManagerFactory("testdb", new HashMap<>());
    }

    @Test
    public void testCreateContainerEntityManagerFactory() {
        final Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.driver", "org.h2.jdbcx.JdbcDataSource");
        props.put("javax.persistence.jdbc.url", "jdbc:h2:mem:");

        final MinijaxPersistenceProvider provider = new MinijaxPersistenceProvider();
        provider.createContainerEntityManagerFactory(new MinijaxPersistenceUnitInfo("testdb", ""), props);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGenerateSchema1() {
        final MinijaxPersistenceProvider provider = new MinijaxPersistenceProvider();
        provider.generateSchema("", null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGenerateSchema2() {
        final MinijaxPersistenceProvider provider = new MinijaxPersistenceProvider();
        provider.generateSchema((PersistenceUnitInfo) null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetProviderUtil() {
        final MinijaxPersistenceProvider provider = new MinijaxPersistenceProvider();
        provider.getProviderUtil();
    }
}