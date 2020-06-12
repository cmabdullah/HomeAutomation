package com.abdullah.home.automation.hardware.model;

import java.util.Objects;


public class HardwareMainSwitch {

    private boolean pinModeLogicalSwitch;
    private boolean pinModePhysicalSwitch;

    public HardwareMainSwitch() {
    }

    public HardwareMainSwitch(boolean pinModeLogicalSwitch, boolean pinModePhysicalSwitch) {
        this.pinModeLogicalSwitch = pinModeLogicalSwitch;
        this.pinModePhysicalSwitch = pinModePhysicalSwitch;
    }


    public boolean isPinModeLogicalSwitch() {
        return pinModeLogicalSwitch;
    }

    public void setPinModeLogicalSwitch(boolean pinModeLogicalSwitch) {
        this.pinModeLogicalSwitch = pinModeLogicalSwitch;
    }

    public boolean isPinModePhysicalSwitch() {
        return pinModePhysicalSwitch;
    }

    public void setPinModePhysicalSwitch(boolean pinModePhysicalSwitch) {
        this.pinModePhysicalSwitch = pinModePhysicalSwitch;
    }

    @Override
    public String toString() {
        return "MainSwitch{" +
                "pinModeLogicalSwitch=" + pinModeLogicalSwitch +
                ", pinModePhysicalSwitch=" + pinModePhysicalSwitch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HardwareMainSwitch)) return false;
        HardwareMainSwitch that = (HardwareMainSwitch) o;
        return isPinModeLogicalSwitch() == that.isPinModeLogicalSwitch() &&
                pinModePhysicalSwitch == that.pinModePhysicalSwitch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPinModeLogicalSwitch(), pinModePhysicalSwitch);
    }
}
