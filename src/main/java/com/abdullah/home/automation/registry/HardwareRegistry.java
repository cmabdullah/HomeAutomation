package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.domain.Switch;

public interface HardwareRegistry {

    boolean save(Switch switchInfo);
    boolean hardwareConfig();
    boolean sensorConfig();
}
