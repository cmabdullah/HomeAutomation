package com.abdullah.home.automation.constants.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SwitchState {

    UNDEFINED("undefined"),
    ON("on"),
    OFF("off");

    private String value;

    SwitchState(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static SwitchState forValue(String value){

        if (value == null){
            return UNDEFINED;
        }

        switch (value){
            case "on" : return ON;
            case "off" : return OFF;
            default: return UNDEFINED;
        }
    }
}
