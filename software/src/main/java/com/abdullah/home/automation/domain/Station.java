package com.abdullah.home.automation.domain;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
@Entity
public class Station implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationId;
    private String country;
    private String stationName;
    private String number1;
    private String number2;
    private String quantity;
    private String stationKey;
    private String stationId2;
    private String key2;
    private String state;


    public Station() {

    }

    public Station(Long id) {
        this.id = id;
    }


    public Station(String stationId, String country, String stationName,
                   String number1, String number2, String quantity, String stationKey,
                   String stationId2, String key2, String state ) {

        this.stationId = stationId;
        this.country = country;
        this.stationName = stationName;
        this.number1 = number1;
        this.number2 = number2;
        this.quantity = quantity;
        this.stationKey = stationKey;
        this.stationId2 = stationId2;
        this.key2 = key2;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStationKey() {
        return stationKey;
    }

    public void setStationKey(String stationKey) {
        this.stationKey = stationKey;
    }

    public String getStationId2() {
        return stationId2;
    }

    public void setStationId2(String stationId2) {
        this.stationId2 = stationId2;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", stationId='" + stationId + '\'' +
                ", country='" + country + '\'' +
                ", stationName='" + stationName + '\'' +
                ", number1='" + number1 + '\'' +
                ", number2='" + number2 + '\'' +
                ", quantity='" + quantity + '\'' +
                ", stationKey='" + stationKey + '\'' +
                ", stationId2='" + stationId2 + '\'' +
                ", key2='" + key2 + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return getId().equals(station.getId()) &&
                getStationId().equals(station.getStationId()) &&
                getCountry().equals(station.getCountry()) &&
                getStationName().equals(station.getStationName()) &&
                getNumber1().equals(station.getNumber1()) &&
                getNumber2().equals(station.getNumber2()) &&
                getQuantity().equals(station.getQuantity()) &&
                getStationKey().equals(station.getStationKey()) &&
                getStationId2().equals(station.getStationId2()) &&
                getKey2().equals(station.getKey2()) &&
                getState().equals(station.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStationId(), getCountry(), getStationName(), getNumber1(), getNumber2(), getQuantity(), getStationKey(), getStationId2(), getKey2(), getState());
    }
}

