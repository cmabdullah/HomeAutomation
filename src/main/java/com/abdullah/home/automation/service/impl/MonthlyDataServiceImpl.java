package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.MonthlyData;
import com.abdullah.home.automation.domain.Station;
import com.abdullah.home.automation.repository.MonthlyDataRepository;
import com.abdullah.home.automation.service.MonthlyDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonthlyDataServiceImpl implements MonthlyDataService {

    private final MonthlyDataRepository monthlyDataRepository;

    private static final Logger log = LoggerFactory.getLogger(MonthlyDataServiceImpl.class);

    @Autowired
    public MonthlyDataServiceImpl(MonthlyDataRepository monthlyDataRepository){
        this.monthlyDataRepository = monthlyDataRepository;
    }

    @Override
    public MonthlyData save(MonthlyData monthlyData) {
        return monthlyDataRepository.save(monthlyData);
    }

    @Override
    public MonthlyData findByStationAndPayloadMonth(Station stationInfo, String filterDate) {
        return monthlyDataRepository.findByStationAndPayloadMonth(stationInfo, filterDate) ;
    }
}
