package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.model.Favorite;

import java.util.List;

public interface FavoriteService {
    void addFavoriteBasedOnStationId(Long stationId);

    List<Favorite> findActiveFavorite();

    List<String> getFavoriteStations();

    void removeFavoriteBasedOnStationId(Long stationId);
}
