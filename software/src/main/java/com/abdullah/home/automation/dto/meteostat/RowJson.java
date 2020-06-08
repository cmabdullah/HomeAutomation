package com.abdullah.home.automation.dto.meteostat;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class RowJson implements Serializable {
	private Meta meta;
	private List<WeatherData> data;

	public RowJson() {
	}

	public RowJson(Meta meta, List<WeatherData> data) {
		this.meta = meta;
		this.data = data;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public List<WeatherData> getData() {
		return data;
	}

	public void setData(List<WeatherData> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RowJson{" +
				"meta=" + meta +
				", data=" + data +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RowJson)) return false;
		RowJson rowJson = (RowJson) o;
		return getMeta().equals(rowJson.getMeta()) &&
				getData().equals(rowJson.getData());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMeta(), getData());
	}
}
