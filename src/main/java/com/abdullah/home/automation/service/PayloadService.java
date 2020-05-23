package com.abdullah.home.automation.service;

import java.util.List;

import com.abdullah.home.automation.domain.MonthlyData;
import com.abdullah.home.automation.domain.Payload;
import com.abdullah.home.automation.domain.WeatherEntity;
import com.abdullah.home.automation.dto.response.Payload2;
import com.abdullah.home.automation.dto.meteostat.WeatherData;

public interface PayloadService {
	void saveAll(List<Payload> payloads);
	List<Payload2> buildPayloadFromList(List<WeatherData> weatherData, String payloadType);

    List<Payload> findByMonthlyDataAndWeatherEntity(MonthlyData monthlyData, WeatherEntity weatherEntity);

	List<Payload> findByMonthlyData(MonthlyData monthlyDataFromDb);
}
