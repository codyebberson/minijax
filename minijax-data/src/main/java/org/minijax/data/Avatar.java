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

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The Avatar class represents an avatar or profile picture for an entity.
 *
 * Entities that have an Avatar should implement the AvatarEntity interface.
 *
 * The avatar fields are intended to be "embedded" with the parent entity,
 * such that each of these columns are added to the entity's database table.
 */
@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Avatar implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The image is the original generated default. */
    public static final int IMAGE_TYPE_DEFAULT = 0;

    /** The image was manually uploaded by the user. */
    public static final int IMAGE_TYPE_MANUAL = 1;

    /** The image was pulled from Gravatar. */
    public static final int IMAGE_TYPE_GRAVATAR = 2;

    /** The image was pulled from Google Plus. */
    public static final int IMAGE_TYPE_GOOGLE = 3;

    private String imageUrl;
    private String thumbUrl;

    @XmlTransient
    private int imageType;

    public Avatar() {
        this(null, null);
    }

    public Avatar(final String imageUrl, final String thumbUrl) {
        this.imageUrl = imageUrl;
        this.thumbUrl = thumbUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(final String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(final int imageType) {
        this.imageType = imageType;
    }
}
