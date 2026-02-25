package com.mainApp.controller;

import com.mainApp.model.dto.TmdbMovieDto;
import com.mainApp.service.MovieApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MoviesApiController implements MoviesApiInterface {
    @Autowired
    private MovieApiService queryService;

    public MoviesApiController() {
    }

    @GetMapping("/")
    public String getExamples() {
        //        return List.of(new MovieEntity("Example Diner", "123 Main St", 4.5));
        return "Example API Endpoints: /api/movies/{movieId} (e.g., /api/movies/550 for Fight Club)";
    }


    // Example call: /api/movies?name=Star Wars
    @GetMapping("/movies/{name}")
    public List<TmdbMovieDto> search(@PathVariable String name) {
        String language = "en-US"; // Default language
        List<TmdbMovieDto> movies = List.of(queryService.getMovieByName(name, language));
        return movies;
    }

}