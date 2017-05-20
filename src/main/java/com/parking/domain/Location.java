package com.parking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "location_address")
    private String locationAddress;

    @Column(name = "location_contact_no")
    private String locationContactNo;

    @Column(name = "activation")
    private Boolean activation;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "add_datetime")
    private ZonedDateTime addDatetime;

    @Column(name = "add_user_id")
    private String addUserId;

    @Column(name = "update_date_time")
    private ZonedDateTime updateDateTime;

    @Column(name = "update_user_id")
    private String updateUserId;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private Set<City> cities = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private Set<State> states = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private Set<Country> countries = new HashSet<>();

    @ManyToOne
    private Users users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public Location locationId(Long locationId) {
        this.locationId = locationId;
        return this;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public Location locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public Location locationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
        return this;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationContactNo() {
        return locationContactNo;
    }

    public Location locationContactNo(String locationContactNo) {
        this.locationContactNo = locationContactNo;
        return this;
    }

    public void setLocationContactNo(String locationContactNo) {
        this.locationContactNo = locationContactNo;
    }

    public Boolean isActivation() {
        return activation;
    }

    public Location activation(Boolean activation) {
        this.activation = activation;
        return this;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public Location vendorId(Long vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public ZonedDateTime getAddDatetime() {
        return addDatetime;
    }

    public Location addDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
        return this;
    }

    public void setAddDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public Location addUserId(String addUserId) {
        this.addUserId = addUserId;
        return this;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public Location updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public Location updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Set<City> getCities() {
        return cities;
    }

    public Location cities(Set<City> cities) {
        this.cities = cities;
        return this;
    }

    public Location addCity(City city) {
        this.cities.add(city);
        city.setLocation(this);
        return this;
    }

    public Location removeCity(City city) {
        this.cities.remove(city);
        city.setLocation(null);
        return this;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public Set<State> getStates() {
        return states;
    }

    public Location states(Set<State> states) {
        this.states = states;
        return this;
    }

    public Location addState(State state) {
        this.states.add(state);
        state.setLocation(this);
        return this;
    }

    public Location removeState(State state) {
        this.states.remove(state);
        state.setLocation(null);
        return this;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public Location countries(Set<Country> countries) {
        this.countries = countries;
        return this;
    }

    public Location addCountry(Country country) {
        this.countries.add(country);
        country.setLocation(this);
        return this;
    }

    public Location removeCountry(Country country) {
        this.countries.remove(country);
        country.setLocation(null);
        return this;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Users getUsers() {
        return users;
    }

    public Location users(Users users) {
        this.users = users;
        return this;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + id +
            ", locationId='" + locationId + "'" +
            ", locationName='" + locationName + "'" +
            ", locationAddress='" + locationAddress + "'" +
            ", locationContactNo='" + locationContactNo + "'" +
            ", activation='" + activation + "'" +
            ", vendorId='" + vendorId + "'" +
            ", addDatetime='" + addDatetime + "'" +
            ", addUserId='" + addUserId + "'" +
            ", updateDateTime='" + updateDateTime + "'" +
            ", updateUserId='" + updateUserId + "'" +
            '}';
    }
}
