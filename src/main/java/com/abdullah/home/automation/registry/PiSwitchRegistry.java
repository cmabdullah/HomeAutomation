package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.dto.SwitchInfo;

public interface PiSwitchRegistry {
    void onSwitch(SwitchInfo logicalSwitchInfo);

    void offSwitch(SwitchInfo logicalSwitchInfo);
}
