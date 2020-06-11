package com.abdullah.home.automation.dto.meteostat;

//import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@ToString
public class ObjectWrapper implements Serializable {
	private Meta meta;
	private List<com.abdullah.home.automation.dto.meteostat.Data> Data;

	public ObjectWrapper() {

	}

	public ObjectWrapper(Meta meta, List<com.abdullah.home.automation.dto.meteostat.Data> data) {
		this.meta = meta;
		Data = data;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public List<com.abdullah.home.automation.dto.meteostat.Data> getData() {
		return Data;
	}

	public void setData(List<com.abdullah.home.automation.dto.meteostat.Data> data) {
		Data = data;
	}

	@Override
	public String toString() {
		return "ObjectWrapper{" +
				"meta=" + meta +
				", Data=" + Data +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ObjectWrapper)) return false;
		ObjectWrapper that = (ObjectWrapper) o;
		return getMeta().equals(that.getMeta()) &&
				getData().equals(that.getData());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMeta(), getData());
	}
}
