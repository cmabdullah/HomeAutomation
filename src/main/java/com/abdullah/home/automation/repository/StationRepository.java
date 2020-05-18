package com.abdullah.home.automation.repository;

import com.abdullah.home.automation.domain.model.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StationRepository extends CrudRepository<Station, Long> {

    List<Station> findByStationNameContaining(String title);
}
