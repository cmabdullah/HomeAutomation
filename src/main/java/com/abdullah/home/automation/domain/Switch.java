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
public class Switch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String switchName;
    private double voltageRange;
    private int logicalSwitch;
    private int physicalSwitch;
    private int piSwitch;

    public Switch(String switchName, double voltageRange, int logicalSwitch , int physicalSwitch, int piSwitch){
        this.switchName = switchName;
        this.voltageRange = voltageRange;
        this.logicalSwitch = logicalSwitch;
        this.physicalSwitch = physicalSwitch;
        this.piSwitch = piSwitch;
    }

}
