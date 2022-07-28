package com.geektrust.backend.exception;

/**
 * Exception occurs when start date provided for start subscription is not valid
 */
public class InvalidDateException extends RuntimeException {

    public InvalidDateException() {
        super();
    }

    public InvalidDateException(String message) {
        super(message);
    }
}
