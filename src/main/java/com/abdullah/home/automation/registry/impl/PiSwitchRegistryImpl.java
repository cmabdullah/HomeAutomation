package com.abdullah.home.automation.registry.impl;

import com.abdullah.home.automation.domain.model.Switch;
import com.abdullah.home.automation.dto.SwitchInfo;
import com.abdullah.home.automation.registry.DBSwitchRegistry;
import com.abdullah.home.automation.registry.PiSwitchRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PiSwitchRegistryImpl implements PiSwitchRegistry {

    private final DBSwitchRegistry dBSwitchRegistry;

    @Autowired
    PiSwitchRegistryImpl(DBSwitchRegistry dBSwitchRegistry){
        this.dBSwitchRegistry = dBSwitchRegistry;
    }

    @Override
    public Switch onSwitch(SwitchInfo piSwitchInfo, Switch switchInfo) {

        return dBSwitchRegistry.switchOnOff(piSwitchInfo , 1, switchInfo);

    }

    @Override
    public Switch offSwitch(SwitchInfo piSwitchInfo, Switch switchInfo) {

        return dBSwitchRegistry.switchOnOff(piSwitchInfo , 0, switchInfo);

    }
}
