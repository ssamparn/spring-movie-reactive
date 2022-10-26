package com.reactive.microservices.movieinfoservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Document
public class MovieInfo {

    @Id
    private String movieInfoId;
    private String movieName;
    private Integer releaseYear;
    private List<String> cast;
    private LocalDate releaseDate;
}
