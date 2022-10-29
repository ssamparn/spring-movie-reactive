package com.reactive.microservices.moviereviewservice.unittest;

import com.reactive.microservices.moviereviewservice.document.MovieReview;
import com.reactive.microservices.moviereviewservice.handler.MovieReviewExceptionHandler;
import com.reactive.microservices.moviereviewservice.handler.MovieReviewHandler;
import com.reactive.microservices.moviereviewservice.repository.MovieReviewRepository;
import com.reactive.microservices.moviereviewservice.router.MovieReviewRouterConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;

@WebFluxTest
@ContextConfiguration(classes = {
        MovieReviewRouterConfig.class,
        MovieReviewHandler.class,
        MovieReviewExceptionHandler.class
})
@AutoConfigureWebTestClient
public class MovieReviewUnitTest {

    @MockBean
    private MovieReviewRepository movieReviewRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void create_movie_review_test() {
        MovieReview newMovieReview = MovieReview.create(
                null,
                1L,
                "Awesome Movie",
                9.0
        );

        Mockito.when(movieReviewRepository.save(isA(MovieReview.class))).thenReturn(Mono.just(newMovieReview));

        webTestClient
                .post()
                .uri("/api/v1/create-movie-review")
                .bodyValue(newMovieReview)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieReview.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieReview movieReview = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(movieReview);
                    assertNotNull(movieReview.getMovieInfoId());
                });
    }

    @Test
    void create_movie_review_validate_test() {
        MovieReview newMovieReview = MovieReview.create(
                null,
                null,
                "Awesome Movie",
                -9.0
        );

        webTestClient
                .post()
                .uri("/api/v1/create-movie-review")
                .bodyValue(newMovieReview)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Review.movieInfoId : must not be null,Review.rating : must not be negative");
    }
}
