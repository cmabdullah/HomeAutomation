package com.abdullah.home.automation.registry.impl;

import com.abdullah.home.automation.constants.Constant;
import com.abdullah.home.automation.dto.SwitchInfo;
import com.abdullah.home.automation.registry.PiSwitchRegistry;
import org.springframework.stereotype.Service;

@Service
public class PiSwitchRegistryImpl implements PiSwitchRegistry {
    @Override
    public void onSwitch(SwitchInfo logicalSwitchInfo) {
        System.out.println("pi switch on ");
        Constant.PI_SWITCH = 1;
    }

    @Override
    public void offSwitch(SwitchInfo logicalSwitchInfo) {
        System.out.println("pi switch off ");
        Constant.PI_SWITCH = 0;
    }
}
