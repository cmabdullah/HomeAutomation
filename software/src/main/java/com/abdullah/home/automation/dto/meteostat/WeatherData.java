package com.abdullah.home.automation.dto.meteostat;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData implements Serializable{

	private static final long serialVersionUID = 1L;

	private String time;
	private String time_local;
	private double temperature ;
	private double dewpoint;
	private int humidity;
	private int precipitation;
	private int precipitation_3;
	private String precipitation_6;
	private String snowdepth;
	private int windspeed;
	private String peakgust;
	private int winddirection;
	private double pressure;
	private int condition;
}
