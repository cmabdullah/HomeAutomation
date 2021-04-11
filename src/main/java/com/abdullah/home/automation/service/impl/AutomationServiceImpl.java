package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.SwitchInfo;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.exception.ApiMessage;
import com.abdullah.home.automation.registry.*;
import com.abdullah.home.automation.service.AutomationService;
import com.abdullah.home.automation.service.SwitchService;
import com.abdullah.home.automation.utlity.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AutomationServiceImpl implements AutomationService {

    private final PhysicalSwitchRegistry physicalSwitchRegistry;

    private final PiSwitchRegistry piSwitchRegistry;

    private final LogicalSwitchRegistry logicalSwitchRegistry;

    private final SwitchService switchService;

    private final HardwareRegistry hardwareRegistry;

    private static final Logger log = LoggerFactory.getLogger(AutomationServiceImpl.class);

    @Autowired
    public AutomationServiceImpl(PhysicalSwitchRegistry physicalSwitchRegistry,
                          PiSwitchRegistry piSwitchRegistry,
                          LogicalSwitchRegistry logicalSwitchRegistry,
                          SwitchService switchService, HardwareRegistry hardwareRegistry){
        this.physicalSwitchRegistry = physicalSwitchRegistry;
        this.piSwitchRegistry = piSwitchRegistry;
        this.logicalSwitchRegistry = logicalSwitchRegistry;
        this.switchService = switchService;
        this.hardwareRegistry = hardwareRegistry;
    }

    /*******
     *
     *
     SP -> Switch Physical
     SR -> Switch Remote

     remote case

     case 1
     SR -> 1 && SP -> 1 = 1 // SR show by default on, // force off switch physical
     case 2
     SR -> 1 && SP -> 0 = 1 // SR show by default off , you have to on  switch remote to active switch remote // disconnect switch physical

     case 3
     SR -> 0 && SP -> 0 = 0 // SR show by default off
     case 4
     SR -> 0 && SP -> 1 = 0 // SR show by default on, you have to off  switch remote to deactive switch remote // disconnect switch physical


     physical case

     case 5
     SP -> 1 && SR -> 1 = 1 // SR show by default on, // force off switch remote
     case 6
     SP -> 1 && SR -> 0 = 1 // SR show by default off, you have to active physical switch // disconnect switch remote

     case 7
     SP -> 0 && SR -> 0 = 0 // SR show by default off
     case 8
     SP -> 0 && SR -> 1 = 0 // SR show by default on, you have to off switch physical to deactive switch physical // disconnect switch physical
     *
     * @param logicalSwitchInfo
     */

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

        log.debug("switch info going to save"+ switchInfo.toString());

        //call hardware service
        boolean hardwareState = hardwareRegistry.save(switchInfo);

        if (hardwareState){
            try{
            //need to sync, will complete later
            Switch updatedSwitch = switchService.save(switchInfo);
            SwitchCentralRegistry.centralSwitchMap.put(updatedSwitch.getSwitchName(), updatedSwitch);
                return updatedSwitch;
            }catch (Exception e){
                Switch updatedSwitch = switchService.save(switchDeepCopy);
                SwitchCentralRegistry.centralSwitchMap.put(updatedSwitch.getSwitchName(), updatedSwitch);
                throw new ApiError(ApiMessage.DB_SYNC_ERROR, HttpStatus.NOT_FOUND);
            }
        }else{
            throw new ApiError(ApiMessage.HARDWARE_PROFILE_NOTSET_ERROR, HttpStatus.NOT_FOUND);
        }
    }

    private Switch switchInfo(String switchName){
        return switchService.findBySwitchName(switchName)
                .orElseThrow(ApiError.createSingletonSupplier(ApiMessage.SWITCH_NOT_FOUND, HttpStatus.EXPECTATION_FAILED));
    }
}
