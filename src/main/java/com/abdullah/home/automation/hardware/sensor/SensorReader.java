package com.abdullah.home.automation.hardware.sensor;

import com.abdullah.home.automation.config.Mqtt;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

class SensorReader extends Thread {

    private static final Logger log = LoggerFactory.getLogger(SensorReader.class);

    private Bmp180 bmp180 = null;

    public SensorReader(Bmp180 bmp180) {

        this.bmp180 = bmp180;
    }

    @Override
    public void run() {

        try{
            log.debug("SensorReader is going to sleep for a minute ");
            Thread.sleep(60000);

        }catch (InterruptedException e){
            log.error("Sensor Initial Interrupted Exception "+e.getLocalizedMessage());
        }


        while (true) {

            try {

                LocalDateTime localDateTime = LocalDateTime.now();
                Instant instant = localDateTime.atZone(ZoneId.of("Asia/Dhaka")).toInstant();
                long timeInMillis = instant.toEpochMilli();
                String message = "OutdoorBmp180 Temp="+ String.format("%.1f", bmp180.getActualTemperature())+
                        "  Pressure="+String.format("%.1f", bmp180.getActualPressure())+
                        " Altitude="+ String.format("%.1f", bmp180.getAltitude())+" PublishTime="+ timeInMillis;
                //log.debug(message);

                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                mqttMessage.setQos(1);
                mqttMessage.setRetained(true);

                Mqtt.getInstance().publish("weather", mqttMessage);

                Thread.sleep(2500);
            } catch (InterruptedException e) {
                log.error(e.getLocalizedMessage());
            } catch (Exception e){
                log.error("Bmp/mqtt error "+ e.getLocalizedMessage());
            }
        }
    }
}