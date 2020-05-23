package com.abdullah.home.automation.repository;

import com.abdullah.home.automation.domain.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WeatherEntityRepository extends CrudRepository<WeatherEntity, Long> {

    Optional<WeatherEntity> findByEntityName(String entity);
}
