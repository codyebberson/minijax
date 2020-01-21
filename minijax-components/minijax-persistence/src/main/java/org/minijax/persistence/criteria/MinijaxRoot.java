package org.minijax.persistence.criteria;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.minijax.commons.MinijaxException;
import org.minijax.persistence.metamodel.MinijaxEntityType;

public class MinijaxRoot<T> implements javax.persistence.criteria.Root<T> {
    private final MinijaxEntityType<T> entityType;
    private String aliasName;

    public MinijaxRoot(final MinijaxEntityType<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public Class<? extends T> getJavaType() {
        return entityType.getJavaType();
    }

    @Override
    public MinijaxRoot<T> alias(final String name) {
        if (aliasName != null) {
            throw new MinijaxException("Root alias already set");
        }
        this.aliasName = name;
        return this;
    }

    @Override
    public <Y> MinijaxPath<Y> get(final String attributeName) {
        return new MinijaxPath<>(this, attributeName);
    }

    /*
     * Unsupported
     */

    @Override
    public Set<Join<T, ?>> getJoins() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCorrelated() {
        throw new UnsupportedOperationException();
    }

    @Override
    public From<T, T> getCorrelationParent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> Join<T, Y> join(final SingularAttribute<? super T, Y> attribute) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> Join<T, Y> join(final SingularAttribute<? super T, Y> attribute, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> CollectionJoin<T, Y> join(final CollectionAttribute<? super T, Y> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> SetJoin<T, Y> join(final SetAttribute<? super T, Y> set) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> ListJoin<T, Y> join(final ListAttribute<? super T, Y> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> MapJoin<T, K, V> join(final MapAttribute<? super T, K, V> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> CollectionJoin<T, Y> join(final CollectionAttribute<? super T, Y> collection, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> SetJoin<T, Y> join(final SetAttribute<? super T, Y> set, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> ListJoin<T, Y> join(final ListAttribute<? super T, Y> list, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> MapJoin<T, K, V> join(final MapAttribute<? super T, K, V> map, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> Join<X, Y> join(final String attributeName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> CollectionJoin<X, Y> joinCollection(final String attributeName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> SetJoin<X, Y> joinSet(final String attributeName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> ListJoin<X, Y> joinList(final String attributeName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, K, V> MapJoin<X, K, V> joinMap(final String attributeName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> Join<X, Y> join(final String attributeName, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> CollectionJoin<X, Y> joinCollection(final String attributeName, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> SetJoin<X, Y> joinSet(final String attributeName, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> ListJoin<X, Y> joinList(final String attributeName, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, K, V> MapJoin<X, K, V> joinMap(final String attributeName, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Path<?> getParentPath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> Path<Y> get(final SingularAttribute<? super T, Y> attribute) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <E, C extends Collection<E>> Expression<C> get(final PluralAttribute<T, C, E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V, M extends Map<K, V>> Expression<M> get(final MapAttribute<T, K, V> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression<Class<? extends T>> type() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Predicate isNull() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Predicate isNotNull() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Predicate in(final Object... values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Predicate in(final Expression<?>... values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Predicate in(final Collection<?> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Predicate in(final Expression<Collection<?>> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X> Expression<X> as(final Class<X> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCompoundSelection() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Selection<?>> getCompoundSelectionItems() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAlias() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Fetch<T, ?>> getFetches() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> Fetch<T, Y> fetch(final SingularAttribute<? super T, Y> attribute) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> Fetch<T, Y> fetch(final SingularAttribute<? super T, Y> attribute, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> Fetch<T, Y> fetch(final PluralAttribute<? super T, ?, Y> attribute) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Y> Fetch<T, Y> fetch(final PluralAttribute<? super T, ?, Y> attribute, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> Fetch<X, Y> fetch(final String attributeName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <X, Y> Fetch<X, Y> fetch(final String attributeName, final JoinType jt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityType<T> getModel() {
        throw new UnsupportedOperationException();
    }
}