package com.mainApp.controller;

import com.mainApp.model.dto.MovieDto;
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
    public MoviesApiController() {
    }

    @GetMapping("/")
    public String getExamples() {
        //        return List.of(new MovieEntity("Example Diner", "123 Main St", 4.5));
        return "Example API Endpoints: /api/movies/{movieId} (e.g., /api/movies/550 for Fight Club)";
    }

    @Autowired
    private MovieApiService queryService;

    // Example call: /api/places?loc=-23.5505,-46.6333 (São Paulo)
    @GetMapping("/movies/{loc}")
    public List<MovieDto> search(@PathVariable String loc) {
        List<MovieDto> movies = List.of(queryService.getMovieDetails(loc));
        return movies;
    }

}