package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.Favorite;
import com.abdullah.home.automation.domain.Station;
import com.abdullah.home.automation.domain.WeatherEntity;
import com.abdullah.home.automation.dto.response.StationInfoDto;
import com.abdullah.home.automation.dto.response.StationsResponseDto;
import com.abdullah.home.automation.repository.FavoriteRepository;
import com.abdullah.home.automation.service.FavoriteService;
import com.abdullah.home.automation.service.StationService;
import com.abdullah.home.automation.service.WeatherEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {


    private final StationService stationService;

    private final FavoriteRepository favoriteRepository;

    private final WeatherEntityService weatherEntityService;

    private static final Logger log = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    @Autowired
    public FavoriteServiceImpl(StationService stationService, FavoriteRepository favoriteRepository,
                        WeatherEntityService weatherEntityService) {
        this.stationService = stationService;
        this.favoriteRepository = favoriteRepository;
        this.weatherEntityService = weatherEntityService;
    }

    @Override
    public void addFavoriteBasedOnStationId(Long stationId) {

        Optional<Station> station = stationService.findById(stationId);
        station.ifPresent(st -> {
            Optional<Favorite> exist = favoriteRepository.findByStationId(stationId);

            if (!exist.isPresent()) {
                Favorite favorite = favoriteRepository.save(new Favorite(st, true));
                log.debug("favorite saved : " + favorite.toString());
            } else {
                if (!exist.get().isActive()) {
                    Favorite fev = exist.get();
                    fev.setActive(true);
                    Favorite favorite = favoriteRepository.save(fev);
                    log.debug("favorite update : " + favorite.toString());
                }
                log.debug("favorite exist : " + exist.toString());
            }
        });
    }

    @Override
    public void removeFavoriteBasedOnStationId(Long stationId) {
        Optional<Station> station = stationService.findById(stationId);
        station.ifPresent(st -> {
            Optional<Favorite> exist = favoriteRepository.findByStationId(stationId);

            if (exist.isPresent()) {
                Favorite fev = exist.get();
                fev.setActive(false);
                Favorite favorite = favoriteRepository.save(fev);
                log.debug("favorite deactivate : " + favorite.toString());
            } else {
                log.debug("favorite already deactivated : " + exist.toString());
            }
        });
    }

    @Override
    public List<Station> favoriteActiveStation() {
        List<Favorite> list = (List<Favorite>) favoriteRepository.findAll();
        return list.stream().filter(Objects::nonNull).filter(Favorite::isActive)
                .map(Favorite::getStation)
                .collect(Collectors.toList());
    }
    @Override
    public StationsResponseDto findActiveFavorite() {
        List<Favorite> list = (List<Favorite>) favoriteRepository.findAll();
        List<Station> filterList = list.stream().filter(Objects::nonNull).filter(Favorite::isActive)
                .map(Favorite::getStation)
                .collect(Collectors.toList());

        StationsResponseDto stationsResponseDto = new StationsResponseDto();

        stationsResponseDto.setStationList(filterList);
        stationsResponseDto.setDelete(true);
        stationsResponseDto.setAdd(false);

        return stationsResponseDto;
    }

    @Override
    public StationInfoDto getFavoriteStations() {

        List<Favorite> list = (List<Favorite>) favoriteRepository.findAll();

        List<WeatherEntity> weatherEntityList = weatherEntityService.findAll();
        List<String> payloadTypes = weatherEntityList.stream().filter(Objects::nonNull).map(WeatherEntity::getEntityName).collect(Collectors.toList());

        List<String> favoriteStations= list.stream().filter(Objects::nonNull)
                .filter(Favorite::isActive)
                .map(station -> station.getStation().getStationId() + "\t" +
                        station.getStation().getCountry() + "\t" +
                        station.getStation().getStationName() + "\t" +
                        station.getStation().getState()).collect(Collectors.toList());

        StationInfoDto stationInfoDto = new StationInfoDto();
        stationInfoDto.setStations(favoriteStations);
        stationInfoDto.setPayloadTypes(payloadTypes);

        return stationInfoDto;
        //"41923\tBD\tDhaka\tAsia/Dhaka"
    }
}
