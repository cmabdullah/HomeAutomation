package com.abdullah.home.automation.registry;

import com.abdullah.home.automation.domain.model.Switch;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SwitchCentralRegistry {

    public static Map<String,Switch> centralSwitchMap = new HashMap<>();
}
