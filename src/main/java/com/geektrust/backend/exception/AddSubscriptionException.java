package com.geektrust.backend.exception;

/**
 * Exception occurs when invalid date used or duplicate category/top comes in execution
 */
public class AddSubscriptionException extends RuntimeException {

    public AddSubscriptionException() {
        super();
    }

    public AddSubscriptionException(String message) {
        super(message);
    }
}
