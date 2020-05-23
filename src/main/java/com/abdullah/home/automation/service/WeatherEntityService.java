package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.WeatherEntity;

import java.util.List;
import java.util.Optional;

public interface WeatherEntityService {
    List<WeatherEntity> migrateWeatherEntity();

    Optional<WeatherEntity> findByEntityName(String entity);

    List<WeatherEntity> findAll();
}
