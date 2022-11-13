package com.reactive.microservices.movieinfoservice.config;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<MovieInfo> sink() {
        return Sinks.many().replay().all();
    }

    @Bean
    public Flux<MovieInfo> movieInfoBroadCast(Sinks.Many<MovieInfo> movieInfoSink) {
        return movieInfoSink.asFlux();
    }
}
