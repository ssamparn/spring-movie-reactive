package com.reactive.microservices.movieaggregatorservice.web.controller;

import com.reactive.microservices.movieaggregatorservice.client.MovieInfoRestClient;
import com.reactive.microservices.movieaggregatorservice.client.MovieReviewRestClient;
import com.reactive.microservices.movieaggregatorservice.web.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieAggregateRestController {

    private final MovieInfoRestClient movieInfoRestClient;
    private final MovieReviewRestClient movieReviewRestClient;

    @GetMapping(value = "/{movieId}")
    public Mono<Movie> retrieveMovieById(@PathVariable("movieId") String movieId) {

        return movieInfoRestClient.retrieveMovieInfoById(movieId)
            .flatMap(movieInfo -> movieReviewRestClient.retrieveMovieReviewsByMovieInfoId(movieInfo.getMovieInfoId())
                    .collectList()
                    .map(reviews -> new Movie(movieInfo, reviews)));

    }
}
