package com.mainApp.controller;

import com.mainApp.model.entity.MovieEntity;
import com.mainApp.service.QueryService;
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
    public List<Object> getExamples() {
        return List.of(new MovieEntity("Example Diner", "123 Main St", 4.5));
    }

    @Autowired
    private QueryService queryService;

    // Example call: /api/places?loc=-23.5505,-46.6333 (São Paulo)
    @GetMapping("/movies/{loc}")
    public List<MovieEntity> search(@PathVariable String loc) {
        return queryService.getNearyPlaces(loc);
    }

}