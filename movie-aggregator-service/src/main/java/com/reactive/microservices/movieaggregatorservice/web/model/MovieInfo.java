package com.reactive.microservices.movieaggregatorservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Validated
public class MovieInfo {

    private String movieInfoId;

    @NotBlank(message = "movieInfo.movieName must be present")
    private String movieName;

    @NotNull
    @Positive(message = "movieInfo.releaseYear must be positive")
    private Integer releaseYear;

    private List<@NotBlank String> cast;

    private LocalDate releaseDate;

}
