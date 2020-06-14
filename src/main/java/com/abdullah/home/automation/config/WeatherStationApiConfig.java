package com.abdullah.home.automation.config;

import com.abdullah.home.automation.dto.config.weatherStationApiConfigDto;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;

public class WeatherStationApiConfig {

    private String baseUrl;
    private String apiKey;


    private WeatherStationApiConfig(weatherStationApiConfigDto weatherStationApiConfigDto) {
        this.baseUrl = weatherStationApiConfigDto.getBaseUrl();
        this.apiKey = weatherStationApiConfigDto.getApiKey();
    }

    private static class Holder {
        private static WeatherStationApiConfig instance = new WeatherStationApiConfig(loadConfig());
    }

    public static WeatherStationApiConfig getInstance() {
        return WeatherStationApiConfig.Holder.instance;
    }

    private static weatherStationApiConfigDto loadConfig() {

        try {
            Yaml yaml = new Yaml(new Constructor(weatherStationApiConfigDto.class));
            File file = new File("weatherStationApiConfig.yaml");
            return yaml.load(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("weather api configuration failed");
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
