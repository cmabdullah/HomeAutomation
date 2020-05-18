package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.StationsDto;
import com.abdullah.home.automation.domain.model.Station;
import com.abdullah.home.automation.repository.StationRepository;
import com.abdullah.home.automation.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationServiceImpl implements StationService {


    private final StationRepository stationRepository;

    @Autowired
    StationServiceImpl(StationRepository stationRepository){
        this.stationRepository = stationRepository;
    }

    public List<StationsDto> stationList() throws IOException {

        List<StationsDto> stationDtoList = Files.readAllLines(Paths.get("stations.csv"))
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


        List<Station> stationList = stationDtoList.stream()
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


    public List<Station> findStations(String keyword) {

        List<Station> list = stationRepository.findByStationNameContaining(keyword);
        //list.forEach(n -> System.out.println(n));
        return list;

    }

    @Override
    public Optional<Station> findById(Long stationId) {


        return stationRepository.findById(stationId);
    }

}
