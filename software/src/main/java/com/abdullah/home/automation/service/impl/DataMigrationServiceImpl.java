package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.MainSwitch;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.domain.WeatherEntity;
import com.abdullah.home.automation.dto.request.StationsDto;
import com.abdullah.home.automation.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DataMigrationServiceImpl implements DataMigrationService {

    private final SwitchService switchService;
    private final StationService stationService;
    private final WeatherEntityService weatherEntityService;
    private final MainSwitchService mainSwitchService;

    @Autowired
    public DataMigrationServiceImpl(StationService stationService,
                                    SwitchService switchService,
                                    WeatherEntityService weatherEntityService,
                                    MainSwitchService mainSwitchService) {
        this.stationService = stationService;
        this.switchService = switchService;
        this.weatherEntityService = weatherEntityService;
        this.mainSwitchService = mainSwitchService;
    }

    @Override
    public MainSwitch getMainSwitchState() {

        List<MainSwitch> list = mainSwitchService.findAll();

        MainSwitch migSuccessMainSwitch = null;

        if (list == null || list.size() == 0) {
            MainSwitch mainSwitch = MainSwitch.builder().dataMigrationSwitch(false)
                    .pinModeLogicalPhysicalSwitch(false).pinModeLogicalSwitch(false)
                    .pinModePhysicalSwitch(false).build();
            MainSwitch mainSwitchSave = mainSwitchService.save(mainSwitch);

            boolean migrateData = migrateData();

            if (migrateData) {
                migSuccessMainSwitch = migrationSuccessOrRevoke(mainSwitchSave);
            }
        } else if (list.size() == 1) {
            MainSwitch mainSwitch = list.get(0);
            migSuccessMainSwitch = mainSwitch;
            if (!mainSwitch.isDataMigrationSwitch()){
                boolean migrateData = migrateData();
                if (migrateData) {
                    migSuccessMainSwitch = migrationSuccessOrRevoke(mainSwitch);
                }
            }else {
                log.debug("Data already migrated");
            }
        }

        return migSuccessMainSwitch;
    }

    private MainSwitch migrationSuccessOrRevoke(MainSwitch mainSwitch) {

        mainSwitch.setDataMigrationSwitch(true);
        mainSwitch.setPinModeLogicalPhysicalSwitch(true);

        MainSwitch mainSwitchStateUpdate = mainSwitchService.save(mainSwitch);

        try {
            if (mainSwitchStateUpdate == null) {
                deleteAll();
                log.error("Data mig failed");
                throw new RuntimeException("Data mig failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("Data mig success");

        return mainSwitch;
    }

    private boolean migrateData() {

        boolean stationMig;
        boolean switchMig;
        boolean weatherEnticeMig;

        List<StationsDto> stations = stationService.migrateStationInfo();

        stationMig = stations.size() == 30411;

        List<Switch> switches = switchService.migrateSwitchInfo();

        switchMig = switches.size() == 2;

        List<WeatherEntity> weatherEntices = weatherEntityService.migrateWeatherEntity();

        weatherEnticeMig = weatherEntices.size() == 7;

        if (!stationMig || !switchMig || !weatherEnticeMig) {
            deleteAll();
            log.error("Data mig failed");
            return false;
        } else {
            return true;
        }
    }

    private void deleteAll(){
        stationService.deleteAll();
        switchService.deleteAll();
        weatherEntityService.deleteAll();
    }

}
