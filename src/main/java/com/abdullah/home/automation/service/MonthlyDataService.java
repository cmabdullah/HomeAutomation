package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.MonthlyData;
import com.abdullah.home.automation.domain.Station;

import java.util.Optional;

public interface MonthlyDataService {

    MonthlyData save(MonthlyData monthlyData);

    MonthlyData findByStationAndPayloadMonth(Station stationInfo, String filterDate);
}
