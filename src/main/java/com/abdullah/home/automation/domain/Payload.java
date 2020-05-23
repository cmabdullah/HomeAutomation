package com.abdullah.home.automation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Payload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String hourName;
    double d1;
    double d2;
    double d3;
    double d4;
    double d5;
    double d6;
    double d7;
    double d8;
    double d9;
    double d10;
    double d11;
    double d12;
    double d13;
    double d14;
    double d15;
    double d16;
    double d17;
    double d18;
    double d19;
    double d20;
    double d21;
    double d22;
    double d23;
    double d24;
    double d25;
    double d26;
    double d27;
    double d28;
    double d29;
    double d30;
    double d31;

    @ManyToOne
    @JoinColumn(name="monthlyDataId", nullable=false)
    private MonthlyData monthlyData;

    @ManyToOne
    @JoinColumn(name="weatherEntityId", nullable=false)
    private WeatherEntity weatherEntity;

    public Payload(String hourName, double d1, double d2, double d3, double d4, double d5, double d6, double d7, double d8,
                   double d9, double d10, double d11, double d12, double d13, double d14, double d15, double d16, double d17,
                   double d18, double d19, double d20, double d21, double d22, double d23, double d24, double d25, double d26,
                   double d27, double d28, double d29, double d30, double d31, MonthlyData monthlyData, WeatherEntity weatherEntity) {
        this.hourName = hourName;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.d4 = d4;
        this.d5 = d5;
        this.d6 = d6;
        this.d7 = d7;
        this.d8 = d8;
        this.d9 = d9;
        this.d10 = d10;
        this.d11 = d11;
        this.d12 = d12;
        this.d13 = d13;
        this.d14 = d14;
        this.d15 = d15;
        this.d16 = d16;
        this.d17 = d17;
        this.d18 = d18;
        this.d19 = d19;
        this.d20 = d20;
        this.d21 = d21;
        this.d22 = d22;
        this.d23 = d23;
        this.d24 = d24;
        this.d25 = d25;
        this.d26 = d26;
        this.d27 = d27;
        this.d28 = d28;
        this.d29 = d29;
        this.d30 = d30;
        this.d31 = d31;
        this.monthlyData = monthlyData;
        this.weatherEntity = weatherEntity;
    }
}
