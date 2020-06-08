package com.abdullah.home.automation.domain;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
@Entity
public class WeatherEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String entityName;

    @OneToMany(mappedBy="weatherEntity")
    private List<Payload> payloadList;

    public WeatherEntity() {

    }

    public WeatherEntity(String entityName){
        this.entityName = entityName;
    }

    public WeatherEntity(Long id, List<Payload> payloadList) {
        this.id = id;
        this.payloadList = payloadList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<Payload> getPayloadList() {
        return payloadList;
    }

    public void setPayloadList(List<Payload> payloadList) {
        this.payloadList = payloadList;
    }


    @Override
    public String toString() {
        return "WeatherEntity{" +
                "id=" + id +
                ", entityName='" + entityName + '\'' +
                ", payloadList=" + payloadList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherEntity)) return false;
        WeatherEntity that = (WeatherEntity) o;
        return getId().equals(that.getId()) &&
                getEntityName().equals(that.getEntityName()) &&
                getPayloadList().equals(that.getPayloadList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEntityName(), getPayloadList());
    }
}
