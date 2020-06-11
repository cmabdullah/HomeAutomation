package com.abdullah.home.automation.registry.impl;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.SwitchInfo;
import com.abdullah.home.automation.registry.DBSwitchRegistry;
import com.abdullah.home.automation.registry.PhysicalSwitchRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicalSwitchRegistryImpl implements PhysicalSwitchRegistry {

    private final DBSwitchRegistry dBSwitchRegistry;

    private static final Logger log = LoggerFactory.getLogger(PhysicalSwitchRegistryImpl.class);

    @Autowired
    public PhysicalSwitchRegistryImpl(DBSwitchRegistry dBSwitchRegistry) {
        this.dBSwitchRegistry = dBSwitchRegistry;
    }


    @Override
    public SwitchInfo getPhysicalSwitchInfo(SwitchName switchName) {

        return dBSwitchRegistry.getPhysicalOrLogicalSwitchInfo(switchName, SwitchType.PHYSICAL);

    }

    @Override
    public Switch offPhysicalSwitch(SwitchInfo physicalSwitchInfo, Switch switchInfo) {

        return dBSwitchRegistry.switchOnOff(physicalSwitchInfo, 0, switchInfo);

    }

    @Override
    public Switch onPhysicalSwitch(SwitchInfo physicalSwitchInfo, Switch switchInfo) {

        return dBSwitchRegistry.switchOnOff(physicalSwitchInfo, 1, switchInfo);

    }
}
