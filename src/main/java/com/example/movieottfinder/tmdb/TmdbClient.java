package com.example.movieottfinder.tmdb;

import com.example.movieottfinder.dto.ProviderDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TmdbClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String region;
    private final String language;

    public TmdbClient(
            RestTemplate restTemplate,
            @Value("${tmdb.api-key}") String apiKey,
            @Value("${tmdb.base-url}") String baseUrl,
            @Value("${tmdb.region}") String region,
            @Value("${tmdb.language}") String language
    ) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.region = region;
        this.language = language;
    }

    public List<TmdbMovieResult> searchMovies(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search/movie")
                .queryParam("api_key", apiKey)
                .queryParam("query", query)
                .queryParam("language", language)
                .build()
                .toUriString();

        TmdbSearchResponse response = restTemplate.getForObject(url, TmdbSearchResponse.class);
        return response != null && response.getResults() != null
                ? response.getResults()
                : List.of();
    }

    public TmdbMovieDetails getMovieDetails(Long tmdbId) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/movie/" + tmdbId)
                .queryParam("api_key", apiKey)
                .queryParam("language", language)
                .build()
                .toUriString();

        return restTemplate.getForObject(url, TmdbMovieDetails.class);
    }

    public List<ProviderDto> getProviders(Long tmdbId) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/movie/" + tmdbId + "/watch/providers")
                .queryParam("api_key", apiKey)
                .build()
                .toUriString();

        TmdbProviderResponse response =
                restTemplate.getForObject(url, TmdbProviderResponse.class);

        if (response == null) {
            return List.of();
        }

        Map<String, TmdbProviderRegion> results = response.getResults();
        if (results == null || !results.containsKey(region)) {
            return List.of();
        }

        TmdbProviderRegion regionData = results.get(region);
        List<ProviderDto> providers = new ArrayList<>();

        if (regionData.getFlatrate() != null) {
            regionData.getFlatrate().forEach(p ->
                    providers.add(new ProviderDto(p.getProviderName(), p.getLogoPath(), "flatrate")));
        }
        if (regionData.getRent() != null) {
            regionData.getRent().forEach(p ->
                    providers.add(new ProviderDto(p.getProviderName(), p.getLogoPath(), "rent")));
        }
        if (regionData.getBuy() != null) {
            regionData.getBuy().forEach(p ->
                    providers.add(new ProviderDto(p.getProviderName(), p.getLogoPath(), "buy")));
        }

        return providers;
    }
}
