package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import com.abdullah.home.automation.utlity.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

@Controller

public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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

    @MessageMapping("/live-comment")
    //@SendTo("/topic/commentary")
    public Comment liveComment(@Payload Commentary message) {
        System.out.println("message "+ message.toString());
        messagingTemplate.convertAndSendToUser("weatherTemp","/queue/commentary",message);
        return new Comment(HtmlUtils.htmlEscape(message.getCommentary()));
    }

    @GetMapping("/regulator")
    String regulator(Model model) {
        int regulatorParam = 10;
        model.addAttribute("regulatorParam", regulatorParam);
        return "regulator";
    }

}
