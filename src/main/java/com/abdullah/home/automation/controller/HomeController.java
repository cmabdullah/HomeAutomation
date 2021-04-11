package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

@Controller

public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(value = "/")
    String helloGet() {
        Map<String, Switch>  map = SwitchCentralRegistry.getCentralSwitchMap();
        return "home";
    }

    @GetMapping(value = "/temp")
    String helloTemp() {
        return "temp";
    }

    @GetMapping(value = "/publisher")
    String helloTemp1() {
        return "publisher";
    }
    @GetMapping(value = "/live-commentary")
    String helloTemp2() {
        return "live-commentary";
    }

    @MessageMapping("/live-comment")
    @SendTo("/topic/commentary")
    public Comment liveComment(Commentary message) throws Exception {
        System.out.println("message "+ message.toString());
        return new Comment(HtmlUtils.htmlEscape(message.getCommentary()));
    }

    @GetMapping("/regulator")
    String regulator(Model model) {
        int regulatorParam = 10;
        model.addAttribute("regulatorParam", regulatorParam);
        return "regulator";
    }

}
