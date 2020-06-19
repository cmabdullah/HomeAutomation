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
    private double publishTime;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public double getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(double publishTime) {
        this.publishTime = publishTime;
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
