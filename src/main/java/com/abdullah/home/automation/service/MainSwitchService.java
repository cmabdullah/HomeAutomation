package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.MainSwitch;

import java.util.List;

public interface MainSwitchService {

    List<MainSwitch> findAll();

    MainSwitch save(MainSwitch mainSwitch);
}
