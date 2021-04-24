package com.abdullah.home.automation.registry.impl;

import com.abdullah.home.automation.config.Subscriber;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.hardware.sensor.SensorContext;
import com.abdullah.home.automation.hardware.service.HardwareService;
import com.abdullah.home.automation.registry.HardwareRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class HardwareRegistryImpl implements HardwareRegistry {

    private final HardwareService hardwareService;

    private final SensorContext sensorContext;

    private final Subscriber subscriber;

    private static final Logger log = LoggerFactory.getLogger(HardwareRegistryImpl.class);


    @Autowired
    public HardwareRegistryImpl(HardwareService hardwareService, SensorContext sensorContext
            ,Subscriber subscriber
    ) {
        this.hardwareService = hardwareService;
        this.sensorContext = sensorContext;
        this.subscriber = subscriber;
    }

    @Value("${hardware.hardwareMode}")
    String devProfile;
    //String devProfile = HardwareConfig.getInstance().getHardwareMode();
    @Override
    public boolean save(Switch switchInfo) {

        if (devProfile.equals("with-hardware")) {
            log.debug("Hardware switch is operating");
            if (switchInfo.getPiSwitch() == 1) {
                return hardwareService.on(switchInfo.getSwitchName());
            } else if (switchInfo.getPiSwitch() == 0) {
                return hardwareService.off(switchInfo.getSwitchName());
            }
        } else if (devProfile.equals("without-hardware")) {
            log.warn("Hardware switch not connected");
            return true;
        }
        return false;
    }

    @Override
    public boolean hardwareConfig() {

        if (devProfile.equals("with-hardware")) {
            boolean buttonConfig = hardwareService.buttonConfig();
            boolean mainSwitchConfig = hardwareService.mainSwitchConfig(true, true);
            return (buttonConfig && mainSwitchConfig);
        } else if (devProfile.equals("without-hardware")) {
            log.warn("Hardware buttons not connected");
            return false;
        }
        return false;
    }

    @Override
    public boolean sensorConfig() {
        //taskExecutor().execute(subscriber);
        if (devProfile.equals("with-hardware")) {

		//comment this block for jdk 11 compatiblity issue
            boolean sensorStatus = true; //sensorContext.runAllWeatherSensor();
            //taskExecutor().execute(subscriber);
            log.debug("sensor thread size -> "+ sensorStatus);
            return sensorStatus;
        } else if (devProfile.equals("without-hardware")) {
            log.warn("Hardware sensors not connected");
            return false;
        }
        return false;
    }

    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
    }
}
