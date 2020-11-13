package com.abdullah.home.automation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(value = "/")
    String helloGet() {
        return "home";
    }

    @GetMapping("/regulator")
    String regulator(Model model) {
        int regulatorParam = 10;
        model.addAttribute("regulatorParam", regulatorParam);
        return "regulator";
    }

}
