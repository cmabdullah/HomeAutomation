package com.abdullah.home.automation.repository;

import com.abdullah.home.automation.domain.model.Favorite;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FavoriteRepository extends CrudRepository<Favorite, Long> {

    Optional<Favorite> findByStationId(Long stationId);
}
