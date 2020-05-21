package com.abdullah.home.automation.dto.response;

import com.abdullah.home.automation.domain.Payload2;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponseDto {

    private String filtarDate;
    private String payloadType;
    List<Payload2> payload2List;

}
