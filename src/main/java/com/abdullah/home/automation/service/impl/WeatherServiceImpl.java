package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.MonthlyData;
import com.abdullah.home.automation.domain.Payload;
import com.abdullah.home.automation.domain.Station;
import com.abdullah.home.automation.domain.WeatherEntity;
import com.abdullah.home.automation.dto.meteostat.Data;
import com.abdullah.home.automation.dto.meteostat.ObjectWrapper;
import com.abdullah.home.automation.dto.meteostat.RowJson;
import com.abdullah.home.automation.dto.meteostat.WeatherData;
import com.abdullah.home.automation.dto.request.FilterDto;
import com.abdullah.home.automation.dto.response.Payload2;
import com.abdullah.home.automation.dto.response.WeatherResponseDto;
import com.abdullah.home.automation.exception.ApiError;
import com.abdullah.home.automation.exception.ApiMessage;
import com.abdullah.home.automation.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {


    private final PayloadService payloadService;

    private final StationService stationService;

    private final MonthlyDataService monthlyDataService;

    private final WeatherEntityService weatherEntityService;

    @Autowired
    WeatherServiceImpl(PayloadService payloadService, StationService stationService, MonthlyDataService monthlyDataService,
                       WeatherEntityService weatherEntityService) {
        this.payloadService = payloadService;
        this.stationService = stationService;
        this.monthlyDataService = monthlyDataService;
        this.weatherEntityService = weatherEntityService;
    }

    private static String basePath = System.getProperty("user.dir");
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public WeatherResponseDto postWeatherRequest() {//for this month
        List<WeatherEntity> weatherEntityList = weatherEntityService.findAll();
        LocalDate targetDate = dateValidation(LocalDate.now());
        LocalDate firstDayOfMonth = targetDate.with(firstDayOfMonth());
        log.info("targetDate " + targetDate + " firstDayOfMonth " + firstDayOfMonth);
        String payloadType = weatherEntityList.get(0).getEntityName();
        FilterDto filterDto = new FilterDto();
        filterDto.setNamePath("41923\tBD\tDhaka\tAsia/Dhaka");
        return buildWeatherResponseDto(filterDto, targetDate, firstDayOfMonth, payloadType);
    }

    @Override
    public WeatherResponseDto postWeatherRequest(FilterDto filterDto) {//for this month
        List<WeatherEntity> weatherEntityList = weatherEntityService.findAll();
        LocalDate targetDate = dateValidation(
                filterDto.getTargetDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());

        LocalDate firstDayOfMonth = targetDate.with(firstDayOfMonth());
        log.info("targetDate " + targetDate + " firstDayOfMonth " + firstDayOfMonth);
        String payloadType = "";

        if (filterDto.getPayloadType() != null) {
            if (weatherEntityList.get(0).getEntityName().contains(filterDto.getPayloadType())) {
                payloadType = filterDto.getPayloadType();
            } else {
                payloadType = weatherEntityList.get(0).getEntityName();
            }
        }
        return buildWeatherResponseDto(filterDto, targetDate, firstDayOfMonth, payloadType);

    }

    @Override
    public WeatherResponseDto processStaticData(FilterDto filterDto) {
        List<WeatherEntity> weatherEntityList = weatherEntityService.findAll();
        LocalDate targetDate = dateValidation(
                filterDto.getTargetDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());

        LocalDate firstDayOfMonth = targetDate.with(firstDayOfMonth());
        log.info("targetDate " + targetDate + " firstDayOfMonth " + firstDayOfMonth);
        String payloadType = weatherEntityList.get(0).getEntityName();

        return buildWeatherResponseDto(filterDto, targetDate, firstDayOfMonth, payloadType);
    }

    private WeatherResponseDto buildWeatherResponseDto(FilterDto filterDto, LocalDate targetDate, LocalDate firstDayOfMonth, String payloadType) {

        List<Payload2> payload2List;

        List<String> strList = List.of(filterDto.getNamePath().split("\\s+"));
        String station = strList.get(0);
        String timeZone = strList.get(strList.size() - 1);
        String filterDate = targetDate.toString().substring(0, 7);
        if (strList.size() > 1) {
            payload2List = postWeatherRequest(filterDto, targetDate, firstDayOfMonth, payloadType, station, timeZone, filterDate);
        } else {
            payload2List = processStaticData(filterDto, targetDate, firstDayOfMonth, payloadType);
        }

        log.info("payload size " + payload2List.size());
        // 2019-04-
        String filterPartialDate = targetDate.toString().substring(0, 7);
        WeatherResponseDto weatherResponseDto = new WeatherResponseDto();
        weatherResponseDto.setFiltarDate(filterPartialDate);
        weatherResponseDto.setPayloadType(payloadType);
        weatherResponseDto.setPayload2List(payload2List);

        return weatherResponseDto;
    }

    //@Override
    public List<Payload2> postWeatherRequest(FilterDto filterDto, LocalDate targetDate, LocalDate firstDayOfMonth,
                                             String payloadType ,String station,String  timeZone,String  filterDate) {

        Station stationInfo = stationService.findByStationId(station)
                .orElseThrow(ApiError.createSingletonSupplier(ApiMessage.STATION_NOT_FOUND, HttpStatus.NOT_FOUND));

        final MonthlyData monthlyData = MonthlyData.builder().station(stationInfo).payloadMonth(filterDate).build();
        final MonthlyData monthlyDataFromDb = monthlyDataService.findByStationAndPayloadMonth(stationInfo, filterDate);

        final WeatherEntity weatherEntity = weatherEntityService.findByEntityName(payloadType)
                .orElseThrow(ApiError.createSingletonSupplier(ApiMessage.WEATHER_ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND));

        List<Payload> payloads = payloadService.findByMonthlyDataAndWeatherEntity(monthlyDataFromDb, weatherEntity);
        log.info("Payload size got from db : "+payloads.size());

        if (payloads.size() == 24) {
            return convertPayloadToPayload2(payloads);
        }

        String quoteUrl = "https://api.meteostat.net/v1/history/hourly?station=" + station + "&start="
                + firstDayOfMonth + "&end=" + targetDate + "&time_zone=" + timeZone + "&time_format=Y-m-d%20H:i&key=qcca1JKR";
        //new key ySq5y6RG

        //return dataset [api response]
        List<WeatherData> list = getData(quoteUrl, stationInfo);

        final MonthlyData monthlyDataFromDbCpy = monthlyDataFromDb != null ? monthlyDataFromDb : monthlyDataService.save(monthlyData);

        return convertWeatherDataToPayload(list, targetDate, payloadType, weatherEntity,  monthlyDataFromDbCpy, payloads.size(), filterDto, firstDayOfMonth);
    }

    List<Payload2> convertWeatherDataToPayload(List<WeatherData> list, LocalDate targetDate, String payloadType,
                                               WeatherEntity weatherEntity, MonthlyData monthlyDataFromDbCpy, int payloadSize, FilterDto filterDto, LocalDate firstDayOfMonth) {

        List<Payload2> payload2ListData  = convertWeatherDataToPayload(list, targetDate, payloadType);

        boolean ifFirstDayOfRunningMonthFalse = firstDayOfRunningMonth(firstDayOfMonth);

        if (ifFirstDayOfRunningMonthFalse && filterDto.getPayloadState().equals("save")){
            //add data gurd first
            saveWeatherData(payload2ListData, weatherEntity, monthlyDataFromDbCpy, payloadSize);
        }

        return payload2ListData;
    }



    private void saveWeatherData(List<Payload2> payload2List, WeatherEntity weatherEntity,
                                 MonthlyData monthlyDataFromDbCpy, int payloadSize) {

        final List<Payload> payloadList = payload2List.stream().filter(Objects::nonNull)
                .map(n -> new Payload(n.getHourName(),
                        n.getD1(), n.getD2(), n.getD3(), n.getD4(), n.getD5(), n.getD6(), n.getD7(),
                        n.getD8(), n.getD9(), n.getD10(), n.getD11(), n.getD12(), n.getD13(), n.getD14(), n.getD15(),
                        n.getD16(), n.getD17(), n.getD18(), n.getD19(), n.getD20(), n.getD21(), n.getD22(),
                        n.getD23(), n.getD24(), n.getD25(), n.getD26(), n.getD27(), n.getD28(), n.getD29(), n.getD30(),
                        n.getD31(), monthlyDataFromDbCpy, weatherEntity)).collect(Collectors.toUnmodifiableList());

        if (payloadSize == 0){
            payloadService.saveAll(payloadList);
        }
    }



    private List<Payload2> convertPayloadToPayload2(List<Payload> payloads) {

        return payloads.stream().filter(Objects::nonNull).map(n -> new Payload2(n.getHourName(),n.getD1(), n.getD2(),
                n.getD3(), n.getD4(), n.getD5(), n.getD6(), n.getD7(), n.getD8(), n.getD9(), n.getD10(), n.getD11(), n.getD12(),
                n.getD13(), n.getD14(), n.getD15(), n.getD16(), n.getD17(), n.getD18(), n.getD19(), n.getD20(), n.getD21(), n.getD22(),
                n.getD23(), n.getD24(), n.getD25(), n.getD26(), n.getD27(), n.getD28(), n.getD29(), n.getD30(), n.getD31()))
                .collect(Collectors.toUnmodifiableList());
    }

    //@Override
    public List<Payload2> processStaticData(FilterDto filterDto, LocalDate targetDate, LocalDate firstDayOfMonth, String payloadType) {

        List<WeatherData> list = getStaticData(filterDto);
        return convertWeatherDataToPayload(list, targetDate, payloadType);
    }


    List<Payload2> convertWeatherDataToPayload(List<WeatherData> list, LocalDate targetDate, String payloadType) {

        // 2019-04-
        String filterDate = targetDate.toString().substring(0, 7);
        List<WeatherData> weatherDataOfThisMonth = list.stream().filter(n -> n.getTime().startsWith(filterDate)).collect(Collectors.toList());
        log.info(String.valueOf(weatherDataOfThisMonth.size()));

        List<Payload2> payload2ListData = payloadService.buildPayloadFromList(weatherDataOfThisMonth, payloadType);

        return payload2ListData;
    }

    @Override
    public List<String> pathList() {

        try (Stream<Path> walk = Files.walk(Paths.get(basePath + "/DataSet/"))) {

            return walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".json")).collect(Collectors.toList());
            //result1.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<WeatherData> getStaticData(FilterDto filterDto) {
        String filePath = filterDto.getNamePath();
        try {
            RowJson rowJson = mapper.readValue(new File(filePath), RowJson.class);
            return rowJson.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<WeatherData> getData(String quoteUrl, Station stationInfo) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(quoteUrl)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            log.info(responseBody);
            ObjectMapper mapper = new ObjectMapper();
            ObjectWrapper staff = mapper.readValue(responseBody, ObjectWrapper.class);
            log.info("staff 1: " + staff.toString());
            List<Data> data = staff.getData();

            List<WeatherData> weatherDataList = data.stream().filter(Objects::nonNull).map(n -> new WeatherData(n.getTime(),
                    n.getTimeLocal(), n.getTemperature(), n.getDewpoint(), n.getHumidity(),
                    n.getPrecipitation(), n.getPrecipitation3(), n.getPrecipitation6(), n.getSnowdepth(),
                    n.getWindspeed(), n.getPeakgust(), n.getWinddirection(),
                    n.getPressure(), n.getCondition())).collect(Collectors.toList());
            log.info("weatherDataList 1: " + weatherDataList.size());
            return weatherDataList;
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        return new ArrayList<>();
    }

    private boolean firstDayOfRunningMonth(LocalDate firstDayOfMonth) {
        LocalDate firstDayOfMonthRunningMonth = LocalDate.now().with(firstDayOfMonth());

        if (firstDayOfMonthRunningMonth.toString().equals(firstDayOfMonth.toString())){
            return false;
        }else {
            return true;
        }
    }


    private LocalDate dateValidation(LocalDate targetDateFromDto) {

        LocalDate today = LocalDate.now();
        LocalDate targetDate = targetDateFromDto;
        boolean dateValidation = today.isAfter(targetDateFromDto);//today is after targetDate
        if (!dateValidation) { //if input date greater then today
            targetDate = LocalDate.now();
        }

        if (dateValidation) {
            LocalDate end = targetDate.with(lastDayOfMonth());
            boolean todayIsAfterEnd = today.isAfter(end);//today is after end
            if (todayIsAfterEnd) {
                targetDate = targetDate.with(lastDayOfMonth());
            }
        }

        return targetDate;

    }
}
