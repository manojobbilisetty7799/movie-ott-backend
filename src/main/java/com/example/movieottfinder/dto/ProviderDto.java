package com.example.movieottfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDto {
    private String providerName;
    private String logoPath;
    private String type;   // "flatrate", "rent", "buy"
}
