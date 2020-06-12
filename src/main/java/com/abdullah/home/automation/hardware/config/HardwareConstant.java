package com.abdullah.home.automation.hardware.config;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class HardwareConstant {

    public static Pin DTH_22_PIN_CONFIG = RaspiPin.GPIO_25;

    public static Pin LIGHT_1_SWITCH_PIN_CONFIG = RaspiPin.GPIO_28;
    public static Pin LIGHT_1_PIN_CONFIG = RaspiPin.GPIO_29;

    public static Pin FAN_1_SWITCH_PIN_CONFIG = RaspiPin.GPIO_04; //bcm 23 -> original GPIO. 4 wPi4  pcb GPIO 23
    public static Pin FAN_1_PIN_CONFIG =  RaspiPin.GPIO_05;//bcm 24 -> original GPIO. 5 wPi5  pcb GPIO 24

    /**
     * Time in nanoseconds to separate ZERO and ONE signals.
     */
    public static final int LONGEST_ZERO = 50000;
}
