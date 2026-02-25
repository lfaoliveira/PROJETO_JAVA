package com.mainApp.model.mapper;

import com.mainApp.model.dto.TmdbResumedMovieDto;
import com.mainApp.model.entity.MovieEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    // Converte Entidade para DTO
    MovieEntity toEntity(TmdbResumedMovieDto movieDTO);

    // Converte DTO para Entidade
    TmdbResumedMovieDto toDTO(MovieEntity movie);

}