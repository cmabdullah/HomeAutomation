package com.abdullah.home.automation.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResponseDto {

    private String filtarDate;
    private String payloadType;
    List<Payload2> payload2List;

}
