package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.WeatherEntity;
import com.abdullah.home.automation.repository.WeatherEntityRepository;
import com.abdullah.home.automation.service.WeatherEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherEntityServiceImpl implements WeatherEntityService {

    private final WeatherEntityRepository weatherEntityRepository;

    @Autowired
    public WeatherEntityServiceImpl(WeatherEntityRepository weatherEntityRepository){
        this.weatherEntityRepository = weatherEntityRepository;
    }
    @Override
    public List<WeatherEntity> migrateWeatherEntity() {

        //weatherEntityRepository.deleteAll();
        List<WeatherEntity> weatherEntitiesList = List.of(

                new WeatherEntity("humidity"),
                new WeatherEntity("temperature"),
                new WeatherEntity("dewpoint"),
                new WeatherEntity("winddirection"),
                new WeatherEntity("windspeed"),
                new WeatherEntity("precipitation"),
                new WeatherEntity("pressure"));

        return (List<WeatherEntity>) weatherEntityRepository.saveAll(weatherEntitiesList);
    }

    @Override
    public Optional<WeatherEntity> findByEntityName(String entity) {
        return weatherEntityRepository.findByEntityName(entity);
    }

    @Override
    public List<WeatherEntity> findAll() {
        return (List<WeatherEntity>) weatherEntityRepository.findAll();
    }

    @Override
    public void deleteAll() {
        weatherEntityRepository.deleteAll();
    }
}
