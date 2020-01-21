package org.minijax.persistence;

import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.minijax.persistence.MinijaxEntityManager;
import org.minijax.persistence.MinijaxEntityManagerFactory;
import org.minijax.persistence.MinijaxPersistenceProvider;

public class MinijaxEntityManagerTest {
    private MinijaxEntityManagerFactory emf;
    private MinijaxEntityManager em;
    private Widget widget;
    private Map<String, Object> properties;
    private LockModeType lockMode;
    private FlushModeType flushMode;

    @Before
    public void setUp() {
        final MinijaxPersistenceProvider provider = new MinijaxPersistenceProvider();
        emf = provider.createEntityManagerFactory("testdb", null);
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    /*
     * Unsupported
     */

    @Test(expected = UnsupportedOperationException.class)
    public void testFind1() {
        em.find(Widget.class, "123", properties);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFind2() {
        em.find(Widget.class, "123", lockMode);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFind3() {
        em.find(Widget.class, "123", lockMode, properties);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetReference() {
        em.getReference(Widget.class, "123");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFlushMode() {
        em.setFlushMode(flushMode);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetFlushMode() {
        em.getFlushMode();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testLock1() {
        em.lock(widget, lockMode);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testLock2() {
        em.lock(widget, lockMode, properties);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRefresh1() {
        em.refresh(widget);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRefresh2() {
        em.refresh(widget, properties);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRefresh3() {
        em.refresh(widget, lockMode);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRefresh4() {
        em.refresh(widget, lockMode, properties);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        em.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDetach() {
        em.detach(widget);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContains() {
        em.contains(widget);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetLockMode() {
        em.getLockMode(widget);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetProperty() {
        em.setProperty("foo", "bar");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetProperties() {
        em.getProperties();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateQuery1() {
        em.createQuery("");
    }

    @Test(expected = UnsupportedOperationException.class)
    @SuppressWarnings("rawtypes")
    public void testCreateQuery2() {
        em.createQuery((CriteriaUpdate) null);
    }

    @Test(expected = UnsupportedOperationException.class)
    @SuppressWarnings("rawtypes")
    public void testCreateQuery3() {
        em.createQuery((CriteriaDelete) null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateNamedQuery1() {
        em.createNamedQuery("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateNamedQuery2() {
        em.createNamedQuery("", Widget.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateNativeQuery3() {
        em.createNativeQuery("");
    }

    @Test(expected = UnsupportedOperationException.class)
    @SuppressWarnings("rawtypes")
    public void testCreateNativeQuery1() {
        em.createNativeQuery("", Widget.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateNativeQuery2() {
        em.createNativeQuery("", "");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateNamedStoredProcedureQuery() {
        em.createNamedStoredProcedureQuery("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateStoredProcedureQuery1() {
        em.createStoredProcedureQuery("");
    }

    @Test(expected = UnsupportedOperationException.class)
    @SuppressWarnings("rawtypes")
    public void testCreateStoredProcedureQuery2() {
        em.createStoredProcedureQuery("", Widget.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateStoredProcedureQuery3() {
        em.createStoredProcedureQuery("", "", "");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testJoinTransaction() {
        em.joinTransaction();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsJoinedToTransaction() {
        em.isJoinedToTransaction();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnwrap() {
        em.unwrap(Widget.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetDelegate() {
        em.getDelegate();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsOpen() {
        em.isOpen();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateEntityGraph1() {
        em.createEntityGraph(Widget.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateEntityGraph2() {
        em.createEntityGraph("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetEntityGraph() {
        em.getEntityGraph("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetEntityGraphs() {
        em.getEntityGraphs(Widget.class);
    }
}