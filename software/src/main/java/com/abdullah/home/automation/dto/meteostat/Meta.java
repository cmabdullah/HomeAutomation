package com.abdullah.home.automation.dto.meteostat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Meta implements Serializable {
	private String source;
}
