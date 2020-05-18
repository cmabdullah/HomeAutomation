package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.domain.model.Favorite;
import com.abdullah.home.automation.domain.model.Station;
import com.abdullah.home.automation.service.FavoriteService;
import com.abdullah.home.automation.service.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class StationController {

    private final StationService stationService;

    private final FavoriteService favoriteService;

    @Autowired
    StationController( StationService stationService , FavoriteService favoriteService){
        this.stationService = stationService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/stations")
    String stations(Model model) {

        List<Favorite> feb = favoriteService.findActiveFavorite();
        List<Station> stationList = feb.stream().filter(Objects::nonNull).map(n-> n.getStation()).collect(Collectors.toList());
        stationList.forEach(n -> log.info(n.toString()));
        model.addAttribute("stationList", stationList);
        return "stations";
    }

    @PostMapping("/stations")
    String stationsPost(Model model, String keyword) {

        //List<StationsDto> list = stationService.stationList();
        //String keyword = "dhaka";
        List<Station> stationList = stationService.findStations(keyword);
        stationList.forEach(n -> log.info(n.toString()));
        model.addAttribute("stationList", stationList);
        return "stations";
    }

}
