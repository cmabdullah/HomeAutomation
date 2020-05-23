package com.abdullah.home.automation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive = true;

    @OneToOne(cascade = CascadeType.ALL)
    private Station station;

    public Favorite( Station station, boolean isActive) {
        this.station = station;
        this.isActive = isActive;
    }
}