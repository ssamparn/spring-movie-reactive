package com.reactive.microservices.movieinfoservice.web.controller;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MovieInfoStreamController {

    private final Flux<MovieInfo> movieInfoFlux;

    @GetMapping(value = "/create-movie-info/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieInfo> getNewMovieUpdate() {
        return this.movieInfoFlux;
    }
}