package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.SensorReading;

import java.util.List;

public interface SensorReadingService {

    SensorReading save(SensorReading sensorReading);
    List<SensorReading> save(List<SensorReading> sensorReading);

}
