package com.example.movieottfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSummaryDto {
    private Long tmdbId;
    private String title;
    private String overview;
    private String posterUrl;
    private String releaseDate;
    private Double rating;
}
