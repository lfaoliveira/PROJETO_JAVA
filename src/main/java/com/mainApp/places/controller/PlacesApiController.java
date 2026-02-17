package com.mainApp.places.controller;

import com.mainApp.places.data.Place;
import com.mainApp.places.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlacesApiController implements PlacesApiInterface {
    public PlacesApiController() {
    }

    @Autowired
    private QueryService queryService;

    // Example call: /api/places?loc=-23.5505,-46.6333 (São Paulo)
    @GetMapping("/places")
    public List<Place> search(@RequestParam String loc) {
        return queryService.getNearyPlaces(loc);
    }

}