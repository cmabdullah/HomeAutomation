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
    public List<Thread> runAllWeatherSensor() {
        DHT22 dht22Indoor = new DHT22(GpioConfig.DTH_22_PIN_CONFIG_INDOOR.getAddress());
        DHT22 dht22OutDoor = new DHT22(GpioConfig.DTH_22_PIN_CONFIG_OUT_DOOR.getAddress());
        Bmp180 bmp180 = new Bmp180();

        Thread threadIndoor = new Thread(dht22Indoor);
        threadIndoor.setName("threadIndoor");
        Thread threadOutDoor = new Thread(dht22OutDoor);
        threadOutDoor.setName("threadOutDoor");
        Thread threadOutDoorBmp = new Thread(bmp180);
        threadOutDoorBmp.setName("threadOutDoorBmp");
        threadIndoor.start();
        threadOutDoor.start();
        threadOutDoorBmp.start();

        SensorReader sensorReader = new SensorReader(dht22Indoor, dht22OutDoor, bmp180);
        Thread threadSensorReader = new Thread(sensorReader);
        threadSensorReader.start();
        threadSensorReader.setName("threadSensorReader");

        try {
            threadIndoor.join();
            threadOutDoor.join();
            threadOutDoorBmp.join();
            threadSensorReader.join();
        } catch (InterruptedException e) {
            log.error(e.getLocalizedMessage());
        }

        List<Thread> threads = Arrays.asList(threadIndoor,threadOutDoor,threadOutDoorBmp,threadSensorReader);

        return threads;
    }
}
