package com.abdullah.home.automation.service;

import java.util.List;

import com.abdullah.home.automation.domain.Payload2;
import com.abdullah.home.automation.domain.WeatherData;

public interface PayloadService {
	List<Payload2> buildPayloadFromList(List<WeatherData> weatherDataSet, String payloadType);
}
