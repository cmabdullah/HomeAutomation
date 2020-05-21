package com.abdullah.home.automation.service;

import com.abdullah.home.automation.dto.request.FilterDto;
import com.abdullah.home.automation.domain.Payload2;
import com.abdullah.home.automation.dto.response.WeatherResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface WeatherService {

    WeatherResponseDto postWeatherRequest();
    WeatherResponseDto postWeatherRequest(FilterDto filterDto);
    WeatherResponseDto processStaticData(FilterDto filterDto);
    List<Payload2> postWeatherRequest(FilterDto filterDto, LocalDate targetDate, LocalDate firstDayOfMonth, String payloadType);

    List<Payload2> processStaticData(FilterDto filterDto, LocalDate targetDate, LocalDate firstDayOfMonth, String payloadType);

    List<String> pathList();
}
