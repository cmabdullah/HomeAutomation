package com.abdullah.home.automation.config;

import com.abdullah.home.automation.constants.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegulatorConfig {

    private static final Logger log = LoggerFactory.getLogger(RegulatorConfig.class);

    @Value("${regulator.mode}")
    private String regulatorName;

    @Bean
    public void setName() {

        Constant.REGULATOR_NAME = regulatorName;
        log.debug("regulator config status "+ regulatorName);
    }
}
