package com.mainApp.model.dto;

// Record to map Google Places Result
public record TmdbMovieDto(
        Long id,
        String title,
        String overview,
        String release_date,
        Double vote_average) {
}