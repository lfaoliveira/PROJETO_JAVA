package com.mainApp.places.data;

// Record to map Google Places Result
public record Place(
        String name,
        String vicinity,
        Double rating
) {
}