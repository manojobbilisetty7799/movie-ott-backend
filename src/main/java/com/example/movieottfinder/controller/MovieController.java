package com.example.movieottfinder.controller;

import com.example.movieottfinder.dto.MovieDetailsDto;
import com.example.movieottfinder.dto.MovieSummaryDto;
import com.example.movieottfinder.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*") // later you can restrict this to your frontend domain
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<MovieSummaryDto> search(@RequestParam String query) {
        return service.search(query);
    }

    @GetMapping("/{tmdbId}")
    public MovieDetailsDto details(@PathVariable Long tmdbId) {
        return service.getDetails(tmdbId);
    }
}
