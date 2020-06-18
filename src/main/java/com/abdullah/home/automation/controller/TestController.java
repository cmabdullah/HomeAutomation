package com.abdullah.home.automation.controller;

//import com.abdullah.home.automation.hardware.service.CustomHardwareService;
import com.abdullah.home.automation.config.HardwareConfig;
import com.abdullah.home.automation.service.MainSwitchService;
import com.abdullah.home.automation.service.StationService;
import com.abdullah.home.automation.service.SwitchService;
import com.abdullah.home.automation.service.WeatherEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final StationService stationService;
    private final SwitchService switchService;
    private final WeatherEntityService weatherEntityService;
    private final MainSwitchService mainSwitchService;

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

//    private CustomHardwareService customHardwareService = new CustomHardwareService();

    @Autowired
    public TestController(StationService stationService,
                          SwitchService switchService,
                          WeatherEntityService weatherEntityService,
                          MainSwitchService mainSwitchService
                          ) {
        this.stationService = stationService;
        this.switchService = switchService;
        this.weatherEntityService = weatherEntityService;
        this.mainSwitchService = mainSwitchService;

    }

    @GetMapping("/regulator")
    String regulator(Model model) {
        int regulatorParam = 10;
        model.addAttribute("regulatorParam", regulatorParam);
        return "regulator";
    }

//    @ResponseBody
//    @GetMapping("/hardwareMode")
//    String hardware() {
//        return HardwareConfig.getHardwareMode();
//    }

//    @ResponseBody
//    @GetMapping("/customHardware")
//    public boolean data() {
//        log.debug("land");
//        try {
//            boolean customHardware = customHardwareService.get();
//            log.debug("customHardware : "+ customHardware);
//            return customHardwareService.get();
//        }catch (Exception e){
//            return false;
//        }
//    }

}
