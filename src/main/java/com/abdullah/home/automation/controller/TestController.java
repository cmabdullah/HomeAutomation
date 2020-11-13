package com.abdullah.home.automation.controller;

//import com.abdullah.home.automation.hardware.service.CustomHardwareService;
import com.abdullah.home.automation.config.HardwareConfig;
import com.abdullah.home.automation.config.Mqtt;
import com.abdullah.home.automation.dto.mqtt.MqttSubscribeModel;
import com.abdullah.home.automation.service.MainSwitchService;
import com.abdullah.home.automation.service.StationService;
import com.abdullah.home.automation.service.SwitchService;
import com.abdullah.home.automation.service.WeatherEntityService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    @GetMapping("/subscribe")
    public List<MqttSubscribeModel> subscribeChannel()
            throws InterruptedException, MqttException {
        List<MqttSubscribeModel> messages = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
//
//        Mqtt.getInstance().setCallback();
//        Mqtt.getInstance().subscribe("weather");


        Mqtt.getInstance().subscribeWithResponse("weather", (s, mqttMessage) -> {
            MqttSubscribeModel mqttSubscribeModel = new MqttSubscribeModel();
            mqttSubscribeModel.setId(mqttMessage.getId());
            mqttSubscribeModel.setMessage(new String(mqttMessage.getPayload()));
            mqttSubscribeModel.setQos(mqttMessage.getQos());
            messages.add(mqttSubscribeModel);
            countDownLatch.countDown();
        });

        countDownLatch.await(10000, TimeUnit.MILLISECONDS);

        String str = messages.get(0).getMessage();

        String[] arr = str.split("\n");

        for (String s: arr
        ) {
            System.out.println(s);
        }

        return messages;
    }

}
