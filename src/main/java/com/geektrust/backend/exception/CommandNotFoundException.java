package com.geektrust.backend.exception;

/**
 * Exception occurs when command to be used is not found
 */
public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException() {
        super();
    }

    public CommandNotFoundException(String message) {
        super(message);
    }
}
