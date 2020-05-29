package org.minijax.client;

import java.io.IOException;

import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.minijax.commons.MinijaxException;
import org.minijax.rs.util.EntityUtils;

public class ConversionUtils {

    ConversionUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> T convertApacheToJax(final HttpEntity apacheEntity, final Class<T> targetClass) {
        if (apacheEntity == null) {
            return null;
        }

        final MediaType mediaType = MediaType.valueOf(apacheEntity.getContentType().getValue());

        try {
            return EntityUtils.readEntity(
                    targetClass,
                    null,
                    null,
                    mediaType,
                    null,
                    apacheEntity.getContent());
        } catch (final IOException ex) {
            throw new MinijaxException("Error converting input stream: " + ex.getMessage(), ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertToGenericType(final HttpEntity apacheEntity, final GenericType<T> genericType) {
        if (apacheEntity == null) {
            return null;
        }

        final MediaType mediaType = MediaType.valueOf(apacheEntity.getContentType().getValue());

        try {
            return EntityUtils.readEntity(
                    (Class<T>) genericType.getRawType(),
                    genericType.getType(),
                    null,
                    mediaType,
                    null,
                    apacheEntity.getContent());
        } catch (final IOException ex) {
            throw new MinijaxException("Error converting input stream: " + ex.getMessage(), ex);
        }
    }
}
