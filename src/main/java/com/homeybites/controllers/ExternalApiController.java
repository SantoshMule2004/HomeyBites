package com.homeybites.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.services.ExternalApiService;

@RestController
@RequestMapping("/api/v1/location")
public class ExternalApiController {
    private final ExternalApiService externalApiService;

    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping(value = "/autocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> autocomplete(@RequestParam String query,
            @RequestParam(defaultValue = "in") String countryCode, @RequestParam(defaultValue = "5") Long limit) {
        String rawJson = externalApiService.autocomplete(query, countryCode, limit);
        return ResponseEntity.ok(rawJson);
    }

    @GetMapping(value = "/reverse-geocoding", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reverseGeocoding(@RequestParam Double latitude,
            @RequestParam() Double longitude, @RequestParam(defaultValue = "json") String format) {
        String rawJson = externalApiService.reverseGeocoding(latitude, longitude, format);
        return ResponseEntity.ok(rawJson);
    }
}