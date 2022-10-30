package com.reactive.microservices.movieaggregatorservice.client;

import com.reactive.microservices.movieaggregatorservice.web.exception.MovieReviewClientException;
import com.reactive.microservices.movieaggregatorservice.web.exception.MovieReviewServerException;
import com.reactive.microservices.movieaggregatorservice.web.model.MovieReview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MovieReviewRestClient {

    private final WebClient webClient;

    @Autowired
    public MovieReviewRestClient(@Qualifier("movieReviewWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<MovieReview> retrieveMovieReviewsByMovieInfoId(String movieInfoId) {
        return webClient.get()
                .uri("/get-movie-review/{movieInfoId}", movieInfoId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.error("Error code : {}", clientResponse.statusCode().value());
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.empty();
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage -> Mono.error(new MovieReviewClientException(responseMessage)));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    log.error("Error code : {}", clientResponse.statusCode().value());
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage -> Mono.error(new MovieReviewServerException("Server exception in MoviesReview service" + responseMessage)));
                })
                .bodyToFlux(MovieReview.class)
                .log();
    }

}
