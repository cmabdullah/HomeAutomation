package com.abdullah.home.automation.hardware.service;


public interface HardwareService {

    boolean on(String switchName);
    boolean off(String switchName);
    boolean buttonConfig();
    boolean mainSwitchConfig(boolean pinModeLogicalSwitch, boolean pinModePhysicalSwitch);
}
