package com.homeybites.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.homeybites.services.ExternalApiService;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {

    private final RestClient restClient;
    private final String apiToken;

    public ExternalApiServiceImpl(RestClient restClient, @Value("${locationiq.accesstoken}") String apiToken) {
        this.restClient = restClient;
        this.apiToken = apiToken;
    }

    @Override
    public String autocomplete(String query, String countryCode, Long limit) {
        String externalApiUrl = "https://api.locationiq.com/v1/autocomplete?key={apiToken}&q={query}&limit={limit}&dedupe=1&";

        return restClient.get()
                .uri(externalApiUrl, this.apiToken, query, limit)
                .retrieve()
                .body(String.class);
    }

    @Override
    public String reverseGeocoding(Double lat, Double lng, String format) {

        String externalApiUrl = "https://us1.locationiq.com/v1/reverse?key={apiToken}&lat={lat}&lon={lng}&format={format}&";

        return restClient.get()
                .uri(externalApiUrl, this.apiToken, lat, lng, format)
                .retrieve()
                .body(String.class);
    }
}
