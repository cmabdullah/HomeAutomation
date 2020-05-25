package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.domain.WeatherEntity;
import com.abdullah.home.automation.dto.request.StationsDto;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.service.StationService;
import com.abdullah.home.automation.service.SwitchService;
import com.abdullah.home.automation.service.WeatherEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;


@Controller
public class TestController {

    private final StationService stationService;
    private final SwitchService switchService;
    private final WeatherEntityService weatherEntityService;

    public TestController(StationService stationService,
                   SwitchService switchService,
                          WeatherEntityService weatherEntityService){
        this.stationService = stationService;
        this.switchService = switchService;
        this.weatherEntityService = weatherEntityService;
    }

    @ResponseBody
    @GetMapping("/stationDataMig")
    public String stationDataMigration() throws IOException {
        List<StationsDto> list = stationService.migrateStationInfo();
        return "done";
    }

    @ResponseBody
    @GetMapping("/switchDataMig")
    public String switchDataMigration() {
        List<Switch> list = switchService.migrateSwitchInfo();
        return "done";
    }

    @ResponseBody
    @GetMapping("/weatherEntityDataMig")
    public String weatherEntityMigration() {
        List<WeatherEntity> list = weatherEntityService.migrateWeatherEntity();
        return "done";
    }

    @GetMapping("/exSwitch")
    public Switch getCustomer() {
        return switchService.findBySwitchName("fan4").orElseThrow(ApiError.createSingletonSupplier("switch not found with id " , HttpStatus.NOT_FOUND));
    }
}
