package org.markoccini.toolkit.common.exceptions;

public class ServerErrorException extends RuntimeException {

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException() {
        super("An Error occured while working with the request.");
    }
}
