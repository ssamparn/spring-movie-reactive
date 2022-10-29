package com.reactive.microservices.moviereviewservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Document
public class MovieReview {

    @Id
    private String movieReviewId;
    @NotNull(message = "Review.movieInfoId : must not be null")
    private Long movieInfoId;
    private String comment;
    @Min(value = 0L, message = "Review.rating : must not be negative")
    private Double rating;
}
