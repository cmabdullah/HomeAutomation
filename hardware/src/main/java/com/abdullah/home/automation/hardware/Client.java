package com.abdullah.home.automation.hardware;

import com.abdullah.home.automation.hardware.config.*;
import com.abdullah.home.automation.hardware.sensor.SensorContext;
import com.abdullah.home.automation.hardware.sensor.dth22.Dth22Reader;

public class Client {

    public static void main(String[] args) {
        SensorContext sensorContext = new SensorContext();
        sensorContext.setSensor(new Dth22Reader(PinConfig.DTH_22_PIN_CONFIG.getAddress()));
//        weatherContext.setSensor(Dth22Reader.getInstance(PinConfig.DTH_22_PIN_CONFIG.getAddress()));
        sensorContext.read();
    }
}
