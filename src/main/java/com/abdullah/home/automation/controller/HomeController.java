package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller

public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(value = "/")
    String helloGet(Model model) {
        Map<String, Switch> map = SwitchCentralRegistry.getCentralSwitchMap();
        for(Map.Entry<String, Switch> entry : map.entrySet()){
            System.out.println(entry.getKey()+"----"+entry.getValue());
        }
        model.addAttribute("data",map);
        return "home";
    }

    @GetMapping("/regulator")
    String regulator(Model model) {
        int regulatorParam = 10;
        model.addAttribute("regulatorParam", regulatorParam);
        return "regulator";
    }

}
