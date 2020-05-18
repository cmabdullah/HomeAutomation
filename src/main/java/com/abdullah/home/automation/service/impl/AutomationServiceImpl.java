package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.dto.SwitchInfo;
import com.abdullah.home.automation.registry.LogicalSwitchRegistry;
import com.abdullah.home.automation.registry.PhysicalSwitchRegistry;
import com.abdullah.home.automation.registry.PiSwitchRegistry;
import com.abdullah.home.automation.service.AutomationService;
import com.abdullah.home.automation.utlity.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomationServiceImpl implements AutomationService {


    private final PhysicalSwitchRegistry physicalSwitchRegistry;

    private final PiSwitchRegistry piSwitchRegistry;

    private final LogicalSwitchRegistry logicalSwitchRegistry;

    @Autowired
    AutomationServiceImpl(PhysicalSwitchRegistry physicalSwitchRegistry,
                          PiSwitchRegistry piSwitchRegistry,
                          LogicalSwitchRegistry logicalSwitchRegistry){
        this.physicalSwitchRegistry = physicalSwitchRegistry;
        this.piSwitchRegistry = piSwitchRegistry;
        this.logicalSwitchRegistry = logicalSwitchRegistry;
    }

    @Override
    public void changeSwitchStateLogicalRequest(SwitchInfo logicalSwitchInfo) {

        //light1
        logicalSwitchInfo.getSwitchName();

        //logical
        logicalSwitchInfo.getSwitchType();

        //on or off
        logicalSwitchInfo.getSwitchState();

        SwitchInfo physicalSwitch = physicalSwitchRegistry.getPhysicalSwitchInfo(logicalSwitchInfo.getSwitchName());

        if (logicalSwitchInfo.getSwitchState() == SwitchState.ON &&  physicalSwitch.getSwitchState() == SwitchState.ON){

            SwitchInfo physicalSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(physicalSwitch);
            //pi switch already running
            // force off switch physical
            physicalSwitchDeepCopy.setSwitchState(SwitchState.OFF);
            physicalSwitchRegistry.offPhysicalSwitch(physicalSwitchDeepCopy);
            //SR show by default on, device is online
            piSwitchRegistry.onSwitch(logicalSwitchInfo);
            logicalSwitchRegistry.onLogicalSwitch(logicalSwitchInfo);
        }

        if (logicalSwitchInfo.getSwitchState()== SwitchState.ON && physicalSwitch.getSwitchState()== SwitchState.OFF){
            // // SR show by default off
            piSwitchRegistry.onSwitch(logicalSwitchInfo);
            //SR show by default on, device is online
            logicalSwitchRegistry.onLogicalSwitch(logicalSwitchInfo);
        }

        if (logicalSwitchInfo.getSwitchState()== SwitchState.OFF &&  physicalSwitch.getSwitchState() == SwitchState.OFF){
            piSwitchRegistry.offSwitch(logicalSwitchInfo);
            logicalSwitchRegistry.offLogicalSwitch(logicalSwitchInfo);
        }

        if (logicalSwitchInfo.getSwitchState() == SwitchState.OFF && physicalSwitch.getSwitchState() == SwitchState.ON){
            // // SR show by default on
            piSwitchRegistry.offSwitch(logicalSwitchInfo);
            SwitchInfo physicalSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(physicalSwitch);
            // force off switch physical
            physicalSwitchDeepCopy.setSwitchState(SwitchState.OFF);
            physicalSwitchRegistry.offPhysicalSwitch(physicalSwitchDeepCopy);
            logicalSwitchRegistry.offLogicalSwitch(logicalSwitchInfo);
        }
    }

    @Override
    public void changeSwitchStatePhysicalRequest(SwitchInfo physicalSwitchInfo) {

        SwitchInfo logicalSwitch = logicalSwitchRegistry.getLogicalSwitchInfo(physicalSwitchInfo.getSwitchName());

        if (physicalSwitchInfo.getSwitchState() == SwitchState.ON  && logicalSwitch.getSwitchState() == SwitchState.ON ){
            SwitchInfo logicalSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(logicalSwitch);
            //pi switch already running
            // force off switch remote
            logicalSwitchDeepCopy.setSwitchState(SwitchState.OFF);
            logicalSwitchRegistry.offLogicalSwitch(logicalSwitchDeepCopy);
            //SR show by default on, device is online
            piSwitchRegistry.onSwitch(physicalSwitchInfo);
            physicalSwitchRegistry.onPhysicalSwitch(physicalSwitchInfo);
        }

        if (physicalSwitchInfo.getSwitchState() == SwitchState.ON && logicalSwitch.getSwitchState() == SwitchState.OFF ){
            // // SR show by default off
            piSwitchRegistry.onSwitch(physicalSwitchInfo);
            //SR show by default on, device is online
            physicalSwitchRegistry.onPhysicalSwitch(physicalSwitchInfo);
        }

        if (physicalSwitchInfo.getSwitchState()== SwitchState.OFF  && logicalSwitch.getSwitchState()== SwitchState.OFF  ){
            piSwitchRegistry.offSwitch(physicalSwitchInfo);
            physicalSwitchRegistry.offPhysicalSwitch(physicalSwitchInfo);
        }

        if (physicalSwitchInfo.getSwitchState()== SwitchState.OFF  && logicalSwitch.getSwitchState() == SwitchState.ON ){
            // // SR show by default on
            piSwitchRegistry.offSwitch(physicalSwitchInfo);
            SwitchInfo logicalSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(logicalSwitch);
            // force off switch logical
            logicalSwitchDeepCopy.setSwitchState(SwitchState.OFF);
            logicalSwitchRegistry.offLogicalSwitch(logicalSwitchDeepCopy);
            physicalSwitchRegistry.offPhysicalSwitch(physicalSwitchInfo);
        }
    }
}
