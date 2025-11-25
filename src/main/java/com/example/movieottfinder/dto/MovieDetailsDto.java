package com.example.movieottfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailsDto {
    private Long tmdbId;
    private String title;
    private String overview;
    private String posterUrl;
    private String backdropUrl;
    private String releaseDate;
    private Integer runtime;
    private List<String> genres;
    private Double rating;
    private java.util.List<ProviderDto> providers;
}
