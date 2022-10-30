package com.reactive.microservices.movieaggregatorservice.web.advice;

import com.reactive.microservices.movieaggregatorservice.web.exception.MovieInfoClientException;
import com.reactive.microservices.movieaggregatorservice.web.exception.MovieInfoServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MovieAggregateControllerAdvice {

    @ExceptionHandler(MovieInfoClientException.class)
    public ResponseEntity<String> handleClientException(MovieInfoClientException ex) {
        log.error("Exception caught while handling client exception : {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }

    @ExceptionHandler(MovieInfoServerException.class)
    public ResponseEntity<String> handleRuntimeException(MovieInfoServerException ex) {
        log.error("Exception caught while handling runtime exception : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
