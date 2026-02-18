package com.mainApp.movies.data;

// Record to map Google Places Result
public record Place(
        String name,
        String vicinity,
        Double rating
) {
}