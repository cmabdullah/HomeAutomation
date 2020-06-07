package com.abdullah.home.automation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StationsDto implements Serializable {

    private String stationId;
    private String country;
    private String stationName;
    private String number1;
    private String number2;
    private String quantity;
    private String stationKey;
    private String stationId2;
    private String key2;
    private String state;
}
