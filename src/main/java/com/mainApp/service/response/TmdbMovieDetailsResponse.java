package com.mainApp.service.response;

import com.mainApp.model.entity.*;

import java.util.List;

public record TmdbMovieDetailsResponse(
        Integer id,
        boolean adult,
        String backdropPath,
        long budget,
        String originCountry,
        List<GenreEntity> genres,
        String originalLanguage,
        String originalTitle,
        String overview,
        double popularity,
        String posterPath,
        String productionCompany,
        String productionCountry,
        String releaseDate,
        long revenue,
        String status,
        String tagline,
        String title,
        boolean video

) {}
