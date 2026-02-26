package com.mainApp.service.response;

import com.mainApp.model.dto.TmdbSearchMovieDto;

import java.util.List;

public record TmdbSearchMovieResponse(
        int page,
        List<TmdbSearchMovieDto> results,
        int total_pages,
        int total_results
) {
}
