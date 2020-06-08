package com.abdullah.home.automation.hardware.model;

import java.util.Arrays;
import java.util.Objects;

public class WeatherParameter {


    /**
     * PI4J Pin number.
     */
    public int pinNumber;

    /**
     * 40 bit Data from sensor
     */
    public  byte[] data ;

    /**
     * Value of last successful humidity reading.
     */
    public Double humidity ;

    /**
     * Value of last successful temperature reading.
     */
    public Double temperature ;

    /**
     * Last read attempt
     */
    public Long lastRead ;

    /**
     * Constructor with pin used for signal.  See PI4J and WiringPI for
     * pin numbering systems.....
     *
     * @param pin
     */
    public WeatherParameter(int pinAddress) {
        this.pinNumber = pinAddress;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Long getLastRead() {
        return lastRead;
    }

    public void setLastRead(Long lastRead) {
        this.lastRead = lastRead;
    }

    @Override
    public String toString() {
        return "WeatherParameter{" +
                "pinNumber=" + pinNumber +
                ", data=" + Arrays.toString(data) +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                ", lastRead=" + lastRead +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherParameter)) return false;
        WeatherParameter that = (WeatherParameter) o;
        return getPinNumber() == that.getPinNumber() &&
                Arrays.equals(getData(), that.getData()) &&
                Objects.equals(getHumidity(), that.getHumidity()) &&
                Objects.equals(getTemperature(), that.getTemperature()) &&
                Objects.equals(getLastRead(), that.getLastRead());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getPinNumber(), getHumidity(), getTemperature(), getLastRead());
        result = 31 * result + Arrays.hashCode(getData());
        return result;
    }
}
