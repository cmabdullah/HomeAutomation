package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.SwitchInfo;

public interface DBSwitchRegistry {

    Switch switchOnOff(SwitchInfo switchInfo , int switchState, Switch mySwitch);

    SwitchInfo getPhysicalOrLogicalSwitchInfo(SwitchName switchName, SwitchType switchType);
}
