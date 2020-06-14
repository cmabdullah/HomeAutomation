package com.abdullah.home.automation.config;

import com.abdullah.home.automation.dto.config.HardwareConfigDto;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;

public class HardwareConfig {

    private String hardwareMode;

    private HardwareConfig(String hardwareMode) {
        this.hardwareMode = hardwareMode;
    }

    private static class Holder {
        private static HardwareConfig instance = new HardwareConfig(loadConfig());
    }

    public static HardwareConfig getInstance() {
        return Holder.instance;
    }

    public String getHardwareMode() {
        return hardwareMode;
    }

    private static String loadConfig() {

        try {
            Yaml yaml = new Yaml(new Constructor(HardwareConfigDto.class));
            File file = new File("hardwareConfig.yaml");
            HardwareConfigDto hardwareConfigDto = yaml.load(new FileInputStream(file));
            return hardwareConfigDto.getHardwareMode();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Hardware configuration failed");
        }
    }

    public boolean isConfigValid() {

        return hardwareMode != null;
    }
}
