package com.abdullah.home.automation.hardware.service;

import com.abdullah.home.automation.hardware.model.CustomHardware;
import org.springframework.stereotype.Service;

@Service
public class CustomHardwareService {

    public  CustomHardware get(){

        return new CustomHardware("CM");
    }
}
