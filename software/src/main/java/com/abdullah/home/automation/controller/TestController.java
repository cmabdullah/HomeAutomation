package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.domain.MainSwitch;
import com.abdullah.home.automation.domain.WeatherEntity;
import com.abdullah.home.automation.dto.request.StationsDto;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.response.StationsResponseDto;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.hardware.model.CustomHardware;
import com.abdullah.home.automation.hardware.service.CustomHardwareService;
import com.abdullah.home.automation.service.MainSwitchService;
import com.abdullah.home.automation.service.StationService;
import com.abdullah.home.automation.service.SwitchService;
import com.abdullah.home.automation.service.WeatherEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    private final StationService stationService;
    private final SwitchService switchService;
    private final WeatherEntityService weatherEntityService;
    private final MainSwitchService mainSwitchService;

    private final CustomHardwareService customHardwareService;

    @Autowired
    public TestController(StationService stationService,
                          SwitchService switchService,
                          WeatherEntityService weatherEntityService,
                          MainSwitchService mainSwitchService,
                          CustomHardwareService customHardwareService) {
        this.stationService = stationService;
        this.switchService = switchService;
        this.weatherEntityService = weatherEntityService;
        this.mainSwitchService = mainSwitchService;
        this.customHardwareService = customHardwareService;
    }

    @GetMapping("/regulator")
    String regulator(Model model) {
        int regulatorParam = 10;
        model.addAttribute("regulatorParam", regulatorParam);
        return "regulator";
    }

    @ResponseBody
    @GetMapping("/customHardware")
    public CustomHardware data() {
        log.debug("land");
        CustomHardware customHardware = customHardwareService.get();
        log.debug("customHardware : "+ customHardware);
        return customHardwareService.get();
    }

}
