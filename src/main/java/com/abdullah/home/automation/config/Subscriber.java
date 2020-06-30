package com.abdullah.home.automation.config;

import com.abdullah.home.automation.domain.SensorReading;
import com.abdullah.home.automation.service.SensorReadingService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class Subscriber implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Subscriber.class);

    private final SensorReadingService sensorReadingService;

    @Autowired
    public Subscriber(SensorReadingService sensorReadingService) {
        this.sensorReadingService = sensorReadingService;
    }

    public List<String> subscribeChannel() {
        List<String> messages = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        try {

            Mqtt.getInstance().subscribeWithResponse("weather", (s, mqttMessage) -> {
                messages.add(new String(mqttMessage.getPayload()));
                countDownLatch.countDown();
            });

            countDownLatch.await(10000, TimeUnit.MILLISECONDS);

        } catch (InterruptedException | MqttException e){
            log.error("Mqtt subscribe/Interrupt error " + e.getLocalizedMessage());
        }
        return messages;
    }

    //@Bean
    public void mqttSubConfig() {


        try {

            CountDownLatch countDownLatch = new CountDownLatch(10);
            Mqtt.getInstance().subscribeWithResponse("weather", (topic, mqttMessage) -> {
                log.debug(new String(mqttMessage.getPayload()));

                SensorReading sensorReadingPayload = buildSensorReading(new String(mqttMessage.getPayload()));
                SensorReading sensorReadingSaved = null;
                if (sensorReadingPayload != null) {
                    sensorReadingSaved = sensorReadingService.save(sensorReadingPayload);
                }
                try {
                    if (sensorReadingSaved == null) {
                        throw new RuntimeException("Sensor reading save failed");
                    }
                } catch (Exception e) {
                    log.error("sensorReading save failed");
                }

                countDownLatch.countDown();
            });

        } catch (
                Exception e) {
            log.error("Mqtt subscribe error " + e.getLocalizedMessage());
        }
    }


    private SensorReading buildSensorReading(String str) {
        String[] arr1 = str.split("\\s+");
        String sensorType = arr1[0].trim();
        String temp = arr1[1].trim();

        if (arr1.length == 4) {
            String humidity = arr1[2].trim();
            String publishTime = arr1[3].trim();
            return new SensorReading()
                    .setSensorName(sensorType)
                    .setTemperature(Float.parseFloat(temp.substring(5)))
                    .setHumidity(Float.parseFloat(humidity.substring(9)))
                    .setPublishTime(Long.parseLong(publishTime.substring(12)));

        } else if (arr1.length == 5) {
            String pressure = arr1[2].trim();
            String altitude = arr1[3].trim();
            String publishTime = arr1[4].trim();
            return new SensorReading()
                    .setSensorName(sensorType)
                    .setTemperature(Float.parseFloat(temp.substring(5)))
                    .setPressure(Float.parseFloat(pressure.substring(9)))
                    .setAltitude(Float.parseFloat(altitude.substring(9)))
                    .setPublishTime(Long.parseLong(publishTime.substring(12)));

        } else {
            throw new RuntimeException("Sensor data parsing error");
        }
    }

    //@SneakyThrows
    @Override
    public void run() {
        while (true) {
            // Do something

            List<String> weatherSensorsReading = subscribeChannel();

            Map<String, String> map = new HashMap<>();

            for (String str: weatherSensorsReading) {
                if (str.startsWith("OutdoorBmp180")) {
                    map.put("OutdoorBmp180", str);
                } else if (str.startsWith("OutdoorDht22")) {
                    map.put("OutdoorDht22", str);
                } else if (str.startsWith("IndoorDht22")) {
                    map.put("IndoorDht22", str);
                }
            }

            List<SensorReading> sensorReadingList = map.values().stream().map(this::buildSensorReading).collect(Collectors.toList());
            sensorReadingList.forEach(sensorReading -> log.debug("sensorReading "+sensorReading));

            List<SensorReading> sensorReadingListSaved = null;

            try {
                sensorReadingListSaved = sensorReadingService.save(sensorReadingList);
                if (sensorReadingListSaved == null) {
                    throw new RuntimeException("Sensor reading save failed");
                }
            } catch (Exception e) {
                log.error("sensorReading save failed");
            }

            //System.out.println("simple processor " + LocalDateTime.now());

            try{
                Thread.sleep(50000);
            }catch (InterruptedException e){
                log.error("InterruptedException "+e.getLocalizedMessage());
            }
        }
    }
}
