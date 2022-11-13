package com.reactive.microservices.movieaggregatorservice.client;

import com.reactive.microservices.movieaggregatorservice.util.RetryUtil;
import com.reactive.microservices.movieaggregatorservice.web.exception.MovieInfoClientException;
import com.reactive.microservices.movieaggregatorservice.web.exception.MovieInfoServerException;
import com.reactive.microservices.movieaggregatorservice.web.model.MovieInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MovieInfoRestClient {

    private final String url;
    private final WebClient webClient;

    @Autowired
    public MovieInfoRestClient(@Qualifier("movieInfoWebClient") WebClient webClient, @Value("${http.url.movie-info}") String url) {
        this.webClient = webClient;
        this.url = url;
    }

    public Mono<MovieInfo> retrieveMovieInfoById(String movieInfoId) {
        return webClient.get()
                .uri(url + "/get-movie-info-by-id/{movieInfoId}", movieInfoId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.error("Error code : {}", clientResponse.statusCode().value());
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new MovieInfoClientException("MovieInfo not available for MovieId: " + movieInfoId, clientResponse.statusCode().value()));
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage -> Mono.error(new MovieInfoClientException(responseMessage, clientResponse.statusCode().value())));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    log.error("Error code : {}", clientResponse.statusCode().value());
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage -> Mono.error(new MovieInfoServerException("Server exception in MoviesInfo service" + responseMessage)));
                })
                .bodyToMono(MovieInfo.class)
                .retryWhen(RetryUtil.retryMovieService())
                .log();
    }


}
