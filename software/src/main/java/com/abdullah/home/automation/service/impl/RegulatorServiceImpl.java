package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.constants.Constant;
import com.abdullah.home.automation.domain.Switch;
import com.abdullah.home.automation.dto.request.RegulatorDto;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.exception.ApiMessage;
import com.abdullah.home.automation.registry.SwitchCentralRegistry;
import com.abdullah.home.automation.service.RegulatorService;
import com.abdullah.home.automation.service.SwitchService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RegulatorServiceImpl implements RegulatorService {

    private final SwitchService switchService;

    @Autowired
    public RegulatorServiceImpl(SwitchService switchService) {
        this.switchService = switchService;
    }

    private String postSocketRequest(int regulatorParam, int processId) {

        String data = regulatorParam + "&"+ processId;

        String result = "";
        try {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            System.out.println(serverAddress.getHostAddress());
            Socket socket = new Socket(serverAddress, 2017);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(data);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            result = input.readLine();
            log.info("c++ server response : "+ result);

            out.close();
            input.close();
            socket.close();

            if (result.length() != 0){
                return result.trim();
            }

        } catch (UnknownHostException e) {
            log.error(e.toString());
            throw new ApiError(ApiMessage.UNKNOWN_HOST_EXCEPTION, HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            log.error(e.toString());
            throw new ApiError(ApiMessage.IO_ERROR_WHILE_FETCHING_DATA_FROM_REGULATOR_ACK, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            log.error(e.toString());
            throw new ApiError(ApiMessage.ILLEGAL_ARGUMENT_EXCEPTION, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ApiError(ApiMessage.SERVER_RESPONSE_ERROR + " "+ e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
        throw new ApiError(ApiMessage.SERVER_RESPONSE_ERROR, HttpStatus.NOT_FOUND);
    }

    @Override
    public int voltageRegulator(RegulatorDto regulatorDto) {

        String switchName = regulatorDto.getSwitchName().getValue();
        Switch switchInfo = switchService.findBySwitchName(switchName)
                .orElseThrow(ApiError.createSingletonSupplier(ApiMessage.SWITCH_NOT_FOUND, HttpStatus.EXPECTATION_FAILED));
        double getVoltageRangeToActualVoltage = getVoltageRangeToActualVoltage(regulatorDto.getVoltageRange());
        switchInfo.setVoltageRange(getVoltageRangeToActualVoltage);

        int processId = getRegulatorProcessId();

        String ack = postSocketRequest((int) Math.round(getVoltageRangeToActualVoltage), processId);

        //post value through socket

        log.info("acknowledgement "+ack);

        if (ack.equals("write success")){
            Switch switchUpdate = switchService.save(switchInfo);
            SwitchCentralRegistry.centralSwitchMap.put(switchName, switchUpdate);
            return getActualVoltageToVoltageRange(switchUpdate.getVoltageRange());
        }else {
            throw new ApiError(ApiMessage.ACCESS_DENIED_FROM_CP_SERVER, HttpStatus.BAD_REQUEST);
        }

    }

    private double getVoltageRangeToActualVoltage(int voltageRange) {
        return ((100 - voltageRange) * 128) / 100;
    }

    private int getActualVoltageToVoltageRange(double voltage) {
        Double res = (100 - (voltage * 100) / 128);
        return res.intValue();
    }

    @Override
    public int getRegulatorProcessId() {

        ProcessBuilder processBuilder = new ProcessBuilder();
        String[] cmd = {"bash", "-c", "ps aux | grep "+Constant.REGULATOR_NAME};

        try {
            processBuilder.command(cmd);

            Process subProcess = processBuilder.start();

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(subProcess.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(subProcess.getErrorStream()));

            // read the output from the command
            String results = stdIn.lines().collect(Collectors.joining(System.lineSeparator()));
            // read any errors from the attempted command
            String errors = stdErr.lines().collect(Collectors.joining(System.lineSeparator()));

            if (results != null) {
                log.info("Here is the standard output of the command -> \n");
                log.info(results);
                String[] arr = results.split("\n");
                log.debug("arr length : " + arr.length);

                if (arr.length == 3) {

                    String parsableString = List.of(arr).stream().filter(n -> n.contains("./"+Constant.REGULATOR_NAME)).findAny()
                            .orElseThrow(ApiError.createSingletonSupplier(ApiMessage.PARSABLE_STRING_NOT_FOUND, HttpStatus.EXPECTATION_FAILED));

                    log.info("parsableString is : " + parsableString);

                    String[] strArr = parsableString.split("\\s+");
                    if (strArr.length == 11) {
                        log.info("process id  " + strArr[1]);
                        return Integer.parseInt(strArr[1]);
                    } else {
                        log.error("process id  not found ");
                    }
                    
                } else {
                    throw new ApiError(ApiMessage.PROCESS_INFO_PARSE_ERROR, HttpStatus.EXPECTATION_FAILED);
                }
            }
            //cmabdullahkhan    2061   0.0  0.1  4277668   4440 s001  S+    2:05PM   0:00.01 ./VoltageRegulatorFinalShape
            if (!errors.trim().equals("")) {
                log.info("Here is the standard error of the command (if any) -> ");
                log.error(errors);
                throw new ApiError(ApiMessage.PROCESS_INFO_READ_ERROR + " " + errors, HttpStatus.EXPECTATION_FAILED);
            }
            //System.exit(0);
        } catch (IOException e) {
            log.error("exception happened - here's what I know -> ");
            //e.printStackTrace();
            //System.exit(-1);
            throw new ApiError(ApiMessage.IO_ERROR_WHILE_GRIP_PROCESS, HttpStatus.EXPECTATION_FAILED);
        }
        throw new ApiError(ApiMessage.PROCESS_ID_NOT_FOUND, HttpStatus.NOT_FOUND);
    }


}
