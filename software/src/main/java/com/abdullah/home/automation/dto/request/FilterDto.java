package com.abdullah.home.automation.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//import lombok.Data;
//import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

//@NoArgsConstructor
//@Data
public class FilterDto implements Serializable {

	@Temporal(TemporalType.DATE)
	private Date targetDate;
	@NotNull
	private String namePath;
	private String payloadType;
	private String payloadState;

	public FilterDto() {
	}

	public FilterDto(Date targetDate, @NotNull String namePath, String payloadType, String payloadState) {
		this.targetDate = targetDate;
		this.namePath = namePath;
		this.payloadType = payloadType;
		this.payloadState = payloadState;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	public String getPayloadType() {
		return payloadType;
	}

	public void setPayloadType(String payloadType) {
		this.payloadType = payloadType;
	}

	public String getPayloadState() {
		return payloadState;
	}

	public void setPayloadState(String payloadState) {
		this.payloadState = payloadState;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FilterDto)) return false;
		FilterDto filterDto = (FilterDto) o;
		return Objects.equals(getTargetDate(), filterDto.getTargetDate()) &&
				Objects.equals(getNamePath(), filterDto.getNamePath()) &&
				Objects.equals(getPayloadType(), filterDto.getPayloadType()) &&
				Objects.equals(getPayloadState(), filterDto.getPayloadState());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTargetDate(), getNamePath(), getPayloadType(), getPayloadState());
	}

	@Override
	public String toString() {
		return "FilterDto{" +
				"targetDate=" + targetDate +
				", namePath='" + namePath + '\'' +
				", payloadType='" + payloadType + '\'' +
				", payloadState='" + payloadState + '\'' +
				'}';
	}
}
