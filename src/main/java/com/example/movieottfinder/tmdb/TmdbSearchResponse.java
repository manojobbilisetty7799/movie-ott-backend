package com.example.movieottfinder.tmdb;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TmdbSearchResponse {
    private java.util.List<TmdbMovieResult> results;
}
