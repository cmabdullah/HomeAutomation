package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class StationController {

    @Autowired
    StationService stationService;

    @GetMapping("/stations")
    String stations() throws IOException {

        //return stationService.stationList();
        return "stations";
    }

}
