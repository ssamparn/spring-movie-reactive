package com.reactive.microservices.movieaggregatorservice.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("movieInfoWebClient")
    public WebClient movieInfoWebClient() {
        return WebClient.builder()
                .build();
    }

    @Bean
    @Qualifier("movieReviewWebClient")
    public WebClient movieReviewWebClient() {
        return WebClient.builder()
                .build();
    }
}
