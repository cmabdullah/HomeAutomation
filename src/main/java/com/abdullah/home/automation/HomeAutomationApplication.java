package com.abdullah.home.automation;

import com.abdullah.home.automation.config.Mqtt;
import com.abdullah.home.automation.config.SensorInitializer;
import com.abdullah.home.automation.config.Subscriber;
import com.abdullah.home.automation.domain.MainSwitch;
import com.abdullah.home.automation.registry.HardwareRegistry;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import com.abdullah.home.automation.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HomeAutomationApplication implements CommandLineRunner {

    private final SwitchService switchService;
    private final DataMigrationService dataMigrationService;
    private final HardwareRegistry hardwareRegistry;
    private final SensorInitializer sensorInitializer;
    private final Subscriber subscriber;

    private static final Logger log = LoggerFactory.getLogger(HomeAutomationApplication.class);

    @Autowired
    public HomeAutomationApplication(SwitchService switchService, DataMigrationService dataMigrationService,
                                     HardwareRegistry hardwareRegistry, SensorInitializer sensorInitializer,Subscriber subscriber ) {
        this.switchService = switchService;
        this.dataMigrationService = dataMigrationService;
        this.hardwareRegistry = hardwareRegistry;
	    this.sensorInitializer = sensorInitializer;
        this.subscriber = subscriber;
    }

    public static void main(String[] args) {
        SpringApplication.run(HomeAutomationApplication.class, args);
    }

    @Override
    public void run(String... args) {
        SwitchCentralRegistry.centralSwitchMap = switchService.findAllSwitchMap();
        MainSwitch mainSwitch = dataMigrationService.getMainSwitchState();
        log.debug("Current state " + mainSwitch);

        boolean piConfig = hardwareRegistry.hardwareConfig();
        log.debug("pi hardware config : " + piConfig);

        boolean sensorConfig = hardwareRegistry.sensorConfig();
        log.debug("pi sensorConfig config : " + sensorConfig);
        Mqtt.getInstance();
        new Thread(subscriber.listener).start();

        //SensorInitializer sensorInitializer = new SensorInitializer();

        new Thread(sensorInitializer.sensorRunnable).start();
	Mqtt.getInstance();
    }
}
