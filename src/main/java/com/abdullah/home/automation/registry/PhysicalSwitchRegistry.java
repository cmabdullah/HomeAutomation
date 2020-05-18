package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.dto.SwitchInfo;

public interface PhysicalSwitchRegistry {

    SwitchInfo getPhysicalSwitchInfo(SwitchName switchName);

    void offPhysicalSwitch(SwitchInfo physicalSwitchInfo);

    void onPhysicalSwitch(SwitchInfo physicalSwitchInfo);
}
