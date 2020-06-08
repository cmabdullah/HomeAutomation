package com.abdullah.home.automation.registry.impl;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.SwitchInfo;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.exception.ApiMessage;
import com.abdullah.home.automation.registry.DBSwitchRegistry;
import com.abdullah.home.automation.service.SwitchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DBSwitchRegistryImpl implements DBSwitchRegistry {

    private final SwitchService switchService;

    private static final Logger log = LoggerFactory.getLogger(DBSwitchRegistryImpl.class);

    @Autowired
    public DBSwitchRegistryImpl(SwitchService switchService) {
        this.switchService = switchService;
    }

    @Override
    public Switch switchOnOff(SwitchInfo switchInfo, int switchState, Switch mySwitch) {

        SwitchType switchType = switchInfo.getSwitchType();

        String status = switchState == 1 ? "on" : "off" ;
        log.info("switch name " + switchInfo.getSwitchName() + " switch type " + switchInfo.getSwitchType() + " switch status " + status );

        if (switchType == SwitchType.LOGICAL) {
            mySwitch.setLogicalSwitch(switchState);
        } else if (switchType == SwitchType.PHYSICAL) {
            mySwitch.setPhysicalSwitch(switchState);
        } else if (switchType == SwitchType.PI) {
            mySwitch.setPiSwitch(switchState);
        }

        return mySwitch;
    }

    @Override
    public SwitchInfo getPhysicalOrLogicalSwitchInfo(SwitchName switchName, SwitchType switchType) {

        Optional<Switch> switchOptional = switchService.findBySwitchName(switchName.getValue());


        if (switchOptional.isPresent()){
            Switch switchObject = switchOptional.get();
            return buildSwitchInfo(switchObject, switchType);
        }else {
            throw new ApiError(ApiMessage.SWITCH_NOT_FOUND, HttpStatus.EXPECTATION_FAILED);
        }
//        return switchService.findBySwitchName(switchName.getValue()).stream()
//                .filter(Objects::nonNull).map(sw -> buildSwitchInfo(sw, switchType)).findAny()
//                .orElseThrow(ApiError.createSingletonSupplier(ApiMessage.SWITCH_NOT_FOUND, HttpStatus.EXPECTATION_FAILED));
    }

    private SwitchInfo buildSwitchInfo(Switch sw, SwitchType switchType) {

        SwitchInfo switchInfo = new SwitchInfo();

        //provide information from network
        switchInfo.setSwitchName(SwitchName.forValue(sw.getSwitchName()));

        if (switchType == SwitchType.PHYSICAL) {
            //will consider use case
            switchInfo.setSwitchType(SwitchType.PHYSICAL);
            if (sw.getPhysicalSwitch() == 1) {
                switchInfo.setSwitchState(SwitchState.ON);
            } else {
                switchInfo.setSwitchState(SwitchState.OFF);
            }
        } else if (switchType == SwitchType.LOGICAL) {
            switchInfo.setSwitchType(SwitchType.LOGICAL);
            if (sw.getLogicalSwitch() == 1) {
                switchInfo.setSwitchState(SwitchState.ON);
            } else {
                switchInfo.setSwitchState(SwitchState.OFF);
            }
        }
        return switchInfo;
    }
}
