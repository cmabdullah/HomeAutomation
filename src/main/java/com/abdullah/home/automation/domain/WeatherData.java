package com.abdullah.home.automation.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData implements Serializable{

	private static final long serialVersionUID = 1L;

	String time;
	String time_local;
	double temperature ;
	double dewpoint;
	int humidity;
	int precipitation;
	int precipitation_3;
	String precipitation_6;
	String snowdepth;
	int windspeed;
	String peakgust;
	int winddirection;
	double pressure;
	int condition;
}
