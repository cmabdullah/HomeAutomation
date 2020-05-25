package com.abdullah.home.automation.dto.response;

import com.abdullah.home.automation.domain.Station;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StationsResponseDto implements Serializable {
    private List<Station> stationList;
    private boolean add;
    private boolean delete;
    private boolean stationSearch;
}
