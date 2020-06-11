package com.abdullah.home.automation.registry.impl;

import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.SwitchInfo;
import com.abdullah.home.automation.registry.DBSwitchRegistry;
import com.abdullah.home.automation.registry.PiSwitchRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PiSwitchRegistryImpl implements PiSwitchRegistry {

    private final DBSwitchRegistry dBSwitchRegistry;

    private static final Logger log = LoggerFactory.getLogger(PiSwitchRegistryImpl.class);

    @Autowired
    public PiSwitchRegistryImpl(DBSwitchRegistry dBSwitchRegistry){
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
