package com.mainApp.model.dto;

// DTO for Movie data from TMDb API - used for search results and resumed movie info
public record TmdbResumedMovieDto(
        Long id,
        String title,
        String overview,
        String release_date,
        Double vote_average) {
}