package org.minijax.security;

import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.CacheIndex;
import org.minijax.dao.DefaultBaseEntity;
import org.minijax.dao.converters.UuidConverter;

/**
 * The ApiKey class represents a single API key for a user.
 */
@Entity
@Cacheable
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = { @Index(columnList = "USERID", unique = false) })
@NamedQuery(
        name = "ApiKey.findByUser",
        query = "SELECT k FROM ApiKey k" +
                " WHERE k.userId = :userId" +
                " AND k.deletedDateTime IS NULL")
@NamedQuery(
        name = "ApiKey.findByValue",
        query = "SELECT k FROM ApiKey k" +
                " WHERE k.value = :value")
@SuppressWarnings("squid:S2160")
public class ApiKey extends DefaultBaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(columnDefinition = "BINARY(16)")
    @Convert(converter = UuidConverter.class)
    @NotNull
    private UUID userId;

    private String name;

    @Column(length = 64, unique = true)
    @CacheIndex
    @Size(min = 8, max = 64)
    private String value;

    public UUID getUserId() {
        return userId;
    }

    public void setUser(final SecurityUser user) {
        userId = user.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}