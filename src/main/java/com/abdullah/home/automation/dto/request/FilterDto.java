package com.abdullah.home.automation.dto.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class FilterDto implements Serializable {

	@Temporal(TemporalType.DATE)
	private Date targetDate;
	@NotNull
	private String namePath;
	private String payloadType;
	private String payloadState;
}
