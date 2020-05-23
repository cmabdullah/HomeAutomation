package com.abdullah.home.automation.dto.meteostat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RowJson {
	Meta meta;
	List<WeatherData> data;
}
