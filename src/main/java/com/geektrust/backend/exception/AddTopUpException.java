package com.geektrust.backend.exception;

/**
 * Exception occurs when Adding TopUp for no subscriptions or duplicate topUp
 */
public class AddTopUpException extends RuntimeException {

    public AddTopUpException() {
        super();
    }

    public AddTopUpException(String message) {
        super(message);
    }
}
