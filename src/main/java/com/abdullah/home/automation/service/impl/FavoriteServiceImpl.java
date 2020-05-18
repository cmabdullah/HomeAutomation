package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.model.Favorite;
import com.abdullah.home.automation.domain.model.Station;
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

    @Autowired
    FavoriteServiceImpl(StationService stationService, FavoriteRepository favoriteRepository){
        this.stationService = stationService;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void addFavoriteBasedOnStationId(Long stationId) {

        Optional<Station> station = stationService.findById(stationId);
        station.ifPresent(st -> {
            Optional<Favorite> exist = favoriteRepository.findByStationId(stationId);

            if (exist.isEmpty()){
                Favorite favorite = favoriteRepository.save(new Favorite(st, true));
                log.debug("favorite saved : " + favorite.toString());
            }else{
                if (exist.get().isActive() == false){
                    Favorite fev = exist.get();
                    fev.setActive(true);
                    Favorite favorite = favoriteRepository.save(fev);
                    log.debug("favorite update : " + favorite.toString());
                }
                log.debug("favorite exist : "+ exist.toString());
            }
        });
    }

    @Override
    public void removeFavoriteBasedOnStationId(Long stationId) {
        Optional<Station> station = stationService.findById(stationId);
        station.ifPresent(st -> {
            Optional<Favorite> exist = favoriteRepository.findByStationId(stationId);

            if (!exist.isEmpty()){
                Favorite fev = exist.get();
                fev.setActive(false);
                Favorite favorite = favoriteRepository.save(fev);
                log.debug("favorite deactivate : " + favorite.toString());
            }else{
                log.debug("favorite already deactivated : "+ exist.toString());
            }
        });
    }

    @Override
    public List<Favorite> findActiveFavorite() {
        List<Favorite> list = (List<Favorite>) favoriteRepository.findAll();
        List<Favorite> filterList = list.stream().filter(Objects::nonNull).filter(n -> n.isActive()==true).collect(Collectors.toList());
        return filterList;
    }

    @Override
    public List<String> getFavoriteStations() {

        List<Favorite> list = (List<Favorite>)favoriteRepository.findAll();
        return list.stream().filter(Objects::nonNull)
                .map(station -> station.getStation().getStationId()+"\t"+
                        station.getStation().getCountry()+"\t"+
                        station.getStation().getStationName()+"\t"+
                        station.getStation().getState()).collect(Collectors.toList());
        //"41923\tBD\tDhaka\tAsia/Dhaka"
    }
}
