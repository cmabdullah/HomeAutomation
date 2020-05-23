package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.SwitchInfo;

public interface PhysicalSwitchRegistry {

    SwitchInfo getPhysicalSwitchInfo(SwitchName switchName);

    Switch offPhysicalSwitch(SwitchInfo physicalSwitchInfo, Switch switchInfo);

    Switch onPhysicalSwitch(SwitchInfo physicalSwitchInfo, Switch switchInfo);
}
