package org.minijax.db;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 * The ConflictException represents a HTTP 409 error.
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class ConflictException extends ClientErrorException {
    private static final long serialVersionUID = 1L;
    private final String key;

    public ConflictException(final String key) {
        super("The " + key + " already exists", Response.Status.CONFLICT);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
