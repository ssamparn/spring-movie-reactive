package com.reactive.microservices.moviereviewservice.handler;

import com.reactive.microservices.moviereviewservice.document.MovieReview;
import com.reactive.microservices.moviereviewservice.repository.MovieReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MovieReviewHandler {

    private final MovieReviewRepository movieReviewRepository;

    public Mono<ServerResponse> getAllMovieReview(ServerRequest request) {
        Flux<MovieReview> movieReviewFlux = movieReviewRepository.findAll();

        return ServerResponse.ok().body(movieReviewFlux, MovieReview.class);
    }

    public Mono<ServerResponse> createMovieReview(ServerRequest request) {
        return request.bodyToMono(MovieReview.class)
                .flatMap(movieReviewRepository::save)
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
    }

    public Mono<ServerResponse> updateMovieReview(ServerRequest request) {
        String movieReviewId = request.pathVariable("movieReviewId");
        Mono<MovieReview> movieReviewMono = movieReviewRepository.findById(movieReviewId);

        return movieReviewMono
                .flatMap(movieReview -> request.bodyToMono(MovieReview.class)
                        .map(movieReviewReq -> {
                            movieReview.setComment(movieReviewReq.getComment());
                            movieReview.setRating(movieReviewReq.getRating());
                            return movieReview;
                        })
                        .flatMap(movieReviewRepository::save)
                .flatMap(savedReview -> ServerResponse.ok().bodyValue(savedReview))
                );
    }

    public Mono<ServerResponse> deleteMovieReview(ServerRequest request) {
        String movieReviewId = request.pathVariable("movieReviewId");
        Mono<MovieReview> movieReviewMono = movieReviewRepository.findById(movieReviewId);

        return movieReviewMono
                .flatMap(movieReview -> movieReviewRepository.deleteById(movieReviewId)
                .then(ServerResponse.noContent().build()));
    }
}
