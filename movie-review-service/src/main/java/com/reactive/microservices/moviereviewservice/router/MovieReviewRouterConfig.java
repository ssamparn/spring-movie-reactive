package com.reactive.microservices.moviereviewservice.router;

import com.reactive.microservices.moviereviewservice.handler.MovieReviewHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MovieReviewRouterConfig {

    @Bean
    RouterFunction<ServerResponse> routes(MovieReviewHandler handler) {
        return route(GET("/api/v1/get-all-movie-reviews").and(accept(MediaType.APPLICATION_JSON)), handler::getAllMovieReview)
                .andRoute(POST("/api/v1/create-movie-review").and(accept(MediaType.APPLICATION_JSON)), handler::createMovieReview)
                .andRoute(PUT("/api/v1/update-movie-review/{movieReviewId}").and(contentType(MediaType.APPLICATION_JSON)), handler::updateMovieReview)
                .andRoute(DELETE("/api/v1/delete-movie-review/{movieReviewId}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteMovieReview);
    }

}
