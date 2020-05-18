package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.dto.SwitchInfo;

public interface LogicalSwitchRegistry {

    SwitchInfo getLogicalSwitchInfo(SwitchName switchName);

    void offLogicalSwitch(SwitchInfo logicalSwitchInfo);

    void onLogicalSwitch(SwitchInfo logicalSwitchInfo);
}
