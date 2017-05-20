package com.parking.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Vehicles.
 */
@Entity
@Table(name = "vehicles")
public class Vehicles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "vehicles_type")
    private String vehiclesType;

    @Column(name = "vehicles_descrtion")
    private String vehiclesDescrtion;

    @Column(name = "add_datetime")
    private ZonedDateTime addDatetime;

    @Column(name = "add_user_id")
    private String addUserId;

    @Column(name = "update_date_time")
    private ZonedDateTime updateDateTime;

    @Column(name = "update_user_id")
    private String updateUserId;

    @Column(name = "activation")
    private Boolean activation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public Vehicles vehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehiclesType() {
        return vehiclesType;
    }

    public Vehicles vehiclesType(String vehiclesType) {
        this.vehiclesType = vehiclesType;
        return this;
    }

    public void setVehiclesType(String vehiclesType) {
        this.vehiclesType = vehiclesType;
    }

    public String getVehiclesDescrtion() {
        return vehiclesDescrtion;
    }

    public Vehicles vehiclesDescrtion(String vehiclesDescrtion) {
        this.vehiclesDescrtion = vehiclesDescrtion;
        return this;
    }

    public void setVehiclesDescrtion(String vehiclesDescrtion) {
        this.vehiclesDescrtion = vehiclesDescrtion;
    }

    public ZonedDateTime getAddDatetime() {
        return addDatetime;
    }

    public Vehicles addDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
        return this;
    }

    public void setAddDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public Vehicles addUserId(String addUserId) {
        this.addUserId = addUserId;
        return this;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public Vehicles updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public Vehicles updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Boolean isActivation() {
        return activation;
    }

    public Vehicles activation(Boolean activation) {
        this.activation = activation;
        return this;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicles vehicles = (Vehicles) o;
        if (vehicles.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, vehicles.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Vehicles{" +
            "id=" + id +
            ", vehicleId='" + vehicleId + "'" +
            ", vehiclesType='" + vehiclesType + "'" +
            ", vehiclesDescrtion='" + vehiclesDescrtion + "'" +
            ", addDatetime='" + addDatetime + "'" +
            ", addUserId='" + addUserId + "'" +
            ", updateDateTime='" + updateDateTime + "'" +
            ", updateUserId='" + updateUserId + "'" +
            ", activation='" + activation + "'" +
            '}';
    }
}
