package com.abdullah.home.automation.dto.response;

import java.io.Serializable;
import java.util.List;

//@Data
public class StationInfoDto implements Serializable {
    private List<String> stations ;
    private List<String> payloadTypes;

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public List<String> getPayloadTypes() {
        return payloadTypes;
    }

    public void setPayloadTypes(List<String> payloadTypes) {
        this.payloadTypes = payloadTypes;
    }

    public StationInfoDto() {
    }

    public StationInfoDto(List<String> stations, List<String> payloadTypes) {
        this.stations = stations;
        this.payloadTypes = payloadTypes;
    }
}
