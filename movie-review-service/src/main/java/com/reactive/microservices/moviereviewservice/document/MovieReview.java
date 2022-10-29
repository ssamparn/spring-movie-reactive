package com.reactive.microservices.moviereviewservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Document
public class MovieReview {

    @Id
    private String movieReviewId;
    private Long movieInfoId;
    private String comment;
    private Double rating;
}
