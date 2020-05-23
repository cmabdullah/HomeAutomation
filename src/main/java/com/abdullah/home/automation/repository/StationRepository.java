package com.abdullah.home.automation.repository;

import com.abdullah.home.automation.domain.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends CrudRepository<Station, Long> {

    List<Station> findByStationNameContaining(String title);

    Optional<Station> findByStationId(String stationId);
}
