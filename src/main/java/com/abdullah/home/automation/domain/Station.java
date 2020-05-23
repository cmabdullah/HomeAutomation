package com.abdullah.home.automation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationId;
    private String country;
    private String stationName;
    private String number1;
    private String number2;
    private String quantity;
    private String stationKey;
    private String stationId2;
    private String key2;
    private String state;

    public Station(String stationId, String country, String stationName,
                   String number1, String number2, String quantity, String stationKey,
                   String stationId2, String key2, String state ) {

        this.stationId = stationId;
        this.country = country;
        this.stationName = stationName;
        this.number1 = number1;
        this.number2 = number2;
        this.quantity = quantity;
        this.stationKey = stationKey;
        this.stationId2 = stationId2;
        this.key2 = key2;
        this.state = state;
    }
}

