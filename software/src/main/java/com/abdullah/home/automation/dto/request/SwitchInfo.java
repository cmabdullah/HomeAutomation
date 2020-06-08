package com.abdullah.home.automation.dto.request;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.constants.enums.SwitchType;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class SwitchInfo implements Serializable {
    private SwitchName switchName;
    private SwitchState switchState;
    private SwitchType switchType;

    public SwitchInfo() {

    }

    public SwitchInfo(SwitchName switchName, SwitchState switchState, SwitchType switchType) {
        this.switchName = switchName;
        this.switchState = switchState;
        this.switchType = switchType;
    }

    public SwitchName getSwitchName() {
        return switchName;
    }

    public void setSwitchName(SwitchName switchName) {
        this.switchName = switchName;
    }

    public SwitchState getSwitchState() {
        return switchState;
    }

    public void setSwitchState(SwitchState switchState) {
        this.switchState = switchState;
    }

    public SwitchType getSwitchType() {
        return switchType;
    }

    public void setSwitchType(SwitchType switchType) {
        this.switchType = switchType;
    }

    @Override
    public String toString() {
        return "SwitchInfo{" +
                "switchName=" + switchName +
                ", switchState=" + switchState +
                ", switchType=" + switchType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SwitchInfo)) return false;
        SwitchInfo that = (SwitchInfo) o;
        return getSwitchName() == that.getSwitchName() &&
                getSwitchState() == that.getSwitchState() &&
                getSwitchType() == that.getSwitchType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSwitchName(), getSwitchState(), getSwitchType());
    }
}
