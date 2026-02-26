package com.mainApp.controller;

import com.mainApp.service.MovieApiService;
import com.mainApp.service.response.TmdbSearchMovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/movies/{name}?page={page}")
    public TmdbSearchMovieResponse search(@PathVariable String name, @RequestParam String page) {
        String language = "en-US"; // Default language
        TmdbSearchMovieResponse movies = queryService.getMovieByName(name, language, page);
        return movies;
    }

}