package com.abdullah.home.automation.service;

import com.abdullah.home.automation.dto.response.StationInfoDto;
import com.abdullah.home.automation.dto.response.StationsResponseDto;

public interface FavoriteService {
    void addFavoriteBasedOnStationId(Long stationId);

    StationsResponseDto findActiveFavorite();

    StationInfoDto getFavoriteStations();

    void removeFavoriteBasedOnStationId(Long stationId);
}
