package com.reactive.microservices.movieinfoservice.service;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import com.reactive.microservices.movieinfoservice.repository.MovieInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class MovieInfoService {

    private final MovieInfoRepository movieInfoRepository;
    private final Sinks.Many<MovieInfo> movieInfoSink;

    public Mono<MovieInfo> createMovieInfo(MovieInfo newMovieInfo) {
        return movieInfoRepository.save(newMovieInfo)
                .doOnNext(movieInfoSink::tryEmitNext);
    }

    public Flux<MovieInfo> getAllMovieInfo() {
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> getMovieInfoByInfoId(String movieInfoId) {
        return movieInfoRepository.findById(movieInfoId);
    }

    public Mono<MovieInfo> updateMovie(MovieInfo newMovieInfo, String movieInfoId) {
        return movieInfoRepository.findById(movieInfoId)
            .flatMap(movieInfo -> {
                movieInfo.setMovieName(newMovieInfo.getMovieName());
                movieInfo.setCast(newMovieInfo.getCast());
                movieInfo.setReleaseDate(newMovieInfo.getReleaseDate());
                movieInfo.setReleaseYear(newMovieInfo.getReleaseYear());
                return movieInfoRepository.save(movieInfo);
            });
    }

    public Mono<Void> deleteMovieInfoByInfoId(String movieInfoId) {
        return movieInfoRepository.deleteById(movieInfoId);
    }

    public Flux<MovieInfo> getMoviesByReleaseYear(Integer releaseYear) {
        return movieInfoRepository.findMovieInfosByReleaseYear(releaseYear);
    }
}
