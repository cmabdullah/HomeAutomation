package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import com.abdullah.home.automation.repository.SwitchRepository;
import com.abdullah.home.automation.service.SwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SwitchServiceImpl implements SwitchService {

    private final SwitchRepository switchRepository;

    @Autowired
    public SwitchServiceImpl(SwitchRepository switchRepository){
        this.switchRepository = switchRepository;
    }

    @Override
    public List<Switch> migrateSwitchInfo() {

        //delete old data -> not implemented yet
        switchRepository.deleteAll();

        List<Switch> switches = List.of(new Switch("light1", 0.0, 0,0,0),
                new Switch("fan1", 0.0, 0,0,0));
        List<Switch> switchesList = (List<Switch>) switchRepository.saveAll(switches);

        SwitchCentralRegistry.centralSwitchMap = listToMapConverter(switchesList);
        return switchesList;
    }

    Map<String,Switch> listToMapConverter(List<Switch> switchesList){
        Map<String,Switch> centralSwitch = switchesList.stream()
                .collect(Collectors.toMap(entry -> entry.getSwitchName(), entry -> entry));
        return centralSwitch;
    }


    @Override
    public List<Switch> findAllSwitch() {
        return (List<Switch>) switchRepository.findAll();
    }

    @Override
    public Map<String,Switch> findAllSwitchMap() {
        return listToMapConverter(findAllSwitch());
    }

    @Override
    public Optional<Switch> findBySwitchName(String switchName) {
        return switchRepository.findBySwitchName(switchName);
    }

    @Override
    public Switch save(Switch mySwitch) {
        return switchRepository.save(mySwitch);
    }
}
