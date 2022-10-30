package com.reactive.microservices.movieaggregatorservice.web.exception;

public class MovieReviewClientException extends RuntimeException {
    private String message;

    public MovieReviewClientException(String message) {
        super(message);
        this.message = message;
    }
}
