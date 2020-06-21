package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.SensorReading;
import com.abdullah.home.automation.repository.SensorReadingRepository;
import com.abdullah.home.automation.service.SensorReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorReadingServiceImpl implements SensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;

    @Autowired
    public SensorReadingServiceImpl(SensorReadingRepository sensorReadingRepository){
        this.sensorReadingRepository = sensorReadingRepository;
    }

    @Override
    public SensorReading save(SensorReading sensorReading) {
        return sensorReadingRepository.save(sensorReading);
    }
}
