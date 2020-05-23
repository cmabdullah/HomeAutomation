package com.abdullah.home.automation.service;

import com.abdullah.home.automation.dto.request.StationsDto;
import com.abdullah.home.automation.domain.Station;
import com.abdullah.home.automation.dto.response.StationsResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface StationService {

    List<StationsDto> migrateStationInfo()throws IOException;

    StationsResponseDto findStations(String keyword);

    Optional<Station> findById(Long id);

    Optional<Station> findByStationId(String stationId);
}
