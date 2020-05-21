package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.domain.model.Switch;
import com.abdullah.home.automation.dto.SwitchInfo;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.exception.ApiMessage;
import com.abdullah.home.automation.registry.LogicalSwitchRegistry;
import com.abdullah.home.automation.registry.PhysicalSwitchRegistry;
import com.abdullah.home.automation.registry.PiSwitchRegistry;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import com.abdullah.home.automation.service.AutomationService;
import com.abdullah.home.automation.service.SwitchService;
import com.abdullah.home.automation.utlity.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AutomationServiceImpl implements AutomationService {


    private final PhysicalSwitchRegistry physicalSwitchRegistry;

    private final PiSwitchRegistry piSwitchRegistry;

    private final LogicalSwitchRegistry logicalSwitchRegistry;

    private final SwitchService switchService;

    @Autowired
    AutomationServiceImpl(PhysicalSwitchRegistry physicalSwitchRegistry,
                          PiSwitchRegistry piSwitchRegistry,
                          LogicalSwitchRegistry logicalSwitchRegistry,
                          SwitchService switchService){
        this.physicalSwitchRegistry = physicalSwitchRegistry;
        this.piSwitchRegistry = piSwitchRegistry;
        this.logicalSwitchRegistry = logicalSwitchRegistry;
        this.switchService = switchService;
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

        Switch switchInfo = switchInfo(logicalSwitchInfo.getSwitchName().getValue());

        Switch switchDeepCopy = (Switch)Util.getDeepCopy(switchInfo);

        if (logicalSwitchInfo.getSwitchState() == SwitchState.ON &&  physicalSwitch.getSwitchState() == SwitchState.ON){

            SwitchInfo physicalSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(physicalSwitch);
            //pi switch already running
            // force off switch physical
            physicalSwitchDeepCopy.setSwitchState(SwitchState.OFF);

            switchInfo = physicalSwitchRegistry.offPhysicalSwitch(physicalSwitchDeepCopy, switchInfo);

            //make py switchDeepCopy
            SwitchInfo piSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(logicalSwitchInfo);
            piSwitchDeepCopy.setSwitchType(SwitchType.PI);
            //SR show by default on, device is online
            switchInfo = piSwitchRegistry.onSwitch(piSwitchDeepCopy, switchInfo);

            switchInfo = logicalSwitchRegistry.onLogicalSwitch(logicalSwitchInfo, switchInfo);

        }

        if (logicalSwitchInfo.getSwitchState()== SwitchState.ON && physicalSwitch.getSwitchState()== SwitchState.OFF){

            SwitchInfo piSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(logicalSwitchInfo);
            piSwitchDeepCopy.setSwitchType(SwitchType.PI);
            // SR show by default off
            switchInfo = piSwitchRegistry.onSwitch(piSwitchDeepCopy, switchInfo);
            //SR show by default on, device is online
            switchInfo = logicalSwitchRegistry.onLogicalSwitch(logicalSwitchInfo , switchInfo);

        }

        if (logicalSwitchInfo.getSwitchState()== SwitchState.OFF &&  physicalSwitch.getSwitchState() == SwitchState.OFF){

            SwitchInfo piSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(logicalSwitchInfo);
            piSwitchDeepCopy.setSwitchType(SwitchType.PI);

            switchInfo = piSwitchRegistry.offSwitch(piSwitchDeepCopy, switchInfo);

            switchInfo = logicalSwitchRegistry.offLogicalSwitch(logicalSwitchInfo, switchInfo);
        }

        if (logicalSwitchInfo.getSwitchState() == SwitchState.OFF && physicalSwitch.getSwitchState() == SwitchState.ON){

            SwitchInfo piSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(logicalSwitchInfo);
            piSwitchDeepCopy.setSwitchType(SwitchType.PI);

            // // SR show by default on
            switchInfo = piSwitchRegistry.offSwitch(piSwitchDeepCopy, switchInfo);

            SwitchInfo physicalSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(physicalSwitch);
            // force off switch physical
            physicalSwitchDeepCopy.setSwitchState(SwitchState.OFF);

            switchInfo = physicalSwitchRegistry.offPhysicalSwitch(physicalSwitchDeepCopy, switchInfo);

            switchInfo = logicalSwitchRegistry.offLogicalSwitch(logicalSwitchInfo, switchInfo);

        }
        saveSwitchInfo(switchInfo, switchDeepCopy);
    }

    @Override
    public void changeSwitchStatePhysicalRequest(SwitchInfo physicalSwitchInfo) {

        SwitchInfo logicalSwitch = logicalSwitchRegistry.getLogicalSwitchInfo(physicalSwitchInfo.getSwitchName());

        Switch switchInfo = switchInfo(physicalSwitchInfo.getSwitchName().getValue());

        Switch switchDeepCopy = (Switch)Util.getDeepCopy(switchInfo);

        if (physicalSwitchInfo.getSwitchState() == SwitchState.ON  && logicalSwitch.getSwitchState() == SwitchState.ON ){

            SwitchInfo logicalSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(logicalSwitch);
            //pi switch already running
            // force off switch remote
            logicalSwitchDeepCopy.setSwitchState(SwitchState.OFF);

            switchInfo = logicalSwitchRegistry.offLogicalSwitch(logicalSwitchDeepCopy, switchInfo);

            SwitchInfo piSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(physicalSwitchInfo);
            piSwitchDeepCopy.setSwitchType(SwitchType.PI);
            //SR show by default on, device is online
            switchInfo = piSwitchRegistry.onSwitch(piSwitchDeepCopy, switchInfo);

            switchInfo = physicalSwitchRegistry.onPhysicalSwitch(physicalSwitchInfo, switchInfo);

        }

        if (physicalSwitchInfo.getSwitchState() == SwitchState.ON && logicalSwitch.getSwitchState() == SwitchState.OFF ){

            SwitchInfo piSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(physicalSwitchInfo);
            piSwitchDeepCopy.setSwitchType(SwitchType.PI);
            // // SR show by default off
            switchInfo = piSwitchRegistry.onSwitch(piSwitchDeepCopy, switchInfo);
            //SR show by default on, device is online
            switchInfo = physicalSwitchRegistry.onPhysicalSwitch(physicalSwitchInfo, switchInfo);

        }

        if (physicalSwitchInfo.getSwitchState()== SwitchState.OFF  && logicalSwitch.getSwitchState()== SwitchState.OFF  ){

            SwitchInfo piSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(physicalSwitchInfo);
            piSwitchDeepCopy.setSwitchType(SwitchType.PI);

            switchInfo = piSwitchRegistry.offSwitch(piSwitchDeepCopy, switchInfo);

            switchInfo = physicalSwitchRegistry.offPhysicalSwitch(physicalSwitchInfo, switchInfo);

        }

        if (physicalSwitchInfo.getSwitchState()== SwitchState.OFF  && logicalSwitch.getSwitchState() == SwitchState.ON ){

            SwitchInfo piSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(physicalSwitchInfo);
            piSwitchDeepCopy.setSwitchType(SwitchType.PI);
            // // SR show by default on
            switchInfo = piSwitchRegistry.offSwitch(piSwitchDeepCopy, switchInfo);

            SwitchInfo logicalSwitchDeepCopy = (SwitchInfo)Util.getDeepCopy(logicalSwitch);
            // force off switch logical
            logicalSwitchDeepCopy.setSwitchState(SwitchState.OFF);

            switchInfo = logicalSwitchRegistry.offLogicalSwitch(logicalSwitchDeepCopy,switchInfo);

            switchInfo = physicalSwitchRegistry.offPhysicalSwitch(physicalSwitchInfo, switchInfo);

        }
        saveSwitchInfo(switchInfo, switchDeepCopy);
    }

    private Switch saveSwitchInfo(Switch switchInfo,Switch switchDeepCopy) {

        //need to sync, will complete later
        Switch updatedSwitch = switchService.save(switchInfo);

        SwitchCentralRegistry.centralSwitchMap.put(updatedSwitch.getSwitchName(), updatedSwitch);

        return updatedSwitch;
    }

    private Switch switchInfo(String switchName){
        return switchService.findBySwitchName(switchName)
                .orElseThrow(ApiError.createSingletonSupplier(ApiMessage.SWITCH_NOT_FOUND, HttpStatus.EXPECTATION_FAILED));
    }
}
