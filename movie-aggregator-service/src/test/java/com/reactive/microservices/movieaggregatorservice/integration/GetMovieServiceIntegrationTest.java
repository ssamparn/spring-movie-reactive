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
    void movie_info_not_found_ById() {
        webTestClient.get()
                .uri("/api/v1/movies/{movieId}", "movieIdDoesNotExist_InInfo")
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(String.class)
                .isEqualTo("MovieInfo not available for MovieId: movieIdDoesNotExist_InInfo");
    }

    @Test
    void movie_reviews_not_found_ById() {
        webTestClient.get()
                .uri("/api/v1/movies/{movieId}", "movieIdDoesNotExist_InReview")
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(String.class)
                .isEqualTo("MovieInfo not available for MovieId: movieIdDoesNotExist_InReview");
    }

    @Test
    void movie_info_service_not_available() {
        webTestClient.get()
                .uri("/api/v1/movies/{movieId}", "movieInfo_Service_Unavailable")
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody(String.class)
                .isEqualTo("Server exception in MoviesInfo serviceMovieInfo Service Unavailable");
    }
}
