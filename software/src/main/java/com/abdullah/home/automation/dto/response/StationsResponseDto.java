package com.abdullah.home.automation.dto.response;

import com.abdullah.home.automation.domain.Station;
//import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//@Data
public class StationsResponseDto implements Serializable {
    private List<Station> stationList;
    private boolean add;
    private boolean delete;
    private boolean stationSearch;

    public StationsResponseDto() {
    }

    public StationsResponseDto(List<Station> stationList, boolean add, boolean delete, boolean stationSearch) {
        this.stationList = stationList;
        this.add = add;
        this.delete = delete;
        this.stationSearch = stationSearch;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isStationSearch() {
        return stationSearch;
    }

    public void setStationSearch(boolean stationSearch) {
        this.stationSearch = stationSearch;
    }

    @Override
    public String toString() {
        return "StationsResponseDto{" +
                "stationList=" + stationList +
                ", add=" + add +
                ", delete=" + delete +
                ", stationSearch=" + stationSearch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationsResponseDto)) return false;
        StationsResponseDto that = (StationsResponseDto) o;
        return isAdd() == that.isAdd() &&
                isDelete() == that.isDelete() &&
                isStationSearch() == that.isStationSearch() &&
                Objects.equals(getStationList(), that.getStationList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStationList(), isAdd(), isDelete(), isStationSearch());
    }
}
