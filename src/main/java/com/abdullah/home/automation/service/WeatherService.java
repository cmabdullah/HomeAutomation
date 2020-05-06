package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.FilterDate;
import com.abdullah.home.automation.domain.Payload2;

import java.time.LocalDate;
import java.util.List;

public interface WeatherService {

    List<Payload2> postWeatherRequest(FilterDate filterDate, LocalDate targetDate, LocalDate firstDayOfMonth, String payloadType);

    List<Payload2> processStaticData(FilterDate filterDate, LocalDate targetDate, LocalDate firstDayOfMonth, String payloadType);

    List<String> pathList();
}
