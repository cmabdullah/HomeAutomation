package com.abdullah.home.automation.hardware.sensor;

import com.abdullah.home.automation.hardware.config.GpioConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SensorContextImpl implements SensorContext {

    private static final Logger log = LoggerFactory.getLogger(SensorContextImpl.class);

    @Override
    public boolean runAllWeatherSensor() {

        Bmp180 bmp180 = new Bmp180();

        Thread threadOutDoorBmp = new Thread(bmp180);
        threadOutDoorBmp.setName("threadOutDoorBmp");
        threadOutDoorBmp.start();

        SensorReader sensorReader = new SensorReader(bmp180);
        Thread threadSensorReader = new Thread(sensorReader);
        threadSensorReader.start();
        threadSensorReader.setName("threadSensorReader");

        try {

            threadOutDoorBmp.join();
            threadSensorReader.join();
        } catch (InterruptedException e) {
            log.error(e.getLocalizedMessage());
        }

        return threadOutDoorBmp.isAlive();
    }
}
