package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.StationsDto;
import com.abdullah.home.automation.domain.model.Station;
import com.abdullah.home.automation.dto.response.StationsResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface StationService {

    List<StationsDto> migrateStationInfo()throws IOException;

    StationsResponseDto findStations(String keyword);

    Optional<Station> findById(Long stationId);
}
