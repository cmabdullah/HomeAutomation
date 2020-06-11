package com.abdullah.home.automation.registry.impl;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.SwitchInfo;
import com.abdullah.home.automation.registry.DBSwitchRegistry;
import com.abdullah.home.automation.registry.LogicalSwitchRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogicalSwitchRegistryImpl implements LogicalSwitchRegistry {

    private final DBSwitchRegistry dBSwitchRegistry;

    private static final Logger log = LoggerFactory.getLogger(LogicalSwitchRegistryImpl.class);


    @Autowired
    public LogicalSwitchRegistryImpl(DBSwitchRegistry dBSwitchRegistry) {
        this.dBSwitchRegistry = dBSwitchRegistry;
    }

    @Override
    public SwitchInfo getLogicalSwitchInfo(SwitchName switchName) {

        return dBSwitchRegistry.getPhysicalOrLogicalSwitchInfo(switchName, SwitchType.LOGICAL);

    }

    @Override
    public Switch offLogicalSwitch(SwitchInfo logicalSwitchInfo, Switch switchInfo) {

        return dBSwitchRegistry.switchOnOff(logicalSwitchInfo, 0, switchInfo);
    }

    @Override
    public Switch onLogicalSwitch(SwitchInfo logicalSwitchInfo, Switch switchInfo) {

        return dBSwitchRegistry.switchOnOff(logicalSwitchInfo, 1, switchInfo);

    }
}
