package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.domain.Switch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SwitchCentralRegistry {

    private static final Logger log = LoggerFactory.getLogger(SwitchCentralRegistry.class);

    public static Map<String,Switch> centralSwitchMap = new HashMap<>();

    public static Map<String, Switch> getCentralSwitchMap() {
        return centralSwitchMap;
    }

    public static void setCentralSwitchMap(Map<String, Switch> centralSwitchMap) {
        SwitchCentralRegistry.centralSwitchMap = centralSwitchMap;
    }

}
