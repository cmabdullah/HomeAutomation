package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.dto.response.StationsResponseDto;
import com.abdullah.home.automation.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;

    private static final Logger log = LoggerFactory.getLogger(FavoriteController.class);

    @Autowired
    FavoriteController(FavoriteService favoriteService){
        this.favoriteService = favoriteService;
    }

    @GetMapping("/favorite")
    String fev(Model model, @RequestParam long id) {
        Long stationId = id;
        favoriteService.addFavoriteBasedOnStationId(stationId);
        StationsResponseDto stationsResponseDto = favoriteService.findActiveFavorite();
        model.addAttribute("stationsResponseDto", stationsResponseDto);
        return "redirect:/stations";
    }

    @GetMapping("/delFavorite")
    String delFev(Model model, @RequestParam long id) {
        favoriteService.removeFavoriteBasedOnStationId(id);
        StationsResponseDto stationsResponseDto = favoriteService.findActiveFavorite();
        model.addAttribute("stationsResponseDto", stationsResponseDto);
        return "redirect:/stations";
    }
}
