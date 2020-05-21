package com.abdullah.home.automation.service.impl;

import com.abdullah.home.automation.domain.*;
import com.abdullah.home.automation.dto.request.FilterDto;
import com.abdullah.home.automation.dto.response.WeatherResponseDto;
import com.abdullah.home.automation.service.PayloadService;
import com.abdullah.home.automation.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {


    private final PayloadService payloadService;

    public final List<String> payloadTypes = List.of("humidity", "temperature", "pressure", "winddirection", "windspeed", "precipitation", "dewpoint");


    @Autowired
    WeatherServiceImpl(PayloadService payloadService){
        this.payloadService = payloadService;
    }

    static String basePath = System.getProperty("user.dir") ;
    static ObjectMapper mapper = new ObjectMapper();

    @Override
    public WeatherResponseDto postWeatherRequest(){//for this month
        LocalDate targetDate = dateValidation(LocalDate.now());
        LocalDate firstDayOfMonth = targetDate.with(firstDayOfMonth());
        log.info("targetDate " + targetDate + " firstDayOfMonth " + firstDayOfMonth);
        String payloadType = payloadTypes.get(0);
        FilterDto filterDto = new FilterDto();
        filterDto.setNamePath("41923\tBD\tDhaka\tAsia/Dhaka");

        return buildWeatherResponseDto(filterDto, targetDate, firstDayOfMonth, payloadType);
    }

    @Override
    public WeatherResponseDto postWeatherRequest(FilterDto filterDto){//for this month
        LocalDate targetDate = dateValidation(
                filterDto.getTargetDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());

        LocalDate firstDayOfMonth = targetDate.with(firstDayOfMonth());
        log.info("targetDate " + targetDate + " firstDayOfMonth " + firstDayOfMonth);
        String payloadType = "";

        if (filterDto.getPayloadType() !=null){
            if (payloadTypes.contains(filterDto.getPayloadType())){
                payloadType = filterDto.getPayloadType();
            }else{
                payloadType = payloadTypes.get(0);
            }
        }
        return buildWeatherResponseDto(filterDto, targetDate, firstDayOfMonth, payloadType);

    }

    @Override
    public WeatherResponseDto processStaticData(FilterDto filterDto){
        LocalDate targetDate = dateValidation(
                filterDto.getTargetDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());

        LocalDate firstDayOfMonth = targetDate.with(firstDayOfMonth());
        log.info("targetDate " + targetDate + " firstDayOfMonth " + firstDayOfMonth);
        String payloadType = payloadTypes.get(0);

        return buildWeatherResponseDto(filterDto, targetDate, firstDayOfMonth, payloadType);

    }




    private WeatherResponseDto buildWeatherResponseDto(FilterDto filterDto, LocalDate targetDate, LocalDate firstDayOfMonth, String payloadType){

        List<Payload2> payload2List;

        List<String> strList = List.of(filterDto.getNamePath().split("\\s+"));
        if (strList.size() > 1){
            payload2List = postWeatherRequest(filterDto, targetDate, firstDayOfMonth, payloadType);
        }else {
            payload2List = processStaticData(filterDto, targetDate, firstDayOfMonth, payloadType);
        }

        log.info("payload size "+ payload2List.size());
        // 2019-04-
        String filterPartialDate = targetDate.toString().substring(0, 7);
        WeatherResponseDto weatherResponseDto = new WeatherResponseDto();
        weatherResponseDto.setFiltarDate(filterPartialDate);
        weatherResponseDto.setPayloadType(payloadType);
        weatherResponseDto.setPayload2List(payload2List);

        return weatherResponseDto;
    }

    @Override
    public List<Payload2>  postWeatherRequest(FilterDto filterDto, LocalDate targetDate, LocalDate firstDayOfMonth, String payloadType) {


        List<String> strList = List.of(filterDto.getNamePath().split("\\s+"));
        String station = strList.get(0);
        String timeZone = strList.get(strList.size()-1);

        String quoteUrl = "https://api.meteostat.net/v1/history/hourly?station=" + station + "&start="
                + firstDayOfMonth + "&end=" + targetDate + "&time_zone="+timeZone+ "&time_format=Y-m-d%20H:i&key=qcca1JKR";
        //new key ySq5y6RG

        //return dataset [api response]
        List<Data> data = getData(quoteUrl);

        List<WeatherData> list = data.stream().map(n -> new WeatherData(n.getTime(), n.getTimeLocal(), n.getTemperature(), n.getDewpoint(), n.getHumidity(),
                n.getPrecipitation(), n.getPrecipitation3(), n.getPrecipitation6(), n.getSnowdepth(),
                n.getWindspeed(), n.getPeakgust(), n.getWinddirection(), n.getPressure(), n.getCondition())).collect(Collectors.toList());

        // 2019-04-
        String filtarDate = targetDate.toString().substring(0, 7);
        List<WeatherData> weatherDataOfThisMonth = list.stream().filter(n -> n.getTime().startsWith(filtarDate)).collect(Collectors.toList());

        log.info(String.valueOf(weatherDataOfThisMonth.size()));

        List<Payload2> payload2ListData = payloadService.buildPayloadFromList(weatherDataOfThisMonth, payloadType);

        //payload2ListData.stream().forEach(n -> System.out.println(n.toString()));

        return payload2ListData;
    }

    @Override
    public List<Payload2> processStaticData(FilterDto filterDto, LocalDate targetDate, LocalDate firstDayOfMonth, String payloadType) {

        RowJson rowJson =  getStaticData(filterDto);
        List<WeatherData> list = rowJson.getData();

        // 2019-04-
        String filtarDate = targetDate.toString().substring(0, 7);

        List<WeatherData> weatherDataOfThisMonth = list.stream().filter(n -> n.getTime().startsWith(filtarDate)).collect(Collectors.toList());

        log.info(String.valueOf(weatherDataOfThisMonth.size()));

        List<Payload2> payload2ListData = payloadService.buildPayloadFromList( weatherDataOfThisMonth, payloadType);

        //payload2listData.stream().forEach(n -> log.info(n.toString()));

        return payload2ListData;
    }

    @Override
    public List<String> pathList() {

        try (Stream<Path> walk = Files.walk(Paths.get(basePath+ "/DataSet/"))) {

            return walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".json")).collect(Collectors.toList());
            //result1.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private RowJson  getStaticData(FilterDto filterDto){
       String filePath = filterDto.getNamePath();
       try {
           return mapper.readValue(new File(filePath), RowJson.class);
       } catch (IOException e) {
           e.printStackTrace();
       }
       return new RowJson();
   }

    private List<Data> getData(String quoteUrl) {
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(quoteUrl)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            log.info(responseBody);
            ObjectMapper mapper = new ObjectMapper();
            ObjectWrapper staff = mapper.readValue(responseBody, ObjectWrapper.class);
            log.info("staff 1: " + staff.toString());
            return staff.getData();
        }
        catch (IOException e1 ){
            e1.printStackTrace();
        }

        catch(InterruptedException e2){
            e2.printStackTrace();
        }
        return new ArrayList<>();
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
