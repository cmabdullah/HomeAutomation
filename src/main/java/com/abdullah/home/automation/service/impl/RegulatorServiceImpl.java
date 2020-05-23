package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.RegulatorDto;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.exception.ApiMessage;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import com.abdullah.home.automation.service.RegulatorService;
import com.abdullah.home.automation.service.SwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@Service
public class RegulatorServiceImpl implements RegulatorService {

    private final SwitchService switchService;

    @Autowired
    public RegulatorServiceImpl(SwitchService switchService){
        this.switchService = switchService;
    }

    @Override
    public int postSocketRequest(int regulatorParam) {

        String result = "";
        try {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            System.out.println(serverAddress.getHostAddress());
            Socket socket = new Socket(serverAddress , 2017);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(regulatorParam);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            result = input.readLine();
            System.out.println(input.readLine());

            out.close();
            //input.close();
            socket.close();

        }catch(UnknownHostException e) {
            System.out.println(e.toString());
        }catch(IOException e) {
            System.out.println(e.toString());
        }catch(IllegalArgumentException e) {
            System.out.println(e.toString());
        }catch(Exception e) {
            System.out.println(e.toString());
        }
        return 0;
    }

    @Override
    public int voltageRegulator(RegulatorDto regulatorDto) {

        String switchName = regulatorDto.getSwitchName().getValue();
        Switch switchInfo = switchService.findBySwitchName(switchName)
                .orElseThrow(ApiError.createSingletonSupplier(ApiMessage.SWITCH_NOT_FOUND, HttpStatus.EXPECTATION_FAILED));
        double getVoltageRangeToActualVoltage = getVoltageRangeToActualVoltage(regulatorDto.getVoltageRange());
        switchInfo.setVoltageRange(getVoltageRangeToActualVoltage);

        Switch switchUpdate  = switchService.save(switchInfo);
        SwitchCentralRegistry.centralSwitchMap.put(switchName, switchUpdate);

        return getActualVoltageToVoltageRange(switchUpdate.getVoltageRange());
    }

    private double getVoltageRangeToActualVoltage(int voltageRange) {
        return  ((100-voltageRange) * 128) / 100;
    }

    private int getActualVoltageToVoltageRange(double voltage) {
        Double res = (100 -  (voltage*100)/128);;
        return  res.intValue();
    }


}
