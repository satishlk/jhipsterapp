package com.parking.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ParkingHistory.
 */
@Entity
@Table(name = "parking_history")
public class ParkingHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parking_id")
    private Long parkingId;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "intime")
    private ZonedDateTime intime;

    @Column(name = "out_time")
    private ZonedDateTime outTime;

    @Column(name = "in_user")
    private String inUser;

    @Column(name = "out_user")
    private String outUser;

    @Column(name = "jhi_cost")
    private Long cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public ParkingHistory parkingId(Long parkingId) {
        this.parkingId = parkingId;
        return this;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public ParkingHistory vehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
        return this;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public ParkingHistory contactNo(String contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public ZonedDateTime getIntime() {
        return intime;
    }

    public ParkingHistory intime(ZonedDateTime intime) {
        this.intime = intime;
        return this;
    }

    public void setIntime(ZonedDateTime intime) {
        this.intime = intime;
    }

    public ZonedDateTime getOutTime() {
        return outTime;
    }

    public ParkingHistory outTime(ZonedDateTime outTime) {
        this.outTime = outTime;
        return this;
    }

    public void setOutTime(ZonedDateTime outTime) {
        this.outTime = outTime;
    }

    public String getInUser() {
        return inUser;
    }

    public ParkingHistory inUser(String inUser) {
        this.inUser = inUser;
        return this;
    }

    public void setInUser(String inUser) {
        this.inUser = inUser;
    }

    public String getOutUser() {
        return outUser;
    }

    public ParkingHistory outUser(String outUser) {
        this.outUser = outUser;
        return this;
    }

    public void setOutUser(String outUser) {
        this.outUser = outUser;
    }

    public Long getCost() {
        return cost;
    }

    public ParkingHistory cost(Long cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParkingHistory parkingHistory = (ParkingHistory) o;
        if (parkingHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, parkingHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParkingHistory{" +
            "id=" + id +
            ", parkingId='" + parkingId + "'" +
            ", vehicleNo='" + vehicleNo + "'" +
            ", contactNo='" + contactNo + "'" +
            ", intime='" + intime + "'" +
            ", outTime='" + outTime + "'" +
            ", inUser='" + inUser + "'" +
            ", outUser='" + outUser + "'" +
            ", cost='" + cost + "'" +
            '}';
    }
}
