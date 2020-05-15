package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.service.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
public class StationController {

    @Autowired
    StationService stationService;

    @GetMapping("/stations")
    String stations() throws IOException {

        stationService.stationList().forEach(n -> log.info(n.toString()));
        return "stations";
    }

}
