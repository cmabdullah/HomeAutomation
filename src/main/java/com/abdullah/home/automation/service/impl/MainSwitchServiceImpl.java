package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.MainSwitch;
import com.abdullah.home.automation.repository.MainSwitchRepository;
import com.abdullah.home.automation.service.MainSwitchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainSwitchServiceImpl implements MainSwitchService {

    private final MainSwitchRepository mainSwitchRepository;

    private static final Logger log = LoggerFactory.getLogger(MainSwitchServiceImpl.class);

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
