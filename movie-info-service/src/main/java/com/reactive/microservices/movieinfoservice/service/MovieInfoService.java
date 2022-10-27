package com.reactive.microservices.movieinfoservice.service;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import com.reactive.microservices.movieinfoservice.repository.MovieInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieInfoService {

    private final MovieInfoRepository movieInfoRepository;

    public Mono<MovieInfo> createMovieInfo(MovieInfo newMovieInfo) {
        Mono<MovieInfo> movieInfoMono = movieInfoRepository.save(newMovieInfo);

        return movieInfoMono;
    }
}
