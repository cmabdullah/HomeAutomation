package com.abdullah.home.automation.dto.response;

//import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//@Data
public class WeatherResponseDto implements Serializable {

    private String filtarDate;
    private String payloadType;
    private List<Payload2> payload2List;


    public WeatherResponseDto() {
    }

    public WeatherResponseDto(String filtarDate, String payloadType, List<Payload2> payload2List) {
        this.filtarDate = filtarDate;
        this.payloadType = payloadType;
        this.payload2List = payload2List;
    }

    public String getFiltarDate() {
        return filtarDate;
    }

    public void setFiltarDate(String filtarDate) {
        this.filtarDate = filtarDate;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(String payloadType) {
        this.payloadType = payloadType;
    }

    public List<Payload2> getPayload2List() {
        return payload2List;
    }

    public void setPayload2List(List<Payload2> payload2List) {
        this.payload2List = payload2List;
    }

    @Override
    public String toString() {
        return "WeatherResponseDto{" +
                "filtarDate='" + filtarDate + '\'' +
                ", payloadType='" + payloadType + '\'' +
                ", payload2List=" + payload2List +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherResponseDto)) return false;
        WeatherResponseDto that = (WeatherResponseDto) o;
        return Objects.equals(getFiltarDate(), that.getFiltarDate()) &&
                Objects.equals(getPayloadType(), that.getPayloadType()) &&
                Objects.equals(getPayload2List(), that.getPayload2List());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFiltarDate(), getPayloadType(), getPayload2List());
    }
}
