package com.abdullah.home.automation.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.abdullah.home.automation.domain.Payload2;
import com.abdullah.home.automation.service.WeatherService;
import com.abdullah.home.automation.domain.FilterDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Slf4j
@Controller
public class RemoteController {

    private final WeatherService weatherService;

    @Autowired
    RemoteController( WeatherService weatherService){
        this.weatherService = weatherService;
    }

    List<String> payloadTypes = List.of("humidity", "temperature", "pressure", "winddirection", "windspeed", "precipitation", "dewpoint");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    String weatherGet(ModelMap modelMap) {

        LocalDate targetDate = dateValidation(LocalDate.now());
        LocalDate firstDayOfMonth = targetDate.with(firstDayOfMonth());
        log.info("targetDate " + targetDate + " firstDayOfMonth " + firstDayOfMonth);

        String payloadType = payloadTypes.get(0);
        FilterDate filterDate = new FilterDate();
        filterDate.setNamePath("41923\tBD\tDhaka\tAsia/Dhaka");

        List<Payload2> payload2List = weatherService.postWeatherRequest(filterDate, targetDate, firstDayOfMonth, payloadType);
        log.info("payload size "+ payload2List.size());
        // 2019-04-
        String filterPartialDate = targetDate.toString().substring(0, 7);
        modelMap.addAttribute("filtarDate", filterPartialDate);
        modelMap.addAttribute("payloadType", payloadType);
        modelMap.addAttribute("payload2list", payload2List);

        return "dataViz";
    }

    @RequestMapping(value = "/remote", method = RequestMethod.GET)
    String remoteGet(ModelMap modelMap) {
        modelMap.addAttribute("filterDate", new FilterDate());
        List<String> stations = List.of("41923\tBD\tDhaka\tAsia/Dhaka", "47662\tJP\tTokyo\tAsia/Tokyo");
        modelMap.addAttribute("stations", stations);

        return "remote";
    }

    //ModelMap modelMap, @Valid FilterDate filterDate, BindingResult result
    @RequestMapping(value = "/remote", method = RequestMethod.POST)
    String remotePost(ModelMap modelMap, @Valid FilterDate filterDate, BindingResult result) {
        log.info("Ui Data : " + filterDate.toString());

        LocalDate targetDate = dateValidation(
                filterDate.getTargetDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());

        LocalDate firstDayOfMonth = targetDate.with(firstDayOfMonth());

        log.info("targetDate " + targetDate + " firstDayOfMonth " + firstDayOfMonth);

        String payloadType = payloadTypes.get(0);
        List<Payload2> payload2List = weatherService.postWeatherRequest(filterDate, targetDate, firstDayOfMonth, payloadType);
        log.info("payload size "+ payload2List.size());
        // 2019-04-
        String filtarDate = targetDate.toString().substring(0, 7);
        modelMap.addAttribute("filtarDate", filtarDate);
        modelMap.addAttribute("payloadType", payloadType);
        modelMap.addAttribute("payload2list", payload2List);

        return "dataViz";
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

    @RequestMapping("/staticFind")
        //@ResponseBody
    String acceptDate(ModelMap modelMap) {
        modelMap.addAttribute("filterDate", new FilterDate());
        modelMap.addAttribute("pathList", weatherService.pathList());
        return "staticFind";
    }

    @RequestMapping(value = "/staticFind", method = RequestMethod.POST)
    String hello(ModelMap modelMap, @Valid FilterDate filterDate, BindingResult result) {

        LocalDate targetDate = dateValidation(
                filterDate.getTargetDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());

        LocalDate firstDayOfMonth = targetDate.with(firstDayOfMonth());

        log.info("targetDate " + targetDate + " firstDayOfMonth " + firstDayOfMonth);

        String payloadType = payloadTypes.get(0);

        List<Payload2> payload2List = weatherService.processStaticData(filterDate, targetDate, firstDayOfMonth, payloadType);

        log.info("payload size "+ payload2List.size());
        // 2019-04-
        String filtarDate = targetDate.toString().substring(0, 7);
        modelMap.addAttribute("filtarDate", filtarDate);
        modelMap.addAttribute("payloadType", payloadType);
        modelMap.addAttribute("payload2list", payload2List);

        return "dataViz";
    }
}
