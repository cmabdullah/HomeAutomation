package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.MainSwitch;
import com.abdullah.home.automation.repository.MainSwitchRepository;
import com.abdullah.home.automation.service.MainSwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainSwitchServiceImpl implements MainSwitchService {

    private final MainSwitchRepository mainSwitchRepository;

    @Autowired
    public MainSwitchServiceImpl(MainSwitchRepository mainSwitchRepository){
        this.mainSwitchRepository = mainSwitchRepository;
    }
    @Override
    public List<MainSwitch> findAll() {
        return (List<MainSwitch>) mainSwitchRepository.findAll();
    }

    @Override
    public MainSwitch save(MainSwitch mainSwitch) {
        return mainSwitchRepository.save(mainSwitch);
    }
}
