package com.abdullah.home.automation.hardware.mainswitchregistry;

import com.abdullah.home.automation.hardware.model.HardwareMainSwitch;

public class HardwareMainSwitchRegistry {
    public static volatile HardwareMainSwitch hardwareMainSwitch;

    public static HardwareMainSwitch getHardwareMainSwitch() {
        return hardwareMainSwitch;
    }

    public static void setHardwareMainSwitch(HardwareMainSwitch hardwareMainSwitch) {
        HardwareMainSwitchRegistry.hardwareMainSwitch = hardwareMainSwitch;
    }
}
