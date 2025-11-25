package com.example.movieottfinder.repository;

import com.example.movieottfinder.entity.MovieDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MovieRepository extends MongoRepository<MovieDocument, String> {

    Optional<MovieDocument> findByTmdbId(Long tmdbId);
}
