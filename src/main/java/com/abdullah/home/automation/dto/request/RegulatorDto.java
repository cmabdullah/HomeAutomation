package com.abdullah.home.automation.dto.request;

import com.abdullah.home.automation.constants.enums.SwitchName;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class RegulatorDto implements Serializable {

    private int voltageRange;
    private SwitchName switchName;

    public RegulatorDto() {

    }

    public RegulatorDto(int voltageRange, SwitchName switchName) {
        this.voltageRange = voltageRange;
        this.switchName = switchName;
    }

    public int getVoltageRange() {
        return voltageRange;
    }

    public void setVoltageRange(int voltageRange) {
        this.voltageRange = voltageRange;
    }

    public SwitchName getSwitchName() {
        return switchName;
    }

    public void setSwitchName(SwitchName switchName) {
        this.switchName = switchName;
    }

    @Override
    public String toString() {
        return "RegulatorDto{" +
                "voltageRange=" + voltageRange +
                ", switchName=" + switchName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegulatorDto)) return false;
        RegulatorDto that = (RegulatorDto) o;
        return getVoltageRange() == that.getVoltageRange() &&
                getSwitchName() == that.getSwitchName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoltageRange(), getSwitchName());
    }
}
