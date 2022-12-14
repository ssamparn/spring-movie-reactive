package com.reactive.microservices.movieinfoservice.web.controller;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import com.reactive.microservices.movieinfoservice.service.MovieInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MovieInfoRestController {

    private final MovieInfoService movieInfoService;

    @PostMapping("/create-movie-info")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> newMovieInfo(@RequestBody @Valid MovieInfo newMovieInfo) {
        return movieInfoService
                .createMovieInfo(newMovieInfo)
                .log();
    }

    @GetMapping("/get-all-movie-info")
    @ResponseStatus(HttpStatus.OK)
    public Flux<MovieInfo> getAllMovieInfos(@RequestParam(value = "year", required = false) Integer year) {
        if (year != null) {
            return movieInfoService.getMoviesByReleaseYear(year);
        }
        return movieInfoService.getAllMovieInfo().log();
    }

    @GetMapping("/get-movie-info-by-id/{movieInfoId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<MovieInfo>> getMovieInfoById(@PathVariable(name = "movieInfoId") String movieInfoId) {
        return movieInfoService.getMovieInfoByInfoId(movieInfoId)
                .map(movieInfo -> ResponseEntity.ok().body(movieInfo))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @PutMapping("/update-movie-info/{movieInfoId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfo(@RequestBody MovieInfo newMovieInfo,
                                                          @PathVariable(name = "movieInfoId") String movieInfoId) {
        return movieInfoService.updateMovie(newMovieInfo, movieInfoId)
                .map(movieInfo -> ResponseEntity.ok().body(movieInfo))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @DeleteMapping("/delete-movie-info/{movieInfoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfoById(@PathVariable(name = "movieInfoId") String movieInfoId) {
        return movieInfoService.deleteMovieInfoByInfoId(movieInfoId);
    }

}
