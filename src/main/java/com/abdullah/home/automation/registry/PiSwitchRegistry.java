package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.SwitchInfo;

public interface PiSwitchRegistry {

    Switch onSwitch(SwitchInfo piSwitchInfo, Switch switchInfo);

    Switch offSwitch(SwitchInfo piSwitchInfo, Switch switchInfo);
}
