package com.parking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A State.
 */
@Entity
@Table(name = "state")
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "state_description")
    private String stateDescription;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "state")
    @JsonIgnore
    private Set<City> states = new HashSet<>();

    @ManyToOne
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStateId() {
        return stateId;
    }

    public State stateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public State stateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public State stateCode(String stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public State stateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
        return this;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public Country getCountry() {
        return country;
    }

    public State country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<City> getStates() {
        return states;
    }

    public State states(Set<City> cities) {
        this.states = cities;
        return this;
    }

    public State addState(City city) {
        this.states.add(city);
        city.setState(this);
        return this;
    }

    public State removeState(City city) {
        this.states.remove(city);
        city.setState(null);
        return this;
    }

    public void setStates(Set<City> cities) {
        this.states = cities;
    }

    public Location getLocation() {
        return location;
    }

    public State location(Location location) {
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
        State state = (State) o;
        if (state.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, state.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "State{" +
            "id=" + id +
            ", stateId='" + stateId + "'" +
            ", stateName='" + stateName + "'" +
            ", stateCode='" + stateCode + "'" +
            ", stateDescription='" + stateDescription + "'" +
            '}';
    }
}
