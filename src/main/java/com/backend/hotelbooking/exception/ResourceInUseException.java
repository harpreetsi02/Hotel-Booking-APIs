package com.backend.hotelbooking.exception;

public class ResourceInUseException extends RuntimeException {

    public ResourceInUseException(String message) {
        super(message);
    }
}