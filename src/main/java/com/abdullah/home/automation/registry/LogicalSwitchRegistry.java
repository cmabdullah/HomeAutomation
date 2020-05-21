package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.domain.model.Switch;
import com.abdullah.home.automation.dto.SwitchInfo;

public interface LogicalSwitchRegistry {

    SwitchInfo getLogicalSwitchInfo(SwitchName switchName);

    Switch offLogicalSwitch(SwitchInfo logicalSwitchInfo, Switch switchInfo);

    Switch onLogicalSwitch(SwitchInfo logicalSwitchInfo, Switch switchInfo);
}
