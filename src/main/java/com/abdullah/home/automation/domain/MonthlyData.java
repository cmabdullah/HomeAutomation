package com.abdullah.home.automation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class MonthlyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String payloadMonth;

    @NotNull
    @ManyToOne
    @JoinColumn(name="station_id")
    private Station station;

    @OneToMany(mappedBy="monthlyData")
    private List<Payload> payloadList;
}
