package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.domain.model.Switch;
import com.abdullah.home.automation.dto.SwitchInfo;

public interface PiSwitchRegistry {

    Switch onSwitch(SwitchInfo piSwitchInfo, Switch switchInfo);

    Switch offSwitch(SwitchInfo piSwitchInfo, Switch switchInfo);
}
