package com.parking.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A City.
 */
@Entity
@Table(name = "city")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "city_description")
    private String cityDescription;

    @ManyToOne
    private State state;

    @ManyToOne
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public City cityId(Integer cityId) {
        this.cityId = cityId;
        return this;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public City cityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public City cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityDescription() {
        return cityDescription;
    }

    public City cityDescription(String cityDescription) {
        this.cityDescription = cityDescription;
        return this;
    }

    public void setCityDescription(String cityDescription) {
        this.cityDescription = cityDescription;
    }

    public State getState() {
        return state;
    }

    public City state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Location getLocation() {
        return location;
    }

    public City location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        if (city.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "City{" +
            "id=" + id +
            ", cityId='" + cityId + "'" +
            ", cityName='" + cityName + "'" +
            ", cityCode='" + cityCode + "'" +
            ", cityDescription='" + cityDescription + "'" +
            '}';
    }
}
