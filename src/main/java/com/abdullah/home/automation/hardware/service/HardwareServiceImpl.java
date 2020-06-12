package com.abdullah.home.automation.hardware.service;

import com.abdullah.home.automation.hardware.config.GpioConfig;
import com.abdullah.home.automation.hardware.mainswitchregistry.HardwareMainSwitchRegistry;
import com.abdullah.home.automation.hardware.model.HardwareMainSwitch;
import com.abdullah.home.automation.hardware.service.buttonswitch.Fan1ButtonSwitch;
import com.abdullah.home.automation.hardware.service.buttonswitch.Light1ButtonSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class HardwareServiceImpl implements HardwareService {
    private static final Logger log = LoggerFactory.getLogger(HardwareServiceImpl.class);

    @Override
    public boolean on(String switchName) {

        HardwareMainSwitch hardwareMainSwitch = HardwareMainSwitchRegistry.getHardwareMainSwitch();

        if (hardwareMainSwitch.isPinModeLogicalSwitch()) {
            return switchOn(switchName);
        }

        log.error("main switch is logical off");
        return false;
    }

    @Override
    public boolean off(String switchName) {

        HardwareMainSwitch hardwareMainSwitch = HardwareMainSwitchRegistry.getHardwareMainSwitch();

         if (hardwareMainSwitch.isPinModeLogicalSwitch()) {
            return switchOff(switchName);
        }
        log.error("main switch is off");
        return false;
    }


    private boolean switchOn(String switchName) {
        boolean pinState = false;
        if (switchName.equals("light1")){
            if (GpioConfig.LIGHT_1_DIGITAL_OUTPUT.isLow()){
                GpioConfig.LIGHT_1_DIGITAL_OUTPUT.high();
            }else {
                log.info("light 1 pin already high");
            }

            pinState = GpioConfig.LIGHT_1_DIGITAL_OUTPUT.isHigh();

        }else if (switchName.equals("fan1")){
            if (GpioConfig.FAN_1_DIGITAL_OUTPUT.isLow()){
                GpioConfig.FAN_1_DIGITAL_OUTPUT.high();
            }else {
                log.info("fan1 pin already high");
            }


            pinState = GpioConfig.FAN_1_DIGITAL_OUTPUT.isHigh();
        }

        log.info("isHigh " + pinState + " switch name : " + switchName + " time " + LocalDateTime.now());
        return pinState;
    }

    private boolean switchOff(String switchName) {

        boolean pinState = false;
        if (switchName.equals("light1")){
            if (GpioConfig.LIGHT_1_DIGITAL_OUTPUT.isHigh()){
                GpioConfig.LIGHT_1_DIGITAL_OUTPUT.low();
            }else {
                log.info("light 1 pin already low");
            }

            pinState = GpioConfig.LIGHT_1_DIGITAL_OUTPUT.isLow();
        }else if (switchName.equals("fan1")){
            if (GpioConfig.FAN_1_DIGITAL_OUTPUT.isHigh()){
                GpioConfig.FAN_1_DIGITAL_OUTPUT.low();
            }else {
                log.info("fan 1 pin already low");
            }

            pinState = GpioConfig.FAN_1_DIGITAL_OUTPUT.isLow();
        }

        log.info("isLow " + pinState + " switch name : " + switchName + " time " + LocalDateTime.now());
        return pinState;
    }


    @Override
    public boolean buttonConfig() {
        Thread thread1 = new Thread(new Light1ButtonSwitch());
        thread1.start();

        Thread thread2 = new Thread(new Fan1ButtonSwitch());
        thread2.start();
//
//        Runnable light1ButtonSwitch = () -> {
//            // your code here ...
//        };
//        Thread t = new Thread(light1ButtonSwitch);
//        t.start();

        return true;
    }
}
