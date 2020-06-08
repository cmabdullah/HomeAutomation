package com.abdullah.home.automation.dto.meteostat;

import java.io.Serializable;
import java.util.Objects;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
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

	public WeatherData() {

	}

	public WeatherData(String time, String time_local, double temperature, double dewpoint, int humidity, int precipitation, int precipitation_3, String precipitation_6, String snowdepth, int windspeed, String peakgust, int winddirection, double pressure, int condition) {
		this.time = time;
		this.time_local = time_local;
		this.temperature = temperature;
		this.dewpoint = dewpoint;
		this.humidity = humidity;
		this.precipitation = precipitation;
		this.precipitation_3 = precipitation_3;
		this.precipitation_6 = precipitation_6;
		this.snowdepth = snowdepth;
		this.windspeed = windspeed;
		this.peakgust = peakgust;
		this.winddirection = winddirection;
		this.pressure = pressure;
		this.condition = condition;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime_local() {
		return time_local;
	}

	public void setTime_local(String time_local) {
		this.time_local = time_local;
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

	public int getPrecipitation_3() {
		return precipitation_3;
	}

	public void setPrecipitation_3(int precipitation_3) {
		this.precipitation_3 = precipitation_3;
	}

	public String getPrecipitation_6() {
		return precipitation_6;
	}

	public void setPrecipitation_6(String precipitation_6) {
		this.precipitation_6 = precipitation_6;
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
		return "WeatherData{" +
				"time='" + time + '\'' +
				", time_local='" + time_local + '\'' +
				", temperature=" + temperature +
				", dewpoint=" + dewpoint +
				", humidity=" + humidity +
				", precipitation=" + precipitation +
				", precipitation_3=" + precipitation_3 +
				", precipitation_6='" + precipitation_6 + '\'' +
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
		if (!(o instanceof WeatherData)) return false;
		WeatherData that = (WeatherData) o;
		return Double.compare(that.getTemperature(), getTemperature()) == 0 &&
				Double.compare(that.getDewpoint(), getDewpoint()) == 0 &&
				getHumidity() == that.getHumidity() &&
				getPrecipitation() == that.getPrecipitation() &&
				getPrecipitation_3() == that.getPrecipitation_3() &&
				getWindspeed() == that.getWindspeed() &&
				getWinddirection() == that.getWinddirection() &&
				Double.compare(that.getPressure(), getPressure()) == 0 &&
				getCondition() == that.getCondition() &&
				getTime().equals(that.getTime()) &&
				getTime_local().equals(that.getTime_local()) &&
				getPrecipitation_6().equals(that.getPrecipitation_6()) &&
				getSnowdepth().equals(that.getSnowdepth()) &&
				getPeakgust().equals(that.getPeakgust());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTime(), getTime_local(), getTemperature(), getDewpoint(), getHumidity(), getPrecipitation(), getPrecipitation_3(), getPrecipitation_6(), getSnowdepth(), getWindspeed(), getPeakgust(), getWinddirection(), getPressure(), getCondition());
	}
}
