package com.parking.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RateCard.
 */
@Entity
@Table(name = "rate_card")
public class RateCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate_per_hour")
    private Double ratePerHour;

    @Column(name = "extra_hour")
    private Double extraHour;

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

    @Column(name = "activation")
    private Boolean activation;

    @OneToOne
    @JoinColumn(unique = true)
    private Vehicles vehicle;

    @OneToOne
    @JoinColumn(unique = true)
    private Vehicles vehicles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRatePerHour() {
        return ratePerHour;
    }

    public RateCard ratePerHour(Double ratePerHour) {
        this.ratePerHour = ratePerHour;
        return this;
    }

    public void setRatePerHour(Double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public Double getExtraHour() {
        return extraHour;
    }

    public RateCard extraHour(Double extraHour) {
        this.extraHour = extraHour;
        return this;
    }

    public void setExtraHour(Double extraHour) {
        this.extraHour = extraHour;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public RateCard vendorId(Long vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public ZonedDateTime getAddDatetime() {
        return addDatetime;
    }

    public RateCard addDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
        return this;
    }

    public void setAddDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public RateCard addUserId(String addUserId) {
        this.addUserId = addUserId;
        return this;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public RateCard updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public RateCard updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Boolean isActivation() {
        return activation;
    }

    public RateCard activation(Boolean activation) {
        this.activation = activation;
        return this;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    public Vehicles getVehicle() {
        return vehicle;
    }

    public RateCard vehicle(Vehicles vehicles) {
        this.vehicle = vehicles;
        return this;
    }

    public void setVehicle(Vehicles vehicles) {
        this.vehicle = vehicles;
    }

    public Vehicles getVehicles() {
        return vehicles;
    }

    public RateCard vehicles(Vehicles vehicles) {
        this.vehicles = vehicles;
        return this;
    }

    public void setVehicles(Vehicles vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RateCard rateCard = (RateCard) o;
        if (rateCard.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rateCard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RateCard{" +
            "id=" + id +
            ", ratePerHour='" + ratePerHour + "'" +
            ", extraHour='" + extraHour + "'" +
            ", vendorId='" + vendorId + "'" +
            ", addDatetime='" + addDatetime + "'" +
            ", addUserId='" + addUserId + "'" +
            ", updateDateTime='" + updateDateTime + "'" +
            ", updateUserId='" + updateUserId + "'" +
            ", activation='" + activation + "'" +
            '}';
    }
}
