package com.reactive.microservices.movieinfoservice.repository;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static com.reactive.microservices.movieinfoservice.util.TestUtil.createMovieInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ActiveProfiles("test")
public class MovieInfoRepositoryTest {

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        movieInfoRepository
                .saveAll(createMovieInfo())
                .blockLast();
    }

    @Test
    void save__movie_info_test() {
        MovieInfo movieInfo = MovieInfo.create(
                null,
                "Inception",
                2013,
                List.of("Christian Bale", "Tom Hardy"),
                LocalDate.parse("2013-07-20")
        );

        Mono<MovieInfo> movieMono = movieInfoRepository.save(movieInfo).log();

        StepVerifier.create(movieMono)
                .assertNext(movie -> {
                    assertNotNull(movie.getMovieInfoId());
                    assertEquals("Inception", movie.getMovieName());
                })
                .verifyComplete();
    }

    @Test
    void update__movie_info_test() {
        MovieInfo movieInfoToBeUpdated = movieInfoRepository.findById("movieId").block();
        movieInfoToBeUpdated.setMovieName("Inception");

        Mono<MovieInfo> movieMono = movieInfoRepository.save(movieInfoToBeUpdated);

        StepVerifier.create(movieMono)
                .assertNext(movie -> {
                    assertNotNull(movie.getMovieInfoId());
                    assertEquals("Inception", movie.getMovieName());
                })
                .verifyComplete();
    }

    @Test
    void find_all_movies_test() {
        Flux<MovieInfo> movieInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(movieInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void find__movie_byId_test() {
        Mono<MovieInfo> movieMono = movieInfoRepository.findById("movieId").log();

        StepVerifier.create(movieMono)
                .assertNext(movie -> {
                    assertEquals("The Dark Knight Rises", movie.getMovieName());
                })
                .verifyComplete();
    }

    @Test
    void delete__movie_byId_test() {
        movieInfoRepository.deleteById("movieId").block();
        Flux<MovieInfo> movieInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(movieInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository
                .deleteAll()
                .block();
    }
}
