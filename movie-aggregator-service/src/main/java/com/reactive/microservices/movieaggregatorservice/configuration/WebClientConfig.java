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
                .baseUrl("http://localhost:7071/api/v1")
                .build();
    }

    @Bean
    @Qualifier("movieReviewWebClient")
    public WebClient movieReviewWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:7072/api/v1")
                .build();
    }
}
