package com.backend.hotelbooking.exception;

public class InvalidBookingException extends RuntimeException{

    public InvalidBookingException(String message){
        super(message);
    }
}
