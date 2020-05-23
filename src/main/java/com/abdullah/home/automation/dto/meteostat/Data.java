package com.abdullah.home.automation.dto.meteostat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Data {
	
	@JsonProperty("time")
	String time;
	@JsonProperty("time_local")
	String timeLocal;
	@JsonProperty("temperature")
	double temperature ;
	@JsonProperty("dewpoint")
	double dewpoint;
	@JsonProperty("humidity")
	int humidity;
	@JsonProperty("precipitation")
	int precipitation;
	@JsonProperty("precipitation_3")
	int precipitation3;
	@JsonProperty("precipitation_6")
	String precipitation6;
	@JsonProperty("snowdepth")
	String snowdepth;
	@JsonProperty("windspeed")
	int windspeed;
	@JsonProperty("peakgust")
	String peakgust;
	@JsonProperty("winddirection")
	int winddirection;
	@JsonProperty("pressure")
	double pressure;
	@JsonProperty("condition")
	int condition;
}
