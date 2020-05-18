package com.abdullah.home.automation.controller.api;

import com.abdullah.home.automation.constants.Constant;
import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.domain.RegulatorDto;
import com.abdullah.home.automation.dto.SwitchInfo;
import com.abdullah.home.automation.service.AutomationService;
import com.abdullah.home.automation.service.RegulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

//https://mkyong.com/spring-boot/spring-boot-ajax-example/
@RestController
public class AutomationController {

    private final RegulatorService regulatorService;

    private final AutomationService automationService;

    @Autowired
    AutomationController(RegulatorService regulatorService,
                         AutomationService automationService){
        this.regulatorService = regulatorService;
        this.automationService = automationService;

    }

    @ResponseBody
    @PostMapping("/regulatorRest")
    public ResponseEntity<?> regulatorPost(@Valid @RequestBody RegulatorDto regulatorDto, Errors errors) {

        System.out.println("regulatorDto : "+ regulatorDto.toString());

        if (regulatorDto.getRegulatorParam() >= 0 && regulatorDto.getRegulatorParam()<=128){
            int req = regulatorService.postSocketRequest(regulatorDto.getRegulatorParam());
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/lightRest")
    public ResponseEntity<?> lightPost(@Valid @RequestBody SwitchInfo logicalSwitchInfo, Errors errors) {

        boolean switchInfoValidation = switchInfoValidationCheck(logicalSwitchInfo);
        if (switchInfoValidation){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        System.out.println("regulatorDto : "+ logicalSwitchInfo.toString());
        automationService.changeSwitchStateLogicalRequest(logicalSwitchInfo);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/lightRestPhysical")
    public ResponseEntity<?> lightPhysicalTest(@Valid @RequestBody SwitchInfo physicalSwitchInfo, Errors errors) {

        boolean switchInfoValidation = switchInfoValidationCheck(physicalSwitchInfo);
        if (switchInfoValidation){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        System.out.println("regulatorDto : "+ physicalSwitchInfo.toString());
        automationService.changeSwitchStatePhysicalRequest(physicalSwitchInfo);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/lightRestPhysical")
    public Map switchState() {

          Map<String, Integer> map = new HashMap<>();
          map.put("PHYSICAL_SWITCH", Constant.PHYSICAL_SWITCH);
          map.put("LOGICAL_SWITCH", Constant.LOGICAL_SWITCH);
          map.put("PI_SWITCH", Constant.PI_SWITCH);

        return map;
    }




    private boolean switchInfoValidationCheck(SwitchInfo logicalSwitchInfo) {

        if (SwitchName.UNDEFINED == SwitchName.forValue(logicalSwitchInfo.getSwitchName().getValue()) ||
            SwitchState.UNDEFINED == SwitchState.forValue(logicalSwitchInfo.getSwitchState().getValue()) ||
            SwitchType.UNDEFINED == SwitchType.forValue(logicalSwitchInfo.getSwitchType().getValue())){
            return true;
        }
        return false;
    }
}
