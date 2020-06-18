package com.abdullah.home.automation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HardwareConfig {

    private static final Logger log = LoggerFactory.getLogger(HardwareConfig.class);

    @Value("${hardware.hardwareMode}")
    private String hardwareMode;

    @Bean
    public void debugLog() {
        log.debug("hardwareMode config status "+ hardwareMode);
    }

    public String getHardwareMode() {
        return hardwareMode;
    }

}
