package com.reactive.microservices.moviereviewservice.handler;

import com.reactive.microservices.moviereviewservice.document.MovieReview;
import com.reactive.microservices.moviereviewservice.exception.ReviewDataException;
import com.reactive.microservices.moviereviewservice.exception.ReviewNotFoundException;
import com.reactive.microservices.moviereviewservice.repository.MovieReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieReviewHandler {

    private final MovieReviewRepository movieReviewRepository;
    private final Validator validator;

    public Mono<ServerResponse> getAllMovieReview(ServerRequest request) {
        Flux<MovieReview> movieReviewFlux = movieReviewRepository.findAll();

        return ServerResponse.ok().body(movieReviewFlux, MovieReview.class);
    }

    public Mono<ServerResponse> createMovieReview(ServerRequest request) {
        return request.bodyToMono(MovieReview.class)
                .doOnNext(this::validate)
                .flatMap(movieReviewRepository::save)
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
    }

    private void validate(MovieReview movieReview) {
        Set<ConstraintViolation<MovieReview>> constraintViolations = validator.validate(movieReview);

        log.info("constraintViolations : {}", constraintViolations);

        if (constraintViolations.size() > 0) {
            String errorMessage = constraintViolations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .sorted()
                    .collect(Collectors.joining(","));
            throw new ReviewDataException(errorMessage);
        }
    }

    public Mono<ServerResponse> updateMovieReview(ServerRequest request) {
        String movieReviewId = request.pathVariable("movieReviewId");
        Mono<MovieReview> movieReviewMono = movieReviewRepository.findById(movieReviewId)
                .switchIfEmpty(Mono.error(new ReviewNotFoundException("Movie Review not found for the given reviewId: " + movieReviewId)));

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
