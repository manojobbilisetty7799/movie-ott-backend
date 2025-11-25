package com.example.movieottfinder.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class TmdbProviderResponse {

    // key = region code like "IN"
    @JsonProperty("results")
    private Map<String, TmdbProviderRegion> results;
}
