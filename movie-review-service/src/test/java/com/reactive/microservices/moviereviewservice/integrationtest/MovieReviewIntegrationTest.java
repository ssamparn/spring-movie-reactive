package com.reactive.microservices.moviereviewservice.integrationtest;

import com.reactive.microservices.moviereviewservice.document.MovieReview;
import com.reactive.microservices.moviereviewservice.repository.MovieReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.reactive.microservices.moviereviewservice.util.TestUtil.createMovieReview;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieReviewIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MovieReviewRepository movieReviewRepository;

    @BeforeEach
    void setUp() {
        movieReviewRepository
                .saveAll(createMovieReview())
                .blockLast();
    }

    @Test
    void create_movie_review_test() {
        MovieReview newMovieReview = MovieReview.create(
                null,
                1L,
                "Awesome Movie",
                9.0
        );

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
                    assertNotNull(movieReview.getMovieReviewId());
                });
    }

    @Test
    void get_all_movie_reviews_test() {
        webTestClient
                .get()
                .uri("/api/v1/get-all-movie-reviews")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MovieReview.class)
                .hasSize(4);
    }

    @Test
    void update_movie_reviews_test() {
        MovieReview newMovieReview = MovieReview.create(
                null,
                1L,
                "Awesome Movie",
                9.0
        );

        webTestClient
                .put()
                .uri("/api/v1/update-movie-review/{movieReviewId}", "movieReviewId")
                .bodyValue(newMovieReview)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MovieReview.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieReview movieReview = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(movieReview);
                    assertNotNull(movieReview.getMovieReviewId());
                });
    }

    @Test
    void delete_movie_review_by_id_test() {
        webTestClient
                .delete()
                .uri("/api/v1/delete-movie-review/{movieReviewId}", "movieReviewId")
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(Void.class);
    }

    @AfterEach
    void tearDown() {
        movieReviewRepository
                .deleteAll()
                .block();
    }
}
