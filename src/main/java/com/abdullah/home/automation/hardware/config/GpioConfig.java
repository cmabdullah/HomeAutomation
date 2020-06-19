package com.abdullah.home.automation.hardware.config;

import com.pi4j.io.gpio.*;

public class GpioConfig {

    public static GpioController gpio = GpioFactory.getInstance();

    public static GpioPinDigitalOutput LIGHT_1_DIGITAL_OUTPUT = gpio.provisionDigitalOutputPin(HardwareConstant.LIGHT_1_PIN_CONFIG, "my Led", PinState.LOW);
    public static GpioPinDigitalInput LIGHT_1_PHYSICAL_BUTTON_INPUT = gpio.provisionDigitalInputPin(HardwareConstant.LIGHT_1_SWITCH_PIN_CONFIG,"my led switch", PinPullResistance.PULL_UP);

    public static GpioPinDigitalOutput FAN_1_DIGITAL_OUTPUT = gpio.provisionDigitalOutputPin(HardwareConstant.FAN_1_PIN_CONFIG, "my fan", PinState.LOW);//bcm 23 -> original GPIO. 4 wPi4  pcb GPIO 23
    public static GpioPinDigitalInput FAN_1_PHYSICAL_BUTTON_INPUT = gpio.provisionDigitalInputPin(HardwareConstant.FAN_1_SWITCH_PIN_CONFIG,"my fan switch", PinPullResistance.PULL_UP);//bcm 24 -> original GPIO. 5 wPi5  pcb GPIO 24

    public static Pin DTH_22_PIN_CONFIG_INDOOR = RaspiPin.GPIO_25;
    public static Pin DTH_22_PIN_CONFIG_OUT_DOOR = RaspiPin.GPIO_24;
    // set shutdown state for pin 1 (LED)

}
