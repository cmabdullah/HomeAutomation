package com.abdullah.home.automation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherStationApiConfig {

    @Value("${meteostat.baseUrl}")
    private String baseUrl;
    @Value("${meteostat.apiKey}")
    private String apiKey;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

}
