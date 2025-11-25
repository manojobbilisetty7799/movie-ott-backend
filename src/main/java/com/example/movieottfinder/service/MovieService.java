package com.example.movieottfinder.service;

import com.example.movieottfinder.dto.MovieDetailsDto;
import com.example.movieottfinder.dto.MovieSummaryDto;
import com.example.movieottfinder.dto.ProviderDto;
import com.example.movieottfinder.entity.MovieDocument;
import com.example.movieottfinder.repository.MovieRepository;
import com.example.movieottfinder.tmdb.TmdbGenre;
import com.example.movieottfinder.tmdb.TmdbMovieDetails;
import com.example.movieottfinder.tmdb.TmdbMovieResult;
import com.example.movieottfinder.tmdb.TmdbClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final TmdbClient tmdbClient;

    public MovieService(MovieRepository movieRepository, TmdbClient tmdbClient) {
        this.movieRepository = movieRepository;
        this.tmdbClient = tmdbClient;
    }

    public List<MovieSummaryDto> search(String query) {
        List<TmdbMovieResult> results = tmdbClient.searchMovies(query);

        return results.stream()
                .map(r -> new MovieSummaryDto(
                        r.getId(),
                        r.getTitle(),
                        r.getOverview(),
                        buildImageUrl(r.getPosterPath()),
                        r.getReleaseDate(),
                        r.getVoteAverage()
                ))
                .toList();
    }

    public MovieDetailsDto getDetails(Long tmdbId) {
        // 1. Check cache
        MovieDocument cached = movieRepository.findByTmdbId(tmdbId).orElse(null);
        if (cached != null) {
            return toDetailsDto(cached);
        }

        // 2. Call TMDB
        TmdbMovieDetails details = tmdbClient.getMovieDetails(tmdbId);
        List<ProviderDto> providers = tmdbClient.getProviders(tmdbId);

        MovieDocument document = MovieDocument.builder()
                .tmdbId(details.getId())
                .title(details.getTitle())
                .overview(details.getOverview())
                .posterUrl(buildImageUrl(details.getPosterPath()))
                .backdropUrl(buildImageUrl(details.getBackdropPath()))
                .releaseDate(details.getReleaseDate())
                .runtime(details.getRuntime())
                .genres(details.getGenres() != null
                        ? details.getGenres().stream().map(TmdbGenre::getName).toList()
                        : List.of())
                .rating(details.getVoteAverage())
                .providers(providers)
                .cachedAt(Instant.now())
                .build();

        movieRepository.save(document);

        return toDetailsDto(document);
    }

    private MovieDetailsDto toDetailsDto(MovieDocument m) {
        return new MovieDetailsDto(
                m.getTmdbId(),
                m.getTitle(),
                m.getOverview(),
                m.getPosterUrl(),
                m.getBackdropUrl(),
                m.getReleaseDate(),
                m.getRuntime(),
                m.getGenres(),
                m.getRating(),
                m.getProviders()
        );
    }

    private String buildImageUrl(String path) {
        if (path == null) return null;
        return "https://image.tmdb.org/t/p/w500" + path;
    }
}
