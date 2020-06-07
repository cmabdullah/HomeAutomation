package com.abdullah.home.automation.dto.meteostat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RowJson implements Serializable {
	private Meta meta;
	private List<WeatherData> data;
}
