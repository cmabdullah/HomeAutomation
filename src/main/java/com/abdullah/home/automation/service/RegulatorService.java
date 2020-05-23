package com.abdullah.home.automation.service;

import com.abdullah.home.automation.dto.request.RegulatorDto;

public interface RegulatorService {
    int postSocketRequest(int regulatorParam);

    int voltageRegulator(RegulatorDto regulatorDto);
}
