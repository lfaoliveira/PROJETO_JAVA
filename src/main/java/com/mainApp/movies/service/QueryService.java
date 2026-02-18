package com.mainApp.movies.service;

import com.mainApp.movies.data.MovieResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mainApp.movies.data.Place;

import java.util.Collections;
import java.util.List;

import com.mainApp.config.Secrets;


@Service
public class QueryService {
    private final String API_KEY;

    public QueryService() {
        this.API_KEY = Secrets.get("PLACES_API_KEY");
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Place> getNearyPlaces(String location) {

        String url = "";
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);
        System.out.println("response: " + response);
        return response != null ? response.results() : Collections.emptyList();
    }
}


