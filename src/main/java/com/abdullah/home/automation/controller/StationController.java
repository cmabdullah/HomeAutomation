package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.dto.response.StationsResponseDto;
import com.abdullah.home.automation.service.FavoriteService;
import com.abdullah.home.automation.service.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        StationsResponseDto stationsResponseDto = favoriteService.findActiveFavorite();
        stationsResponseDto.setStationSearch(true);
        model.addAttribute("stationsResponseDto", stationsResponseDto);
        return "stations";
    }

    @PostMapping("/stations")
    String stationsPost(Model model, String keyword) {
        //need to filter from existing data
        if (keyword == null || keyword.isEmpty()){
            return "redirect:/stations";
        }
        StationsResponseDto stationsResponseDto = stationService.findStations(keyword);
        model.addAttribute("stationsResponseDto", stationsResponseDto);
        return "stations";
    }

}
