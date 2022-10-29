package com.reactive.microservices.movieinfoservice.web.controller;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import com.reactive.microservices.movieinfoservice.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.reactive.microservices.movieinfoservice.util.TestUtil.createMovieInfo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieInfoServiceIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        movieInfoRepository
                .saveAll(createMovieInfo())
                .blockLast();
    }

    @Test
    void create_movie_info_test() {
        MovieInfo newMovieInfo = MovieInfo.create(
                null,
                "Sita Ramam",
                2022,
                List.of("Dulquer Salmaan", "Mrunal Thakur"),
                LocalDate.parse("2022-08-05")
        );

        webTestClient
                .post()
                .uri("/api/v1/create-movie-info")
                .bodyValue(newMovieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieInfo movieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(movieInfo);
                    assertNotNull(movieInfo.getMovieInfoId());
                });
    }

    @Test
    void get_all_movie_infos_test() {
        webTestClient
                .get()
                .uri("/api/v1/get-all-movie-info")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MovieInfo.class)
                .hasSize(4);
    }

    @Test
    void get_movie_infos_by_release_year_test() {
        URI uri = UriComponentsBuilder
                .fromUriString("/api/v1/get-all-movie-info")
                .queryParam("year", 2012)
                .buildAndExpand()
                .toUri();

        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MovieInfo.class)
                .hasSize(2);
    }


    @Test
    void get_movie_info_by_id_test() {
        webTestClient
                .get()
                .uri("/api/v1/get-movie-info-by-id/{movieInfoId}", "movieId")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieInfo movieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(movieInfo);
                    assertNotNull(movieInfo.getMovieInfoId());
                });
    }

    @Test
    void update_movie_info_test() {
        MovieInfo newMovieInfo = MovieInfo.create(
                null,
                "Sita Ramam",
                2022,
                List.of("Dulquer Salmaan", "Mrunal Thakur"),
                LocalDate.parse("2022-08-05")
        );

        webTestClient
                .put()
                .uri("/api/v1/update-movie-info/{movieInfoId}", "movieId")
                .bodyValue(newMovieInfo)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    MovieInfo movieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(movieInfo);
                    assertNotNull(movieInfo.getMovieInfoId());
                });
    }

    @Test
    void delete_movie_info_by_id_test() {
        webTestClient
                .delete()
                .uri("/api/v1/delete-movie-info-by-id/{movieInfoId}", "movieId")
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(Void.class);
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository
                .deleteAll()
                .block();
    }
}
