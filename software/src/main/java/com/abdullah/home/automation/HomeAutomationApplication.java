package com.abdullah.home.automation;

import com.abdullah.home.automation.domain.MainSwitch;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import com.abdullah.home.automation.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class HomeAutomationApplication implements CommandLineRunner {

	private final SwitchService switchService;
	private final DataMigrationService dataMigrationService;

	@Autowired
	public HomeAutomationApplication(SwitchService switchService,DataMigrationService dataMigrationService) {
		this.switchService = switchService;
		this.dataMigrationService = dataMigrationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(HomeAutomationApplication.class, args);
	}

	@Override
	public void run(String... args) {
		SwitchCentralRegistry.centralSwitchMap = switchService.findAllSwitchMap();
		MainSwitch mainSwitch = dataMigrationService.getMainSwitchState();
		log.debug("Current state "+ mainSwitch);
	}
}