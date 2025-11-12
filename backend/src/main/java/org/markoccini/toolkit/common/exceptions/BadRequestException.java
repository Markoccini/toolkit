package org.markoccini.toolkit.common.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super("There was an issue with the request sent.");
    }
}
