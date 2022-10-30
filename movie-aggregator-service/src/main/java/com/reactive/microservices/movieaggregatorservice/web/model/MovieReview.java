package com.reactive.microservices.movieaggregatorservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class MovieReview {

    private String movieReviewId;

    @NotNull(message = "Review.movieInfoId : must not be null")
    private Long movieInfoId;

    private String comment;

    @Min(value = 0L, message = "Review.rating : must not be negative")
    private Double rating;
}