package com.abdullah.home.automation.hardware.config;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class PinConfig {

    public static Pin DTH_22_PIN_CONFIG = RaspiPin.GPIO_25;
    /**
     * Time in nanoseconds to separate ZERO and ONE signals.
     */
    public static final int LONGEST_ZERO = 50000;
}
