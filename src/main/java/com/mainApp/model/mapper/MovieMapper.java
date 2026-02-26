package com.mainApp.model.mapper;

import com.mainApp.model.dto.TmdbSearchMovieDto;
import com.mainApp.model.entity.MovieEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    // Converte Entidade para DTO
    MovieEntity toEntity(TmdbSearchMovieDto movieDTO);

    // Converte DTO para Entidade
    TmdbSearchMovieDto toDTO(MovieEntity movie);

}