package com.abdullah.home.automation.registry.impl;

import com.abdullah.home.automation.constants.Constant;
import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.dto.SwitchInfo;
import com.abdullah.home.automation.registry.LogicalSwitchRegistry;
import org.springframework.stereotype.Service;

@Service
public class LogicalSwitchRegistryImpl implements LogicalSwitchRegistry {

    static SwitchInfo logicalSwitchInfo = new SwitchInfo();

    @Override
    public SwitchInfo getLogicalSwitchInfo(SwitchName switchName) {
        System.out.println("getLogicalSwitchInfo method called ");
        //provide information from network
        logicalSwitchInfo.setSwitchName(SwitchName.LIGHT_1);
        logicalSwitchInfo.setSwitchType(SwitchType.PHYSICAL);

        if (Constant.LOGICAL_SWITCH == 1 ){
            logicalSwitchInfo.setSwitchState(SwitchState.ON);
        }else{
            logicalSwitchInfo.setSwitchState(SwitchState.OFF);
        }

        return logicalSwitchInfo;
    }

    @Override
    public void offLogicalSwitch(SwitchInfo logicalSwitchInfo) {
        System.out.println("logical switch off ");
        logicalSwitchInfo.setSwitchName(SwitchName.LIGHT_1);
        logicalSwitchInfo.setSwitchType(SwitchType.PHYSICAL);
        logicalSwitchInfo.setSwitchState(SwitchState.OFF);

        //dummy
        Constant.LOGICAL_SWITCH = 0;
    }

    @Override
    public void onLogicalSwitch(SwitchInfo logicalSwitchInfo) {
        System.out.println("logical switch on ");
        logicalSwitchInfo.setSwitchName(SwitchName.LIGHT_1);
        logicalSwitchInfo.setSwitchType(SwitchType.PHYSICAL);
        logicalSwitchInfo.setSwitchState(SwitchState.ON);

        //dummy
        Constant.LOGICAL_SWITCH = 1;
    }
}
