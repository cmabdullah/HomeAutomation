package com.abdullah.home.automation.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class StationInfoDto {
    private List<String> stations ;
    private List<String> payloadTypes;

}
