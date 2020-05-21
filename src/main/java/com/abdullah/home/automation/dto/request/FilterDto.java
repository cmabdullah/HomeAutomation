package com.abdullah.home.automation.dto.request;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class FilterDto {

	@Temporal(TemporalType.DATE)
	private Date targetDate;
	@NotNull
	private String namePath;
	private String payloadType;
}
