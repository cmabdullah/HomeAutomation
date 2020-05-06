package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.Stations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationServiceImpl implements StationService {

    public List<Stations> stationList() throws IOException {
        return Files.readAllLines(Paths.get("stations.csv"))
                .stream().map(line -> {
                    String[] split = line.split(",");

                    return new Stations(
                            split.length >= 1 ? split[0] : "",
                            split.length >= 2 ? split[1] : "",
                            split.length >= 3 ? split[1] : "",
                            split.length >= 4 ? split[1] : "",
                            split.length >= 5 ? split[1] : "",
                            split.length >= 6 ? split[1] : "",
                            split.length >= 7 ? split[1] : "",
                            split.length >= 8 ? split[1] : "",
                            split.length >= 9 ? split[1] : "",
                            split.length >= 10 ? split[1] : "");
                }).collect(Collectors.toList());

    }
}
