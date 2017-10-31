package org.minijax.cdi;

import java.io.InputStream;

import javax.inject.Provider;
import javax.servlet.http.Part;
import javax.ws.rs.DefaultValue;

import org.minijax.MinijaxForm;
import org.minijax.MinijaxRequestContext;

class FormParamProvider<T> implements Provider<T> {
    private final Key<T> key;

    public FormParamProvider(final Key<T> key) {
        this.key = key;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        final MinijaxRequestContext context = MinijaxRequestContext.getThreadLocal();
        final Class<?> c = key.getType();
        final MinijaxForm form = context.getForm();
        final String name = key.getName();

        if (c == InputStream.class) {
            return form == null ? null : (T) form.getInputStream(name);
        }

        if (c == Part.class) {
            return form == null ? null : (T) form.getPart(name);
        }

        String value = form == null ? null : form.getString(name);

        final DefaultValue defaultValue = key.getDefaultValue();
        if (value == null && defaultValue != null) {
            value = defaultValue.value();
        }

        return (T) context.getContainer().convertParamToType(value, c, null, key.getAnnotations());
    }
}
