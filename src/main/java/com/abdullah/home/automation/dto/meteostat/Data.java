package com.abdullah.home.automation.dto.meteostat;

import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.*;

import java.io.Serializable;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
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

	public Data() {
	}

	public Data(String time, String timeLocal, double temperature, double dewpoint, int humidity, int precipitation, int precipitation3, String precipitation6, String snowdepth, int windspeed, String peakgust, int winddirection, double pressure, int condition) {
		this.time = time;
		this.timeLocal = timeLocal;
		this.temperature = temperature;
		this.dewpoint = dewpoint;
		this.humidity = humidity;
		this.precipitation = precipitation;
		this.precipitation3 = precipitation3;
		this.precipitation6 = precipitation6;
		this.snowdepth = snowdepth;
		this.windspeed = windspeed;
		this.peakgust = peakgust;
		this.winddirection = winddirection;
		this.pressure = pressure;
		this.condition = condition;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimeLocal() {
		return timeLocal;
	}

	public void setTimeLocal(String timeLocal) {
		this.timeLocal = timeLocal;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getDewpoint() {
		return dewpoint;
	}

	public void setDewpoint(double dewpoint) {
		this.dewpoint = dewpoint;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(int precipitation) {
		this.precipitation = precipitation;
	}

	public int getPrecipitation3() {
		return precipitation3;
	}

	public void setPrecipitation3(int precipitation3) {
		this.precipitation3 = precipitation3;
	}

	public String getPrecipitation6() {
		return precipitation6;
	}

	public void setPrecipitation6(String precipitation6) {
		this.precipitation6 = precipitation6;
	}

	public String getSnowdepth() {
		return snowdepth;
	}

	public void setSnowdepth(String snowdepth) {
		this.snowdepth = snowdepth;
	}

	public int getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(int windspeed) {
		this.windspeed = windspeed;
	}

	public String getPeakgust() {
		return peakgust;
	}

	public void setPeakgust(String peakgust) {
		this.peakgust = peakgust;
	}

	public int getWinddirection() {
		return winddirection;
	}

	public void setWinddirection(int winddirection) {
		this.winddirection = winddirection;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "Data{" +
				"time='" + time + '\'' +
				", timeLocal='" + timeLocal + '\'' +
				", temperature=" + temperature +
				", dewpoint=" + dewpoint +
				", humidity=" + humidity +
				", precipitation=" + precipitation +
				", precipitation3=" + precipitation3 +
				", precipitation6='" + precipitation6 + '\'' +
				", snowdepth='" + snowdepth + '\'' +
				", windspeed=" + windspeed +
				", peakgust='" + peakgust + '\'' +
				", winddirection=" + winddirection +
				", pressure=" + pressure +
				", condition=" + condition +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Data)) return false;
		Data data = (Data) o;
		return Double.compare(data.getTemperature(), getTemperature()) == 0 &&
				Double.compare(data.getDewpoint(), getDewpoint()) == 0 &&
				getHumidity() == data.getHumidity() &&
				getPrecipitation() == data.getPrecipitation() &&
				getPrecipitation3() == data.getPrecipitation3() &&
				getWindspeed() == data.getWindspeed() &&
				getWinddirection() == data.getWinddirection() &&
				Double.compare(data.getPressure(), getPressure()) == 0 &&
				getCondition() == data.getCondition() &&
				getTime().equals(data.getTime()) &&
				getTimeLocal().equals(data.getTimeLocal()) &&
				getPrecipitation6().equals(data.getPrecipitation6()) &&
				getSnowdepth().equals(data.getSnowdepth()) &&
				getPeakgust().equals(data.getPeakgust());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTime(), getTimeLocal(), getTemperature(), getDewpoint(), getHumidity(), getPrecipitation(), getPrecipitation3(), getPrecipitation6(), getSnowdepth(), getWindspeed(), getPeakgust(), getWinddirection(), getPressure(), getCondition());
	}
}
