package com.reactive.microservices.movieaggregatorservice.web.exception;

public class MovieInfoServerException extends RuntimeException {
    private String message;

    public MovieInfoServerException(String message) {
        super(message);
        this.message = message;
    }
}
