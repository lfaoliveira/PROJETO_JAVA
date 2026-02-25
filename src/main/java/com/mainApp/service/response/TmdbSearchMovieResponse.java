package com.mainApp.service.response;

import com.mainApp.model.dto.TmdbResumedMovieDto;

import java.util.List;

public record TmdbSearchMovieResponse(
        int page,
        List<TmdbResumedMovieDto> results,
        int total_pages,
        int total_results
) {}
