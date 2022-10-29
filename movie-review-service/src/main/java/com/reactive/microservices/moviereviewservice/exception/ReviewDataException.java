package com.reactive.microservices.moviereviewservice.exception;

public class ReviewDataException extends RuntimeException {

    private String message;

    public ReviewDataException(String message) {
        super(message);
        this.message = message;
    }
}
