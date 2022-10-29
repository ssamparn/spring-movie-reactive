package com.reactive.microservices.movieinfoservice.repository;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {
    Flux<MovieInfo> findMovieInfosByReleaseYear(Integer releaseYear);
}
