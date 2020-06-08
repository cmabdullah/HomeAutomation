package com.abdullah.home.automation.dto.meteostat;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class Meta implements Serializable {
	private String source;

	public Meta() {

	}

	public Meta(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Meta{" +
				"source='" + source + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Meta)) return false;
		Meta meta = (Meta) o;
		return getSource().equals(meta.getSource());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSource());
	}
}
