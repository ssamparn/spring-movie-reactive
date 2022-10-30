package com.reactive.microservices.movieaggregatorservice.integration;

import com.reactive.microservices.movieaggregatorservice.web.model.Movie;
import org.junit.jupiter.api.Test;

public class GetMovieServiceIntegrationTest extends AbstractIntegrationTest {

    @Test
    void retrieve_movie_ById() {
        webTestClient.get()
                .uri("/api/v1/movies/{movieId}", "movieId")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Movie.class);
    }

    @Test
    void movie_not_found_ById() {
        webTestClient.get()
                .uri("/api/v1/movies/{movieId}", "movieIdDoesNotExist")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(String.class);
    }
}
