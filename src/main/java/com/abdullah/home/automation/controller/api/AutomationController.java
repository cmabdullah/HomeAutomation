package com.abdullah.home.automation.controller.api;

import com.abdullah.home.automation.constants.enums.SwitchName;
import com.abdullah.home.automation.constants.enums.SwitchState;
import com.abdullah.home.automation.constants.enums.SwitchType;
import com.abdullah.home.automation.dto.request.RegulatorDto;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.SwitchInfo;
import com.abdullah.home.automation.exception.ApiResponse;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import com.abdullah.home.automation.service.AutomationService;
import com.abdullah.home.automation.service.RegulatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
//https://mkyong.com/spring-boot/spring-boot-ajax-example/
@RestController
public class AutomationController {

    private final RegulatorService regulatorService;

    private final AutomationService automationService;

    @Autowired
    AutomationController(RegulatorService regulatorService,
                         AutomationService automationService) {
        this.regulatorService = regulatorService;
        this.automationService = automationService;

    }

    @ResponseBody
        @PostMapping("/regulatorRest")
    public ApiResponse<String> regulatorPost(@RequestBody RegulatorDto regulatorDto, Errors errors) {

        if (errors.hasErrors()){
            log.error("Error");
        }

        log.info("regulatorDto : " + regulatorDto.toString());
        boolean regulatorDtoValidation = regulatorDtoValidationCheck(regulatorDto);

        if (regulatorDtoValidation) {
            return new ApiResponse<>("bad request");
        }

        //int req = regulatorService.postSocketRequest(regulatorDto.getVoltageRange());
        int voltageRegulatorValue = regulatorService.voltageRegulator(regulatorDto);
        log.info("voltageRegulatorValue "+ voltageRegulatorValue);

        return new ApiResponse<>("Success");
    }


    @PostMapping("/logicalSwitchRest")
    public ResponseEntity<?> logicalSwitchPost(@Valid @RequestBody SwitchInfo logicalSwitchInfo, Errors errors) {

        boolean switchInfoValidation = switchInfoValidationCheck(logicalSwitchInfo);
        if (switchInfoValidation) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        log.info("logicalSwitchInfo : " + logicalSwitchInfo.toString());
        automationService.changeSwitchStateLogicalRequest(logicalSwitchInfo);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/physicalSwitchRest")
    public ResponseEntity<?> lightPhysicalTest(@Valid @RequestBody SwitchInfo physicalSwitchInfo, Errors errors) {

        boolean switchInfoValidation = switchInfoValidationCheck(physicalSwitchInfo);
        if (switchInfoValidation) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        log.info("physicalSwitchInfo : " + physicalSwitchInfo.toString());
        automationService.changeSwitchStatePhysicalRequest(physicalSwitchInfo);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/switches")
    public Map<String, Switch> switches() {
        Map<String, Switch> switchMap = SwitchCentralRegistry.centralSwitchMap;
        return switchMap;
    }


    private boolean switchInfoValidationCheck(SwitchInfo logicalSwitchInfo) {

        if (SwitchName.UNDEFINED == SwitchName.forValue(logicalSwitchInfo.getSwitchName().getValue()) ||
                SwitchState.UNDEFINED == SwitchState.forValue(logicalSwitchInfo.getSwitchState().getValue()) ||
                SwitchType.UNDEFINED == SwitchType.forValue(logicalSwitchInfo.getSwitchType().getValue()) ||
                SwitchType.PI == SwitchType.forValue(logicalSwitchInfo.getSwitchType().getValue())) {
            return true;
        }
        return false;
    }

    private boolean regulatorDtoValidationCheck(RegulatorDto regulatorDto) {

        if (regulatorDto.getVoltageRange() < 0 || regulatorDto.getVoltageRange() > 100
                || SwitchName.UNDEFINED == SwitchName.forValue(regulatorDto.getSwitchName().getValue())
        ) {
            return true;
        }

        String switchName = regulatorDto.getSwitchName().getValue();

        if (!switchName.contains("fan")) {
            return true;
        }

        return false;
    }
}
