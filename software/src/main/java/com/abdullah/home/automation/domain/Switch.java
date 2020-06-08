package com.abdullah.home.automation.domain;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
@Entity
public class Switch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String switchName;
    private double voltageRange;
    private int logicalSwitch;
    private int physicalSwitch;
    private int piSwitch;

    public Switch() {

    }

    public Switch(Long id) {
        this.id = id;
    }

    public Switch(String switchName, double voltageRange, int logicalSwitch , int physicalSwitch, int piSwitch){
        this.switchName = switchName;
        this.voltageRange = voltageRange;
        this.logicalSwitch = logicalSwitch;
        this.physicalSwitch = physicalSwitch;
        this.piSwitch = piSwitch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public double getVoltageRange() {
        return voltageRange;
    }

    public void setVoltageRange(double voltageRange) {
        this.voltageRange = voltageRange;
    }

    public int getLogicalSwitch() {
        return logicalSwitch;
    }

    public void setLogicalSwitch(int logicalSwitch) {
        this.logicalSwitch = logicalSwitch;
    }

    public int getPhysicalSwitch() {
        return physicalSwitch;
    }

    public void setPhysicalSwitch(int physicalSwitch) {
        this.physicalSwitch = physicalSwitch;
    }

    public int getPiSwitch() {
        return piSwitch;
    }

    public void setPiSwitch(int piSwitch) {
        this.piSwitch = piSwitch;
    }

    @Override
    public String toString() {
        return "Switch{" +
                "id=" + id +
                ", switchName='" + switchName + '\'' +
                ", voltageRange=" + voltageRange +
                ", logicalSwitch=" + logicalSwitch +
                ", physicalSwitch=" + physicalSwitch +
                ", piSwitch=" + piSwitch +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Switch)) return false;
        Switch aSwitch = (Switch) o;
        return Double.compare(aSwitch.getVoltageRange(), getVoltageRange()) == 0 &&
                getLogicalSwitch() == aSwitch.getLogicalSwitch() &&
                getPhysicalSwitch() == aSwitch.getPhysicalSwitch() &&
                getPiSwitch() == aSwitch.getPiSwitch() &&
                getId().equals(aSwitch.getId()) &&
                getSwitchName().equals(aSwitch.getSwitchName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSwitchName(), getVoltageRange(), getLogicalSwitch(), getPhysicalSwitch(), getPiSwitch());
    }
}
