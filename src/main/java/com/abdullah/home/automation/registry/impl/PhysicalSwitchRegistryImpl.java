package com.abdullah.home.automation.registry.impl;

import com.abdullah.home.automation.constants.Constant;
import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.dto.SwitchInfo;
import com.abdullah.home.automation.registry.PhysicalSwitchRegistry;
import org.springframework.stereotype.Service;

@Service
public class PhysicalSwitchRegistryImpl implements PhysicalSwitchRegistry {

    static SwitchInfo physicalSwitchInfo = new SwitchInfo();
    @Override
    public SwitchInfo getPhysicalSwitchInfo(SwitchName switchName) {
        System.out.println("getPhysicalSwitchInfo called ");

        //provide information from hardware
        physicalSwitchInfo.setSwitchName(SwitchName.LIGHT_1);
        physicalSwitchInfo.setSwitchType(SwitchType.PHYSICAL);

        if (Constant.PHYSICAL_SWITCH == 1 ){
            physicalSwitchInfo.setSwitchState(SwitchState.ON);
        }else{
            physicalSwitchInfo.setSwitchState(SwitchState.OFF);
        }

        return physicalSwitchInfo;
    }

    @Override
    public void offPhysicalSwitch(SwitchInfo physicalSwitchInfo) {
        System.out.println("physical switch off ");
        physicalSwitchInfo.setSwitchName(SwitchName.LIGHT_1);
        physicalSwitchInfo.setSwitchType(SwitchType.PHYSICAL);
        physicalSwitchInfo.setSwitchState(SwitchState.OFF);

        //dummy
        Constant.PHYSICAL_SWITCH = 0;
    }

    @Override
    public void onPhysicalSwitch(SwitchInfo physicalSwitchInfo) {
        System.out.println("physical switch on ");
        physicalSwitchInfo.setSwitchName(SwitchName.LIGHT_1);
        physicalSwitchInfo.setSwitchType(SwitchType.PHYSICAL);
        physicalSwitchInfo.setSwitchState(SwitchState.ON);

        //dummy
        Constant.PHYSICAL_SWITCH = 1;
    }
}
