package com.reactive.microservices.movieaggregatorservice.web.exception;

public class MovieInfoClientException extends RuntimeException {
    private String message;
    private Integer statusCode;

    public MovieInfoClientException(String message, Integer statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
