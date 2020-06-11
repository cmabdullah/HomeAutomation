package com.abdullah.home.automation.repository;

import com.abdullah.home.automation.domain.MonthlyData;
import com.abdullah.home.automation.domain.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MonthlyDataRepository extends CrudRepository<MonthlyData, Long> {
    MonthlyData findByStationAndPayloadMonth(Station stationInfo, String filterDate);
}
