package com.abdullah.home.automation.dto.meteostat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Data implements Serializable {
	
	@JsonProperty("time")
	private String time;
	@JsonProperty("time_local")
	private String timeLocal;
	@JsonProperty("temperature")
	private double temperature ;
	@JsonProperty("dewpoint")
	private double dewpoint;
	@JsonProperty("humidity")
	private int humidity;
	@JsonProperty("precipitation")
	private int precipitation;
	@JsonProperty("precipitation_3")
	private int precipitation3;
	@JsonProperty("precipitation_6")
	private String precipitation6;
	@JsonProperty("snowdepth")
	private String snowdepth;
	@JsonProperty("windspeed")
	private int windspeed;
	@JsonProperty("peakgust")
	private String peakgust;
	@JsonProperty("winddirection")
	private int winddirection;
	@JsonProperty("pressure")
	private double pressure;
	@JsonProperty("condition")
	private int condition;
}
