package com.reactive.microservices.movieinfoservice.web.controller;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import com.reactive.microservices.movieinfoservice.service.MovieInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MovieInfoRestController {

    private final MovieInfoService movieInfoService;

    @PostMapping("/create-movie-info")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> newMovieInfo(@RequestBody MovieInfo newMovieInfo) {

        Mono<MovieInfo> movieInfoMono = movieInfoService.createMovieInfo(newMovieInfo).log();

        return movieInfoMono;
    }
}
