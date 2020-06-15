package com.abdullah.home.automation.controller.api;

import com.abdullah.home.automation.dto.request.SearchKeyword;
import com.abdullah.home.automation.dto.response.StationsResponseDto;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.exception.ApiMessage;
import com.abdullah.home.automation.exception.ApiResponse;
import com.abdullah.home.automation.service.FavoriteService;
import com.abdullah.home.automation.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class StationRestController {

    private final StationService stationService;

    private final FavoriteService favoriteService;

    private static final Logger log = LoggerFactory.getLogger(StationRestController.class);

    @Autowired
    StationRestController(StationService stationService, FavoriteService favoriteService) {
        this.stationService = stationService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/a/stations")
    ApiResponse<?> stations() {
        StationsResponseDto stationsResponseDto = favoriteService.findActiveFavorite();
        stationsResponseDto.setStationSearch(true);
        return new ApiResponse<>(stationsResponseDto);
    }

    @PostMapping("/a/stations")
    ApiResponse<?> stationsPost(@RequestBody SearchKeyword searchKeyword) {
        //need to filter from existing data

        String keyword = "";

        if (searchKeyword == null) {
            throw new ApiError(ApiMessage.STATION_SEARCH_KEYWORD_CANNOT_BE_EMPTY, HttpStatus.BAD_REQUEST);
        } else {
            if (searchKeyword.getKeyword() == null || searchKeyword.getKeyword().isEmpty()) {
                throw new ApiError(ApiMessage.STATION_SEARCH_KEYWORD_CANNOT_BE_EMPTY, HttpStatus.BAD_REQUEST);
            } else {
                keyword = searchKeyword.getKeyword();
            }
        }
        log.info("keyword " + keyword);
        StationsResponseDto stationsResponseDto = stationService.findStations(keyword);
        return new ApiResponse<>(stationsResponseDto);
    }
}
