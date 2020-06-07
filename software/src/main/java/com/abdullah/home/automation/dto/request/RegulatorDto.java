package com.abdullah.home.automation.dto.request;

import com.abdullah.home.automation.constants.enums.SwitchName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegulatorDto implements Serializable {

    private int voltageRange;
    private SwitchName switchName;
}
