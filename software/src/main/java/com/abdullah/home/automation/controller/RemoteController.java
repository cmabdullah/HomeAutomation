package com.abdullah.home.automation.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import com.abdullah.home.automation.dto.response.StationInfoDto;
import com.abdullah.home.automation.dto.response.WeatherResponseDto;
import com.abdullah.home.automation.service.FavoriteService;
import com.abdullah.home.automation.service.WeatherService;
import com.abdullah.home.automation.dto.request.FilterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class RemoteController {

    private final WeatherService weatherService;

    private final FavoriteService favoriteService;

    private static final Logger log = LoggerFactory.getLogger(RemoteController.class);

    @Autowired
    RemoteController(WeatherService weatherService, FavoriteService favoriteService) {
        this.weatherService = weatherService;
        this.favoriteService = favoriteService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping("/weather")
    String weatherGet(ModelMap modelMap) {
        WeatherResponseDto weatherResponseDto = weatherService.postWeatherRequest();
        modelMap.addAttribute("weatherResponseDto", weatherResponseDto);
        return "dataViz";
    }

    @GetMapping("/remote")
    String remoteGet(ModelMap modelMap) {

        StationInfoDto stationInfoDto = favoriteService.getFavoriteStations();
        FilterDto filterDto = new FilterDto();
        filterDto.setNamePath("C");
        filterDto.setPayloadState("C");
        filterDto.setPayloadType("C");
        modelMap.addAttribute("filterDto", filterDto);
        modelMap.addAttribute("stationInfoDto", stationInfoDto);

        return "remote";
    }

    @PostMapping("/remote")
    String remotePost(ModelMap modelMap, @Valid FilterDto filterDto, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/remote";
        }
        WeatherResponseDto weatherResponseDto = weatherService.postWeatherRequest(filterDto);
        modelMap.addAttribute("weatherResponseDto", weatherResponseDto);
        return "dataViz";
    }

    @GetMapping("/staticFind")
    String acceptDate(ModelMap modelMap) {
        modelMap.addAttribute("filterDto", new FilterDto());
        modelMap.addAttribute("pathList", weatherService.pathList());
        return "staticFind";
    }

    @PostMapping("/staticFind")
    String hello(ModelMap modelMap, @Valid FilterDto filterDto, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/staticFind";
        }
        WeatherResponseDto weatherResponseDto = weatherService.processStaticData(filterDto);
        modelMap.addAttribute("weatherResponseDto", weatherResponseDto);
        return "dataViz";
    }
}
