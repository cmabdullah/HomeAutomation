package com.abdullah.home.automation.dto.request;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.constants.enums.SwitchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SwitchInfo {
    private SwitchName switchName;
    private SwitchState switchState;
    private SwitchType switchType;
}
