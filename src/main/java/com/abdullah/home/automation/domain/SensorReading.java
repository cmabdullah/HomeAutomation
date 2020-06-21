package com.abdullah.home.automation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sensorName;
    private float temperature;
    private float humidity;
    private float pressure;
    private float altitude;
    private long publishTime;

    public String getSensorName() {
        return sensorName;
    }

    public SensorReading setSensorName(String sensorName) {
        this.sensorName = sensorName;
        return this;
    }

    public float getTemperature() {
        return temperature;
    }

    public SensorReading setTemperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    public float getHumidity() {
        return humidity;
    }

    public SensorReading setHumidity(float humidity) {
        this.humidity = humidity;
        return this;
    }

    public float getPressure() {
        return pressure;
    }

    public SensorReading setPressure(float pressure) {
        this.pressure = pressure;
        return this;
    }

    public float getAltitude() {
        return altitude;
    }

    public SensorReading setAltitude(float altitude) {
        this.altitude = altitude;
        return this;
    }

    public double getPublishTime() {
        return publishTime;
    }

    public SensorReading setPublishTime(long publishTime) {
        this.publishTime = publishTime;
        return this;
    }

    @Override
    public String toString() {
        return "SensorReading{" +
                "id=" + id +
                ", sensorName='" + sensorName + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", altitude=" + altitude +
                ", publishTime=" + publishTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorReading)) return false;
        SensorReading that = (SensorReading) o;
        return Float.compare(that.getTemperature(), getTemperature()) == 0 &&
                Float.compare(that.getHumidity(), getHumidity()) == 0 &&
                Float.compare(that.getPressure(), getPressure()) == 0 &&
                Float.compare(that.getAltitude(), getAltitude()) == 0 &&
                Double.compare(that.getPublishTime(), getPublishTime()) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(getSensorName(), that.getSensorName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getSensorName(), getTemperature(), getHumidity(), getPressure(), getAltitude(), getPublishTime());
    }
}
