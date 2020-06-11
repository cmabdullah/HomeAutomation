package com.abdullah.home.automation.config;

import com.abdullah.home.automation.constants.Constant;
import com.abdullah.home.automation.service.RegulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

    private RegulatorService regulatorService;

    @Autowired
    public CustomHealthIndicator(RegulatorService regulatorService) {
        this.regulatorService = regulatorService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        // Use the builder to build the health status details that should be reported.
        // If you throw an exception, the status will be DOWN with the exception message.

        try {
            regulatorService.getRegulatorProcessId();
            builder.up()
                    .withDetail("app", Constant.REGULATOR_NAME)
                    .withDetail("error", "Nothing! I'm good.");
        } catch (Exception e) {
            builder.down()
                    .withDetail("app", Constant.REGULATOR_NAME)
                    .withDetail("error", "Device offline");

        }
    }
}