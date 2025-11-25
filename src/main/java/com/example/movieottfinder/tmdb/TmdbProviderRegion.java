package com.example.movieottfinder.tmdb;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TmdbProviderRegion {
    private List<TmdbProvider> flatrate;
    private List<TmdbProvider> buy;
    private List<TmdbProvider> rent;
}
