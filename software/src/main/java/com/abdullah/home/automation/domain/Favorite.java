package com.abdullah.home.automation.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Favorite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive = true;

    @OneToOne(cascade = CascadeType.ALL)
    private Station station;

    public Favorite() {

    }
    public Favorite( Station station, boolean isActive) {
        this.station = station;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Favorite(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", station=" + station +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorite)) return false;
        Favorite favorite = (Favorite) o;
        return isActive() == favorite.isActive() &&
                Objects.equals(getId(), favorite.getId()) &&
                Objects.equals(getStation(), favorite.getStation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isActive(), getStation());
    }
}
