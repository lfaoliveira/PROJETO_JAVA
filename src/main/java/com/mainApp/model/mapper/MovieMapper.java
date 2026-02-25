package com.mainApp.model.mapper;

import com.mainApp.model.dto.TmdbMovieDto;
import com.mainApp.model.entity.MovieEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    // Converte Entidade para DTO
    TmdbMovieDto toDTO(MovieEntity movie);

    // Converte DTO para Entidade
    MovieEntity toEntity(TmdbMovieDto movieDTO);
}