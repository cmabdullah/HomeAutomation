package com.abdullah.home.automation.hardware.sensor;

import com.abdullah.home.automation.hardware.model.WeatherParameter;
import com.abdullah.home.automation.hardware.sensor.dth22.Dth22Reader;
import com.pi4j.wiringpi.Gpio;

import java.util.concurrent.TimeoutException;

public class SensorContext {

    private Sensor sensor;

    public void setSensor(Sensor sensor){
        this.sensor = sensor;
    }

    public void read(){

        if (sensor != null && sensor instanceof Dth22Reader){
            readFromDth22();
        }
    }

    private void readFromDth22(){

        System.out.println("Starting DHT22");
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println("GPIO wiringPiSetup Failed!");
            return;
        }

        int LOOP_SIZE = 10;
        int countSuccess = 0;
        WeatherParameter weatherParameter = null;
        for (int i=0; i < LOOP_SIZE; i++) {
            try {
                Thread.sleep(3000);
                System.out.println();
                weatherParameter = sensor.readWeatherData();
                countSuccess++;
            } catch (TimeoutException e) {
                System.out.println("main TimeoutException -> ERROR: " + e);
                if (weatherParameter != null){
                    System.out.println("main time err weatherParameter ");
                }
            } catch (Exception e) {
                System.out.println("main Exception -> ERROR: " + e);
                if (weatherParameter != null){
                    System.out.println("main time err Exception ");
                }
            }finally {

                if (weatherParameter != null){
                    System.out.println("weatherParameter : "+ weatherParameter.toString());
                    System.out.println("Humidity=" + weatherParameter.getHumidity() + "%, Temperature=" + weatherParameter.getTemperature() + "*C");
                }

            }
        }
        System.out.println("Read success rate: "+ countSuccess + " / " + LOOP_SIZE);
        System.out.println("Ending DHT22");

    }
}
