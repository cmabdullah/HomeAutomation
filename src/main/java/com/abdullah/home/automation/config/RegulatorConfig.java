package com.abdullah.home.automation.config;

import com.abdullah.home.automation.constants.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegulatorConfig {

    @Value("${regulator.mode}")
    private String regulatorName;

    @Bean
    public void setName() {

        Constant.REGULATOR_NAME = regulatorName;
    }
}
