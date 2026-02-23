package com.mainApp.model.mapper;

import com.mainApp.model.dto.TmdbMovieDto;
import com.mainApp.model.entity.MovieEntity;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class MovieMapper {

    public static MovieEntity toDomain(@NotNull TmdbMovieDto dto) {

        return new MovieEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getOverview(),
                LocalDate.parse(dto.getRelease_date()),
                dto.getVote_average()
        );
    }
}