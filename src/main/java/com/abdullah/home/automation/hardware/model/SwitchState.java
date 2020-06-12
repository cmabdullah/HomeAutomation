package com.abdullah.home.automation.hardware.model;

public class SwitchState {

    private String switchState;

    private String switchName;

    public String getSwitchState() {
        return switchState;
    }

    public void setSwitchState(String switchState) {
        this.switchState = switchState;
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    @Override
    public String toString() {
        return "SwitchState{" +
                "switchState='" + switchState + '\'' +
                ", switchName='" + switchName + '\'' +
                '}';
    }
}
