package com.reactive.microservices.movieaggregatorservice.web.exception;

public class MovieReviewServerException extends RuntimeException {
    private String message;

    public MovieReviewServerException(String message) {
        super(message);
        this.message = message;
    }
}
