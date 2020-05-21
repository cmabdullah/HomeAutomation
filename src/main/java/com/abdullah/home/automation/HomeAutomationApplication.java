package com.abdullah.home.automation;

import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import com.abdullah.home.automation.service.SwitchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class HomeAutomationApplication implements CommandLineRunner {

	private final SwitchService switchService;

	HomeAutomationApplication(SwitchService switchService){
		this.switchService = switchService;
	}

	public static void main(String[] args) {
		SpringApplication.run(HomeAutomationApplication.class, args);
	}

	@Override
	public void run(String... args) {
		SwitchCentralRegistry.centralSwitchMap = switchService.findAllSwitchMap();
	}
}