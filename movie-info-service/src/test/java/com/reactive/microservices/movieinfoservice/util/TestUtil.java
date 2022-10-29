package com.reactive.microservices.movieinfoservice.util;

import com.reactive.microservices.movieinfoservice.document.MovieInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtil {

    public static List<MovieInfo> createMovieInfo() {
        MovieInfo movieInfo1 = MovieInfo.create(
                null,
                "Batman Begins",
                2005,
                List.of("Christian Bale", "Michael Cane"),
                LocalDate.parse("2005-06-15")
        );

        MovieInfo movieInfo2 = MovieInfo.create(
                null,
                "The Dark Knight",
                2008,
                List.of("Christian Bale", "Heath Ledger"),
                LocalDate.parse("2008-07-18")
        );

        MovieInfo movieInfo3 = MovieInfo.create(
                "movieId",
                "The Dark Knight Rises",
                2012,
                List.of("Christian Bale", "Tom Hardy"),
                LocalDate.parse("2012-07-20")
        );

        MovieInfo movieInfo4 = MovieInfo.create(
                null,
                "Jack Reacher",
                2012,
                List.of("Tom Cruise", "Rosamund Pike"),
                LocalDate.parse("2012-08-21")
        );

        return List.of(movieInfo1, movieInfo2, movieInfo3, movieInfo4);
    }
}
