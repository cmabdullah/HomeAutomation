package com.abdullah.home.automation.controller;

import com.abdullah.home.automation.domain.RegulatorDto;
import com.abdullah.home.automation.service.RegulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegulatorController {

    @Autowired
    private RegulatorService regulatorService;

    @GetMapping("/regulator")
    public String regulatorGet(ModelMap modelMap) {
        modelMap.addAttribute("regulatorDto", new RegulatorDto());
        int currentRegulatorValue = 58;
        modelMap.addAttribute("currentRegulatorValue", currentRegulatorValue);
        return "regulator";
    }

    @PostMapping("/regulator")
    public String regulatorPost(RegulatorDto regulatorDto) {
        System.out.println("regulatorDto : "+ regulatorDto.toString());

        if (regulatorDto.getRegulatorParam() >= 0 && regulatorDto.getRegulatorParam()<=128){
            int req = regulatorService.postSocketRequest(regulatorDto.getRegulatorParam());
            //int remoteVal =
        }

        return "regulator";
    }

    @ResponseBody
    @RequestMapping(value="/remove", method= RequestMethod.POST)
    public String remove(@RequestParam(value = "arr") String[] tdValues) {

        for (String id :tdValues ) {
            System.out.println("id : "+ id);
        }

        System.out.println("size : "+ tdValues.length);

        //System.out.println("Id "+ tdValues.toString());

        return tdValues.toString();
    }
}
