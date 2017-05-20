package com.parking.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserType.
 */
@Entity
@Table(name = "user_type")
public class UserType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "user_descrption")
    private String userDescrption;

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

    public String getUserType() {
        return userType;
    }

    public UserType userType(String userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserDescrption() {
        return userDescrption;
    }

    public UserType userDescrption(String userDescrption) {
        this.userDescrption = userDescrption;
        return this;
    }

    public void setUserDescrption(String userDescrption) {
        this.userDescrption = userDescrption;
    }

    public ZonedDateTime getAddDatetime() {
        return addDatetime;
    }

    public UserType addDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
        return this;
    }

    public void setAddDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public UserType addUserId(String addUserId) {
        this.addUserId = addUserId;
        return this;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public UserType updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public UserType updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Boolean isActivation() {
        return activation;
    }

    public UserType activation(Boolean activation) {
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
        UserType userType = (UserType) o;
        if (userType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserType{" +
            "id=" + id +
            ", userType='" + userType + "'" +
            ", userDescrption='" + userDescrption + "'" +
            ", addDatetime='" + addDatetime + "'" +
            ", addUserId='" + addUserId + "'" +
            ", updateDateTime='" + updateDateTime + "'" +
            ", updateUserId='" + updateUserId + "'" +
            ", activation='" + activation + "'" +
            '}';
    }
}
