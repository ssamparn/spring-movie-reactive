package com.reactive.microservices.moviereviewservice.util;

import com.reactive.microservices.moviereviewservice.document.MovieReview;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtil {

    public static List<MovieReview> createMovieReview() {
        MovieReview movieReview1 = MovieReview.create(
                null,
                1L,
                "Batman Begins",
                9.0
        );

        MovieReview movieReview2 = MovieReview.create(
                null,
                1L,
                "The Dark Knight",
                9.0
        );

        MovieReview movieReview3 = MovieReview.create(
                "movieReviewId",
                2L,
                "The Dark Knight Rises",
                8.0
        );

        MovieReview movieReview4 = MovieReview.create(
                null,
                2L,
                "Jack Reacher",
                2.2
        );

        return List.of(movieReview1, movieReview2, movieReview3, movieReview4);
    }
}
