package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.domain.model.Favorite;
import com.abdullah.home.automation.domain.model.Station;
import com.abdullah.home.automation.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    FavoriteController(FavoriteService favoriteService){
        this.favoriteService = favoriteService;
    }

    @GetMapping("/favorite")
    String fev(Model model, @RequestParam long id) {
        Long stationId = id;
        favoriteService.addFavoriteBasedOnStationId(stationId);
        log.info("id : "+ id);
        List<Favorite> feb = favoriteService.findActiveFavorite();
        List<Station> stationList = feb.stream().filter(Objects::nonNull).map(n-> n.getStation()).collect(Collectors.toList());
        stationList.forEach(n -> log.info(n.toString()));
        model.addAttribute("stationList", stationList);
        return "stations";
    }

    @GetMapping("/delFavorite")
    String delFev(Model model, @RequestParam long id) {
        Long stationId = id;
        favoriteService.removeFavoriteBasedOnStationId(stationId);
        System.out.println("id : "+ id);
        List<Favorite> feb = favoriteService.findActiveFavorite();
        List<Station> stationList = feb.stream().filter(Objects::nonNull).map(n-> n.getStation()).collect(Collectors.toList());
        stationList.forEach(n -> log.info(n.toString()));
        model.addAttribute("stationList", stationList);
        return "stations";
    }
}
