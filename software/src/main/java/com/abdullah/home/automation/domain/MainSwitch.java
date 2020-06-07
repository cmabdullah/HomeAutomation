package com.abdullah.home.automation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class MainSwitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean dataMigrationSwitch;
    private boolean pinModeLogicalSwitch;
    private boolean pinModePhysicalSwitch;
    private boolean pinModeLogicalPhysicalSwitch;
}
