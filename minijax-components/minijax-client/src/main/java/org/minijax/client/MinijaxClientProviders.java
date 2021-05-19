package org.minijax.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Providers;

import org.minijax.rs.writers.FileBodyWriter;
import org.minijax.rs.writers.InputStreamBodyWriter;
import org.minijax.rs.writers.StringBodyWriter;

public class MinijaxClientProviders implements Providers {
    private static final StringBodyWriter STRING_WRITER = new StringBodyWriter();
    private static final InputStreamBodyWriter INPUT_STREAM_WRITER = new InputStreamBodyWriter();
    private static final FileBodyWriter FILE_WRITER = new FileBodyWriter();
    private final MinijaxClient context;

    public MinijaxClientProviders(final MinijaxClient context) {
        this.context = context;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T> MessageBodyReader<T> getMessageBodyReader(
            final Class<T> type,
            final Type genericType,
            final Annotation[] annotations,
            final MediaType mediaType) {

        for (final Class<? extends MessageBodyReader<?>> readerClass : context.getReaders()) {
            final MessageBodyReader reader = context.getResource(readerClass);
            if (reader.isReadable(type, genericType, annotations, mediaType)) {
                return reader;
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T> MessageBodyWriter<T> getMessageBodyWriter(
            final Class<T> type,
            final Type genericType,
            final Annotation[] annotations,
            final MediaType mediaType) {

        if (STRING_WRITER.isWriteable(type, genericType, annotations, mediaType)) {
            return (MessageBodyWriter<T>) STRING_WRITER;
        }

        if (INPUT_STREAM_WRITER.isWriteable(type, genericType, annotations, mediaType)) {
            return (MessageBodyWriter<T>) INPUT_STREAM_WRITER;
        }

        if (FILE_WRITER.isWriteable(type, genericType, annotations, mediaType)) {
            return (MessageBodyWriter<T>) FILE_WRITER;
        }

        for (final Class<? extends MessageBodyWriter<?>> writerClass : context.getWriters()) {
            final MessageBodyWriter writer = context.getResource(writerClass);
            if (writer.isWriteable(type, genericType, annotations, mediaType)) {
                return writer;
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Throwable> ExceptionMapper<T> getExceptionMapper(final Class<T> type) {
        for (final Class<? extends ExceptionMapper<?>> exceptionMapperClass : context.getExceptionMappers()) {
            final ParameterizedType parameterizedType = (ParameterizedType) exceptionMapperClass.getGenericInterfaces()[0];
            final Class<? extends Exception> exClass = (Class<? extends Exception>) parameterizedType.getActualTypeArguments()[0];
            if (exClass.isAssignableFrom(type)) {
                return (ExceptionMapper<T>) context.getResource(exceptionMapperClass);
            }
        }
        return null;
    }

    @Override
    public <T> ContextResolver<T> getContextResolver(final Class<T> contextType, final MediaType mediaType) {
        throw new UnsupportedOperationException();
    }
}
