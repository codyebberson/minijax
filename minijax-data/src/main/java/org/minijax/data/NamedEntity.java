/*
 * AJIBOT CONFIDENTIAL
 * __________________
 *
 *  2017 Ajibot Inc
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Ajibot Inc and its suppliers, if any.
 * The intellectual and technical concepts contained herein
 * are proprietary to Ajibot Inc and its suppliers and may
 * be covered by U.S. and Foreign Patents, patents in process,
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written
 * permission is obtained from Ajibot Inc.
 */
package org.minijax.data;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;
import org.eclipse.persistence.annotations.CacheIndex;

/**
 * The NamedEntity class is a base class for web entities with names.
 */
@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class NamedEntity extends BaseEntity implements Principal {
    private static final long serialVersionUID = 1L;
    private static final String HANDLE_SPECIAL_CHARS = "!#$%&'()*+,/:;=?@[\\]^`{|}~";

    @Column(length = 32, unique = true)
    @CacheIndex
    private String handle;

    private String name;

    @Embedded
    private Avatar avatar;

    public NamedEntity() {
        this(null);
    }

    public NamedEntity(final UUID id) {
        super(id);
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(final String handle) {
        if (handle != null) {
            this.handle = handle.trim();
        } else {
            this.handle = null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        if (name != null) {
            this.name = name.trim();
        } else {
            this.name = null;
        }
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(final Avatar avatar) {
        this.avatar = avatar;
    }

    public abstract String getUrl();


    public void generateHandle() {
        if (name == null) {
            handle = RandomStringUtils.randomAlphanumeric(16).toLowerCase();
        } else {
            handle = (name.replaceAll("[^A-Za-z0-9]", "") + "-" + RandomStringUtils.randomAlphanumeric(6)).toLowerCase();
        }
    }


    @Override
    public void validate() {
        validateHandle(handle);
        validateName(name);
    }


    public static void validateHandle(final String handle) {
        Validate.notEmpty(handle, "Handle must not be null or empty.");
        Validate.inclusiveBetween(1, 32, handle.length(), "Handle must be between 1 and 32 characters long");
        Validate.isTrue(handle.charAt(0) != '.', "Handle cannot start with a period");

        for (int i = 0; i < HANDLE_SPECIAL_CHARS.length(); i++) {
            final char c = HANDLE_SPECIAL_CHARS.charAt(i);
            Validate.isTrue(handle.indexOf(c) == -1, "Handle cannot contain any of the following special characters: %s", HANDLE_SPECIAL_CHARS);
        }
    }


    public static void validateName(final String name) {
        Validate.notEmpty(name, "Name must not be null or empty.");
        Validate.inclusiveBetween(1, 255, name.length(), "Name must be between 1 and 255 characters long");
    }


    /**
     * Sorts a list of ID objects by name (ascending).
     *
     * @param list The list of named entities (modified in place).
     */
    public static <T extends NamedEntity> void sortByName(final List<T> list) {
        Collections.sort(list, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }
}
