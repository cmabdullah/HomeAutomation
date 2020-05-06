package com.abdullah.home.automation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/socket")
    public String socket(){
        return "10";
    }
}
