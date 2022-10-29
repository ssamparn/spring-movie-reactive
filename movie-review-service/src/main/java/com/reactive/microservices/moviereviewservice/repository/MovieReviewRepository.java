package com.reactive.microservices.moviereviewservice.repository;

import com.reactive.microservices.moviereviewservice.document.MovieReview;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MovieReviewRepository extends ReactiveMongoRepository<MovieReview, String> {
    Flux<MovieReview> findByMovieInfoId(Long movieInfoId);
}
