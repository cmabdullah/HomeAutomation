package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.dto.request.StationsDto;
import com.abdullah.home.automation.domain.Station;
import com.abdullah.home.automation.dto.response.StationsResponseDto;
import com.abdullah.home.automation.repository.StationRepository;
import com.abdullah.home.automation.service.FavoriteService;
import com.abdullah.home.automation.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationServiceImpl implements StationService {


    private final StationRepository stationRepository;
    private final FavoriteService favoriteService;

    private static final Logger log = LoggerFactory.getLogger(StationServiceImpl.class);

    @Autowired
    public StationServiceImpl(StationRepository stationRepository
                       ,@Lazy FavoriteService favoriteService
    ){
        this.stationRepository = stationRepository;
        this.favoriteService = favoriteService;
    }

    public List<StationsDto> migrateStationInfo(){

        //delete all info first
        stationRepository.deleteAll();

        List<StationsDto> stationDtoList = new ArrayList<>();
        try {
            stationDtoList = Files.readAllLines(Paths.get("stations.csv"))
                    .stream().map(line -> {
                        String[] split = line.split(",");

                        return new StationsDto(
                                split.length >= 1 ? split[0] : "",
                                split.length >= 2 ? split[1] : "",
                                split.length >= 3 ? split[2] : "",
                                split.length >= 4 ? split[3] : "",
                                split.length >= 5 ? split[4] : "",
                                split.length >= 6 ? split[5] : "",
                                split.length >= 7 ? split[6] : "",
                                split.length >= 8 ? split[7] : "",
                                split.length >= 9 ? split[8] : "",
                                split.length >= 10 ? split[9] : "");
                    }).collect(Collectors.toList());

        }catch(IOException e){
            log.error("Station not found");
        }

        List<Station> stationList = stationDtoList.stream().filter(Objects::nonNull)
                .map((stationDto ->
                        new Station(stationDto.getStationId() ,
                                 stationDto.getCountry(),
                                 stationDto.getStationName(),
                                 stationDto.getNumber1(),
                                 stationDto.getNumber2(),
                                 stationDto.getQuantity(),
                                 stationDto.getStationKey(),
                                 stationDto.getStationId2(),
                                 stationDto.getKey2(),
                                 stationDto.getState())
                ))
                .collect(Collectors.toList());
        stationRepository.saveAll(stationList);

        return stationDtoList;

    }


    public StationsResponseDto findStations(String keyword) {

        List<Station> stations = stationRepository.findByStationNameContaining(keyword);

        List<Station> favoriteActiveStation = favoriteService.favoriteActiveStation();

        List<Station> filteredStations  = stations.stream().filter(Objects::nonNull)
                .filter(n -> !removeActiveStation( n, favoriteActiveStation)).collect(Collectors.toUnmodifiableList());

        StationsResponseDto stationsResponseDto = new StationsResponseDto();
        stationsResponseDto.setStationList(filteredStations);
        stationsResponseDto.setDelete(false);
        stationsResponseDto.setAdd(true);
        stationsResponseDto.setStationSearch(true);
        return stationsResponseDto;

    }

    private boolean removeActiveStation(Station station, List<Station> favoriteActiveStation){

        if (favoriteActiveStation != null){
            return favoriteActiveStation.contains(station);
        }
        return false;
    }

    @Override
    public Optional<Station> findById(Long id) {

        return stationRepository.findById(id);
    }

    @Override
    public Optional<Station> findByStationId(String stationId) {

        return stationRepository.findByStationId(stationId);
    }

    @Override
    public void deleteAll() {
        stationRepository.deleteAll();
    }


}
