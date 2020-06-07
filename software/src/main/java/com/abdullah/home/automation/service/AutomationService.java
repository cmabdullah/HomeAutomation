package com.abdullah.home.automation.service;

import com.abdullah.home.automation.dto.request.SwitchInfo;

public interface AutomationService {
    void changeSwitchStateLogicalRequest(SwitchInfo logicalSwitchInfo);
    void changeSwitchStatePhysicalRequest(SwitchInfo physicalSwitchInfo);
}
