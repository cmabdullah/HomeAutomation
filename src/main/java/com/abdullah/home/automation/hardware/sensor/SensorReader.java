package com.abdullah.home.automation.hardware.sensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SensorReader extends Thread {

    private static final Logger log = LoggerFactory.getLogger(SensorReader.class);

    private DHT22 dht22Indoor = null;
    private DHT22 dht22OutDoor = null;
    private Bmp180 bmp180 = null;

    public SensorReader(DHT22 dht22Indoor, DHT22 dht22OutDoor, Bmp180 bmp180) {
        this.dht22Indoor = dht22Indoor;
        this.dht22OutDoor = dht22OutDoor;
        this.bmp180 = bmp180;
    }

    @Override
    public void run() {

        while (true) {

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                log.error(e.getLocalizedMessage());
            }

            log.debug("Indoor " + "Temperature " + dht22Indoor.getTemperature() + " Humidity " + dht22Indoor.getHumidity() + " counter : " + dht22Indoor.getCounter());
            log.debug("OutDoor " + "Temperature " + dht22OutDoor.getTemperature() + " Humidity " + dht22OutDoor.getHumidity() + " counter : " + dht22OutDoor.getCounter());
            log.debug("OutDoor " + "Temperature " + bmp180.getActualTemperature() + " Pressure " + bmp180.getActualPressure() + " Altitude " + bmp180.getAltitude() + " counter : " + dht22OutDoor.getCounter());

        }
    }
}
