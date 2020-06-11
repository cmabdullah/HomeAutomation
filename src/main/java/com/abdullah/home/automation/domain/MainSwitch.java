package com.abdullah.home.automation.domain;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
@Entity
public class MainSwitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean dataMigrationSwitch;
    private boolean pinModeLogicalSwitch;
    private boolean pinModePhysicalSwitch;
    private boolean pinModeLogicalPhysicalSwitch;

    public MainSwitch() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDataMigrationSwitch() {
        return dataMigrationSwitch;
    }

    public void setDataMigrationSwitch(boolean dataMigrationSwitch) {
        this.dataMigrationSwitch = dataMigrationSwitch;
    }

    public boolean isPinModeLogicalSwitch() {
        return pinModeLogicalSwitch;
    }

    public void setPinModeLogicalSwitch(boolean pinModeLogicalSwitch) {
        this.pinModeLogicalSwitch = pinModeLogicalSwitch;
    }

    public boolean isPinModePhysicalSwitch() {
        return pinModePhysicalSwitch;
    }

    public void setPinModePhysicalSwitch(boolean pinModePhysicalSwitch) {
        this.pinModePhysicalSwitch = pinModePhysicalSwitch;
    }

    public boolean isPinModeLogicalPhysicalSwitch() {
        return pinModeLogicalPhysicalSwitch;
    }

    public void setPinModeLogicalPhysicalSwitch(boolean pinModeLogicalPhysicalSwitch) {
        this.pinModeLogicalPhysicalSwitch = pinModeLogicalPhysicalSwitch;
    }

    @Override
    public String toString() {
        return "MainSwitch{" +
                "id=" + id +
                ", dataMigrationSwitch=" + dataMigrationSwitch +
                ", pinModeLogicalSwitch=" + pinModeLogicalSwitch +
                ", pinModePhysicalSwitch=" + pinModePhysicalSwitch +
                ", pinModeLogicalPhysicalSwitch=" + pinModeLogicalPhysicalSwitch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MainSwitch)) return false;
        MainSwitch that = (MainSwitch) o;
        return isDataMigrationSwitch() == that.isDataMigrationSwitch() &&
                isPinModeLogicalSwitch() == that.isPinModeLogicalSwitch() &&
                isPinModePhysicalSwitch() == that.isPinModePhysicalSwitch() &&
                isPinModeLogicalPhysicalSwitch() == that.isPinModeLogicalPhysicalSwitch() &&
                getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isDataMigrationSwitch(), isPinModeLogicalSwitch(), isPinModePhysicalSwitch(), isPinModeLogicalPhysicalSwitch());
    }
}
