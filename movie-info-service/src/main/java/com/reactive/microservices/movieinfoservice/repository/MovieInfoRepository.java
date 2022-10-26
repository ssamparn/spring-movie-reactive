package com.reactive.microservices.movieinfoservice.repository;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {

}
