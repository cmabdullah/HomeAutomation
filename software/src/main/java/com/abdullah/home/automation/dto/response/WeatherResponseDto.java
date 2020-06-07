package com.abdullah.home.automation.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WeatherResponseDto implements Serializable {

    private String filtarDate;
    private String payloadType;
    private List<Payload2> payload2List;

}
