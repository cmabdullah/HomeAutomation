package com.abdullah.home.automation.repository;

import com.abdullah.home.automation.domain.MonthlyData;
import com.abdullah.home.automation.domain.Payload;
import com.abdullah.home.automation.domain.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PayloadRepository extends CrudRepository<Payload, Long> {
    List<Payload> findByMonthlyDataAndWeatherEntity(MonthlyData monthlyData, WeatherEntity weatherEntity);

    List<Payload> findByMonthlyData(MonthlyData monthlyData);
}
