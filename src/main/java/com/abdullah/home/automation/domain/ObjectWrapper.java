package com.abdullah.home.automation.domain;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ObjectWrapper {
	Meta meta;
	List<Data> Data;
}
