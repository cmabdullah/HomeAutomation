package com.abdullah.home.automation.constants.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SwitchName {

    UNDEFINED("undefined"),
    LIGHT_1("light1"),
    FAN_1("fan1");

    private String value;

    SwitchName(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static SwitchName forValue(String value) {

        if (value == null) {
            return UNDEFINED;
        }

        switch (value) {
            case "light1":
                return LIGHT_1;
            case "fan1":
                return FAN_1;
            default:
                return UNDEFINED;
        }
    }
}
