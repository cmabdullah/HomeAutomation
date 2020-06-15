package com.abdullah.home.automation.controller.api;

import com.abdullah.home.automation.dto.request.FilterDto;
import com.abdullah.home.automation.dto.response.StationInfoDto;
import com.abdullah.home.automation.dto.response.WeatherResponseDto;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.exception.ApiMessage;
import com.abdullah.home.automation.exception.ApiResponse;
import com.abdullah.home.automation.service.FavoriteService;
import com.abdullah.home.automation.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RemoteRestController {

    private final WeatherService weatherService;

    private final FavoriteService favoriteService;

    private static final Logger log = LoggerFactory.getLogger(RemoteRestController.class);

    @Autowired
    RemoteRestController(WeatherService weatherService, FavoriteService favoriteService) {
        this.weatherService = weatherService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/a/weather")
    ApiResponse<?> weatherGet() {
        WeatherResponseDto weatherResponseDto = weatherService.postWeatherRequest();
        return new ApiResponse<>(weatherResponseDto);
    }

    @GetMapping("/a/remote")
    ApiResponse<?> remoteGet() {
        StationInfoDto stationInfoDto = favoriteService.getFavoriteStations();
        return new ApiResponse<>(stationInfoDto);
    }

    @PostMapping("/a/remote")
    ApiResponse<?> remotePost(@RequestBody @Valid FilterDto filterDto, BindingResult result) {
        log.info("filterDto "+ filterDto);

        if (result.hasErrors()) {
            throw new ApiError(ApiMessage.WEATHER_INFORMATION_SEARCH_INPUT_VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
        }

        WeatherResponseDto weatherResponseDto = weatherService.postWeatherRequest(filterDto);
        return new ApiResponse<>(weatherResponseDto);
    }

    @GetMapping("/a/staticFind")
    ApiResponse<?>  acceptDate() {
        return new ApiResponse<>(weatherService.pathList());
    }

    @PostMapping("/a/staticFind")
    ApiResponse<?>  hello(@RequestBody  @Valid FilterDto filterDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ApiError(ApiMessage.WEATHER_INFORMATION_SEARCH_INPUT_VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
        }

        WeatherResponseDto weatherResponseDto = weatherService.processStaticData(filterDto);
        return new ApiResponse<>(weatherResponseDto);
    }
}
