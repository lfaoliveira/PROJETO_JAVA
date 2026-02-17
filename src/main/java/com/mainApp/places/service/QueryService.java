package com.mainApp.places.service;

import com.mainApp.places.data.GooglePlacesResponse;
import com.mainApp.places.data.PlaceType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mainApp.places.data.Place;

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
        String url = String.format(
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=1500&type=%s&key=%s",
                location, PlaceType.DINER.name().toLowerCase(), API_KEY
        );

        GooglePlacesResponse response = restTemplate.getForObject(url, GooglePlacesResponse.class);
        return response != null ? response.results() : Collections.emptyList();
    }
}


