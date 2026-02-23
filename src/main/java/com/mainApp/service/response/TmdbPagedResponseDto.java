package com.mainApp.service.response;

import com.mainApp.model.dto.TmdbMovieDto;

import java.util.List;

public record TmdbPagedResponseDto(
        int page,
        List<TmdbMovieDto> results,
        int total_pages,
        int total_results) {
}
