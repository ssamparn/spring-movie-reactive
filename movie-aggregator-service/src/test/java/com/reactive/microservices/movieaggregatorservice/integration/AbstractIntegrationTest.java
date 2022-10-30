package com.reactive.microservices.movieaggregatorservice.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.reactive.microservices.movieaggregatorservice.MovieAggregatorServiceApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(classes = MovieAggregatorServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock
@TestPropertySource(
        properties = {
                "http.url.movie-info=http://localhost:7073/api/v1",
                "http.url.movie-review=http://localhost:7073/api/v1"
        }
)
public class AbstractIntegrationTest {

    protected static WireMockServer wireMock = new WireMockServer(WireMockSpring.options()
            .port(7073)
            .notifier(new ConsoleNotifier(true))
            .extensions(new ResponseTemplateTransformer(true)));

    @Autowired
    protected WebTestClient webTestClient;

    @BeforeAll
    static void setUpClass() {
        wireMock.start();
    }

    @AfterEach
    void tearDown() {
        wireMock.resetAll();
    }

    @AfterAll
    static void tearDownClass() {
        wireMock.shutdown();
    }

}
