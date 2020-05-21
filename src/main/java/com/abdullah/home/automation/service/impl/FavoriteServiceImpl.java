package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.model.Favorite;
import com.abdullah.home.automation.domain.model.Station;
import com.abdullah.home.automation.dto.response.StationInfoDto;
import com.abdullah.home.automation.dto.response.StationsResponseDto;
import com.abdullah.home.automation.repository.FavoriteRepository;
import com.abdullah.home.automation.service.FavoriteService;
import com.abdullah.home.automation.service.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService {


    private final StationService stationService;

    private final FavoriteRepository favoriteRepository;

    private final List<String> payloadTypes = List.of("humidity", "temperature", "pressure", "winddirection", "windspeed", "precipitation", "dewpoint");


    @Autowired
    FavoriteServiceImpl(StationService stationService, FavoriteRepository favoriteRepository) {
        this.stationService = stationService;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void addFavoriteBasedOnStationId(Long stationId) {

        Optional<Station> station = stationService.findById(stationId);
        station.ifPresent(st -> {
            Optional<Favorite> exist = favoriteRepository.findByStationId(stationId);

            if (exist.isEmpty()) {
                Favorite favorite = favoriteRepository.save(new Favorite(st, true));
                log.debug("favorite saved : " + favorite.toString());
            } else {
                if (exist.get().isActive() == false) {
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

            if (!exist.isEmpty()) {
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
    public StationsResponseDto findActiveFavorite() {
        List<Favorite> list = (List<Favorite>) favoriteRepository.findAll();
        List<Station> filterList = list.stream().filter(Objects::nonNull).filter(n -> n.isActive() == true)
                .map(n -> n.getStation())
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

        List<String> favoriteStations= list.stream().filter(Objects::nonNull)
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
