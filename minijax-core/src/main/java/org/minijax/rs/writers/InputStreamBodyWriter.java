package org.minijax.rs.writers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;

import org.minijax.commons.IOUtils;

public class InputStreamBodyWriter implements MessageBodyWriter<InputStream> {

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return InputStream.class.isAssignableFrom(type);
    }

    @Override
    public void writeTo(
            final InputStream inputStream,
            final Class<?> type,
            final Type genericType,
            final Annotation[] annotations,
            final MediaType mediaType,
            final MultivaluedMap<String, Object> httpHeaders,
            final OutputStream entityStream)
                    throws IOException {

        IOUtils.copy(inputStream, entityStream);
    }
}
