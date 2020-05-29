package org.minijax.mustache;

import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;

public class MustacheFeature implements Feature {

    @Override
    public boolean configure(final FeatureContext context) {
        context.register(new DefaultMustacheFactory(), MustacheFactory.class);
        context.register(MinijaxMustacheWriter.class);
        context.register(MinijaxMustacheExceptionMapper.class);
        return true;
    }
}
