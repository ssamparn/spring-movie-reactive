package com.reactive.microservices.movieaggregatorservice.util;

import com.reactive.microservices.movieaggregatorservice.web.exception.MovieInfoServerException;
import reactor.core.Exceptions;
import reactor.util.retry.Retry;

import java.time.Duration;

public class RetryUtil {

    public static Retry retryMovieService() {
        return Retry.fixedDelay(3, Duration.ofMillis(100))
            .filter(ex -> ex instanceof MovieInfoServerException)
            .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) ->
                    Exceptions.propagate(retrySignal.failure())
            ));
    }
}
