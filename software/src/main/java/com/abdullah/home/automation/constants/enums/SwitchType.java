package com.abdullah.home.automation.constants.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SwitchType {

    UNDEFINED("undefined"),
    PHYSICAL("physical"),
    LOGICAL("logical"),
    PI("pi");

    private String value;

    SwitchType(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static SwitchType forValue(String value){

        if (value == null){
            return UNDEFINED;
        }

        switch (value){
            case "physical" : return PHYSICAL;
            case "logical" : return LOGICAL;
            case "pi" : return PI;
            default: return UNDEFINED;
        }
    }
}
