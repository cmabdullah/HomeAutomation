package com.abdullah.home.automation.domain;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
@Entity
public class MonthlyData implements Serializable {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayloadMonth() {
        return payloadMonth;
    }

    public void setPayloadMonth(String payloadMonth) {
        this.payloadMonth = payloadMonth;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Payload> getPayloadList() {
        return payloadList;
    }

    public void setPayloadList(List<Payload> payloadList) {
        this.payloadList = payloadList;
    }

    public MonthlyData() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonthlyData)) return false;
        MonthlyData that = (MonthlyData) o;
        return getId().equals(that.getId()) &&
                getPayloadMonth().equals(that.getPayloadMonth()) &&
                getStation().equals(that.getStation()) &&
                getPayloadList().equals(that.getPayloadList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPayloadMonth(), getStation(), getPayloadList());
    }

    @Override
    public String toString() {
        return "MonthlyData{" +
                "id=" + id +
                ", payloadMonth='" + payloadMonth + '\'' +
                ", station=" + station +
                ", payloadList=" + payloadList +
                '}';
    }
}
