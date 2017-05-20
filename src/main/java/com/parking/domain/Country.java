package com.parking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_description")
    private String countryDescription;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private Set<State> countries = new HashSet<>();

    @ManyToOne
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public Country countryId(Integer countryId) {
        this.countryId = countryId;
        return this;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Country countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryDescription() {
        return countryDescription;
    }

    public Country countryDescription(String countryDescription) {
        this.countryDescription = countryDescription;
        return this;
    }

    public void setCountryDescription(String countryDescription) {
        this.countryDescription = countryDescription;
    }

    public Set<State> getCountries() {
        return countries;
    }

    public Country countries(Set<State> states) {
        this.countries = states;
        return this;
    }

    public Country addCountry(State state) {
        this.countries.add(state);
        state.setCountry(this);
        return this;
    }

    public Country removeCountry(State state) {
        this.countries.remove(state);
        state.setCountry(null);
        return this;
    }

    public void setCountries(Set<State> states) {
        this.countries = states;
    }

    public Location getLocation() {
        return location;
    }

    public Country location(Location location) {
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
        Country country = (Country) o;
        if (country.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, country.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + id +
            ", countryId='" + countryId + "'" +
            ", countryName='" + countryName + "'" +
            ", countryCode='" + countryCode + "'" +
            ", countryDescription='" + countryDescription + "'" +
            '}';
    }
}
