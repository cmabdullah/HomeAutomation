package com.abdullah.home.automation.service;

import com.abdullah.home.automation.domain.Stations;

import java.io.IOException;
import java.util.List;

public interface StationService {

    List<Stations> stationList()throws IOException;
}
