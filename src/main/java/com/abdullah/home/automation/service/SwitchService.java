package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.model.Switch;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SwitchService {
    List<Switch> migrateSwitchInfo();

    List<Switch> findAllSwitch();

    Optional<Switch> findBySwitchName(String switchName);
    Map<String,Switch> findAllSwitchMap();
    Switch save(Switch mySwitch);
}
