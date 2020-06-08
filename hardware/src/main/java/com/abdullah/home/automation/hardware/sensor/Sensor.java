package com.abdullah.home.automation.hardware.sensor;

import com.abdullah.home.automation.hardware.model.WeatherParameter;

public interface Sensor {

    WeatherParameter readWeatherData() throws Exception;
}
