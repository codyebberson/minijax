package org.minijax.security;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.Validate;
import org.mindrot.jbcrypt.BCrypt;
import org.minijax.db.DefaultNamedEntity;

/**
 * The SecurityUser class is an abstract base class for "security" entities
 * that can login, logout, change passwords, etc.
 */
@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("squid:S2160")
public abstract class SecurityUser extends DefaultNamedEntity {
    private static final long serialVersionUID = 1L;

    @Column(length = 128, unique = true)
    private String email;

    @XmlTransient
    private String roles;

    @XmlTransient
    private String passwordHash;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(final String password) {
        passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Returns all user roles as a string.
     *
     * @return All user roles.
     */
    public String getRoles() {
        return roles;
    }

    /**
     * Sets the user roles.
     *
     * @param roles The user roles.
     */
    public void setRoles(final String... roles) {
        Validate.notEmpty(roles, "User roles cannot be empty.");

        this.roles = Arrays.asList(roles).toString();
    }

    public boolean hasRole(final String role) {
        return roles != null && roles.contains(role);
    }

    /**
     * Validates that the user object is in a good state.
     *
     * Validates that name and email are not null and not empty.
     */
    @Override
    public void validate() {
        super.validate();

        Validate.notEmpty(email, "Email must not be null or empty.");
        Validate.inclusiveBetween(1, 128, email.length(), "Email must be between 1 and 32 characters long");

        Validate.notEmpty(roles, "Roles must not be null or empty.");
    }
}