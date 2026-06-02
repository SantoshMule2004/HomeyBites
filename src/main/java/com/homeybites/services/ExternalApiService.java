package com.homeybites.services;

public interface ExternalApiService {
    String autocomplete(String query, String countryCode, Long limit);

    String reverseGeocoding(Double lat, Double lng, String format);
}
