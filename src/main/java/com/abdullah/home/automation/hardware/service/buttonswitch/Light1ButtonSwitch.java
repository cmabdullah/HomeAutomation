package com.abdullah.home.automation.hardware.service.buttonswitch;

import com.abdullah.home.automation.hardware.config.GpioConfig;
import com.abdullah.home.automation.hardware.mainswitchregistry.HardwareMainSwitchRegistry;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Light1ButtonSwitch extends Thread {

    private static final Logger log = LoggerFactory.getLogger(Light1ButtonSwitch.class);

    @Override
    public void run() {
        try {

            GpioConfig.LIGHT_1_PHYSICAL_BUTTON_INPUT.addListener((GpioPinListenerDigital) event -> {
                log.info("Event" + LocalDateTime.now());
                if (event.getState().isHigh()) {
                    if (HardwareMainSwitchRegistry.getHardwareMainSwitch().isPinModePhysicalSwitch()) {
                        //turn off led pin
                        //GpioConfig.LIGHT_1_DIGITAL_OUTPUT.setState(PinState.LOW);
                        GpioConfig.LIGHT_1_DIGITAL_OUTPUT.low();
                        //log.info("Pin state " + GpioConfig.LIGHT_1_DIGITAL_OUTPUT.getState());
                        boolean pinState = GpioConfig.LIGHT_1_DIGITAL_OUTPUT.isLow();
                        log.info("isLow " + pinState + " switch name : " + "Light1ButtonSwitch" + " time " + LocalDateTime.now());

                    } else {
                        log.error("Physical switch is off");
                    }

                } else {
                    if (HardwareMainSwitchRegistry.getHardwareMainSwitch().isPinModePhysicalSwitch()) {
                        //turn on led pin
                        //GpioConfig.LIGHT_1_DIGITAL_OUTPUT.setState(PinState.HIGH);
                        GpioConfig.LIGHT_1_DIGITAL_OUTPUT.high();
                        //log.info("Pin state " + GpioConfig.LIGHT_1_DIGITAL_OUTPUT.getState());
                        boolean pinState = GpioConfig.LIGHT_1_DIGITAL_OUTPUT.isHigh();
                        log.info("isHigh " + pinState + " switch name : " + "Light1ButtonSwitch" + " time " + LocalDateTime.now());

                    } else {
                        log.error("Physical switch is off");
                    }
                }
            });

//            buttonPin.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
//                public Void call() throws Exception {
//                    System.out.println(" --> GPIO TRIGGER CALLBACK RECEIVED ");
//                    return null;
//                }
//            }));

            while (true) {
                Thread.sleep(500);
            }

        } catch (Exception e) {

        }


    }
}
