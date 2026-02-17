package com.mainApp.places.data;

import java.util.List;

// Wrapper for the Google API response
public record GooglePlacesResponse(
        List<Place> results,
        String status
) {
}
