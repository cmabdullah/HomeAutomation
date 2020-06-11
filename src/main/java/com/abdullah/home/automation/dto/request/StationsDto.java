package com.abdullah.home.automation.dto.request;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class StationsDto implements Serializable {

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

    public StationsDto() {
    }

    public StationsDto(String stationId, String country, String stationName, String number1, String number2, String quantity, String stationKey, String stationId2, String key2, String state) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationsDto)) return false;
        StationsDto that = (StationsDto) o;
        return Objects.equals(getStationId(), that.getStationId()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getStationName(), that.getStationName()) &&
                Objects.equals(getNumber1(), that.getNumber1()) &&
                Objects.equals(getNumber2(), that.getNumber2()) &&
                Objects.equals(getQuantity(), that.getQuantity()) &&
                Objects.equals(getStationKey(), that.getStationKey()) &&
                Objects.equals(getStationId2(), that.getStationId2()) &&
                Objects.equals(getKey2(), that.getKey2()) &&
                Objects.equals(getState(), that.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStationId(), getCountry(), getStationName(), getNumber1(), getNumber2(), getQuantity(), getStationKey(), getStationId2(), getKey2(), getState());
    }
}
