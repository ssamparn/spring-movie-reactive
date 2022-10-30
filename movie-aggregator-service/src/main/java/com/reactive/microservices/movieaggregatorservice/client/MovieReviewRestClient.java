package com.reactive.microservices.movieaggregatorservice.client;

import com.reactive.microservices.movieaggregatorservice.web.model.MovieInfo;
import com.reactive.microservices.movieaggregatorservice.web.model.MovieReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                .bodyToFlux(MovieReview.class)
                .log();
    }

}
