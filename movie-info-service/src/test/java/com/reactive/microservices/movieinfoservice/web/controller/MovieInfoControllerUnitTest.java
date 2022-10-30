package com.reactive.microservices.movieinfoservice.web.controller;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import com.reactive.microservices.movieinfoservice.service.MovieInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static com.reactive.microservices.movieinfoservice.util.TestUtil.createMovieInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = MovieInfoRestController.class)
@AutoConfigureWebTestClient
public class MovieInfoControllerUnitTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MovieInfoService movieInfoService;

    @Test
    void create_movie_info_test() {
        MovieInfo newMovieInfo = MovieInfo.create(
                "mockId",
                "Sita Ramam",
                2022,
                List.of("Dulquer Salmaan", "Mrunal Thakur"),
                LocalDate.parse("2022-08-05")
        );

        when(movieInfoService.createMovieInfo(isA(MovieInfo.class))).thenReturn(Mono.just(newMovieInfo));

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
                    assertEquals("mockId", movieInfo.getMovieInfoId());
                    assertEquals("Sita Ramam", movieInfo.getMovieName());
                });
    }

    @Test
    void create_movie_info_validation_test() {
        MovieInfo newMovieInfo = MovieInfo.create(
                "movieInfoId",
                "",
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
                .isBadRequest();
    }

    @Test
    void get_all_movie_infos_test() {
        when(movieInfoService.getAllMovieInfo()).thenReturn(Flux.fromIterable(createMovieInfo()));

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
    void get_movie_info_by_id_test() {
        when(movieInfoService.getMovieInfoByInfoId("movieId")).thenReturn(Mono.just(createMovieInfo().get(2)));

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
                    assertEquals("The Dark Knight Rises", movieInfo.getMovieName());
                });
    }

    @Test
    void update_movie_info_test() {
        MovieInfo newMovieInfo = MovieInfo.create(
                "mockMovieInfoId",
                "Sita Ramam",
                2022,
                List.of("Dulquer Salmaan", "Mrunal Thakur"),
                LocalDate.parse("2022-08-05")
        );

        when(movieInfoService.updateMovie(isA(MovieInfo.class), anyString())).thenReturn(Mono.just(newMovieInfo));

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
                    assertEquals("mockMovieInfoId", movieInfo.getMovieInfoId());
                    assertEquals("Sita Ramam", movieInfo.getMovieName());
                });
    }

    @Test
    void delete_movie_info_by_id_test() {
        when(movieInfoService.deleteMovieInfoByInfoId(anyString())).thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri("/api/v1/delete-movie-info-by-id/{movieInfoId}", "movieId")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(Void.class);
    }
}
