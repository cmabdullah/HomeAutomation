package com.abdullah.home.automation.controller.api;

import com.abdullah.home.automation.dto.response.StationsResponseDto;
import com.abdullah.home.automation.exception.ApiResponse;
import com.abdullah.home.automation.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoriteRestController {

    private final FavoriteService favoriteService;

    private static final Logger log = LoggerFactory.getLogger(FavoriteRestController.class);

    @Autowired
    FavoriteRestController(FavoriteService favoriteService){
        this.favoriteService = favoriteService;
    }

    @GetMapping("/a/favorite")
    ApiResponse<?> fevA(@RequestParam long id) {
        Long stationId = id;
        favoriteService.addFavoriteBasedOnStationId(stationId);
        StationsResponseDto stationsResponseDto = favoriteService.findActiveFavorite();
        return new ApiResponse<>(stationsResponseDto);
    }

    @GetMapping("/a/delFavorite")
    ApiResponse<?> delFevA(@RequestParam long id) {
        favoriteService.removeFavoriteBasedOnStationId(id);
        StationsResponseDto stationsResponseDto = favoriteService.findActiveFavorite();
        return new ApiResponse<>(stationsResponseDto);
    }
}
