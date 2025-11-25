package com.example.movieottfinder.entity;

import com.example.movieottfinder.dto.ProviderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "movies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDocument {

    @Id
    private String id;

    private Long tmdbId;

    private String title;
    private String overview;
    private String posterUrl;
    private String backdropUrl;
    private String releaseDate;
    private Integer runtime;
    private List<String> genres;
    private Double rating;

    private List<ProviderDto> providers;

    private Instant cachedAt;
}
