package com.abdullah.home.automation.dto;

import com.abdullah.home.automation.constants.enums.SwitchName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegulatorDto {

    private int voltageRange;
    private SwitchName switchName;
}
