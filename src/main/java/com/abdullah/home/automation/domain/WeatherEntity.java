package com.abdullah.home.automation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String entityName;

    @OneToMany(mappedBy="weatherEntity")
    private List<Payload> payloadList;

    public WeatherEntity(String entityName){
        this.entityName = entityName;
    }

}
