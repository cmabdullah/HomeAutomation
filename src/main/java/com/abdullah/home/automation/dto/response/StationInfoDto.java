package com.abdullah.home.automation.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StationInfoDto implements Serializable {
    private List<String> stations ;
    private List<String> payloadTypes;

}
