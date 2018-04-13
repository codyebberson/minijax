package org.minijax.undertow;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.minijax.Minijax;
import org.minijax.MinijaxApplication;
import org.minijax.MinijaxRequestContext;
import org.minijax.MinijaxServer;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.Headers;

public class MinijaxUndertowServer implements MinijaxServer, HttpHandler {
    private final Minijax minijax;
    private final Undertow undertow;

    public MinijaxUndertowServer(final Minijax minijax) {
        this.minijax = minijax;
        undertow = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(new BlockingHandler(this))
                .build();
    }

    @Override
    public void start() {
        undertow.start();
    }

    @Override
    public void stop() {
        undertow.stop();
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        final MinijaxApplication application = minijax.getDefaultApplication();

        try (final MinijaxRequestContext ctx = new MinijaxUndertowRequestContext(application, exchange)) {
            final Response response = application.handle(ctx);

            exchange.setStatusCode(response.getStatus());

            for (final Entry<String, List<Object>> entry : response.getHeaders().entrySet()) {
                final String name = entry.getKey();
                for (final Object value : entry.getValue()) {
                    exchange.getResponseHeaders().add(Headers.fromCache(name), value.toString());
                }
            }

            final MediaType mediaType = response.getMediaType();
            if (mediaType != null) {
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, mediaType.toString());
            }

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            application.writeEntity(response.getEntity(), mediaType, outputStream);

            final ByteBuffer byteBuffer = ByteBuffer.wrap(outputStream.toByteArray());
            exchange.getResponseSender().send(byteBuffer);
        }
    }
}
