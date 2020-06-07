package com.abdullah.home.automation.dto.meteostat;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ObjectWrapper implements Serializable {
	private Meta meta;
	private List<Data> Data;
}
