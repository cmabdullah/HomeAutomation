package com.abdullah.home.automation.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class FilterDate {
	private Date targetDate;
	private String namePath;
}
