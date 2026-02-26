package com.mainApp.model.dto;

// DTO for Movie data from TMDb API - used for search results and resumed movie info
public record TmdbSearchMovieDto(
        Long id,
        boolean adult,
        String backdrop_path,
        Integer[] genre_ids,
        String original_language,
        String original_title,
        String overview,
        Double popularity,
        String poster_path,
        String release_date,
        String title,
        boolean video,
        Double vote_average,
        Integer vote_count
) {
}

