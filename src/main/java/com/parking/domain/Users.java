package com.parking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Users.
 */
@Entity
@Table(name = "users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "address")
    private String address;

    @Column(name = "pincode")
    private Integer pincode;

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

    @Column(name = "parent")
    private Long parent;

    @OneToOne
    @JoinColumn(unique = true)
    private UserType type;

    @OneToOne
    @JoinColumn(unique = true)
    private City city;

    @OneToOne
    @JoinColumn(unique = true)
    private State state;

    @OneToOne
    @JoinColumn(unique = true)
    private Country country;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Location> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Users userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public Users fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public Users username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Users password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Users email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public Users contactNo(String contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public Users address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPincode() {
        return pincode;
    }

    public Users pincode(Integer pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public Boolean isActivation() {
        return activation;
    }

    public Users activation(Boolean activation) {
        this.activation = activation;
        return this;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public Users vendorId(Long vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public ZonedDateTime getAddDatetime() {
        return addDatetime;
    }

    public Users addDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
        return this;
    }

    public void setAddDatetime(ZonedDateTime addDatetime) {
        this.addDatetime = addDatetime;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public Users addUserId(String addUserId) {
        this.addUserId = addUserId;
        return this;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public Users updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public Users updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Long getParent() {
        return parent;
    }

    public Users parent(Long parent) {
        this.parent = parent;
        return this;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public UserType getType() {
        return type;
    }

    public Users type(UserType userType) {
        this.type = userType;
        return this;
    }

    public void setType(UserType userType) {
        this.type = userType;
    }

    public City getCity() {
        return city;
    }

    public Users city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public Users state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Country getCountry() {
        return country;
    }

    public Users country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Location getLocation() {
        return location;
    }

    public Users location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Location> getUsers() {
        return users;
    }

    public Users users(Set<Location> locations) {
        this.users = locations;
        return this;
    }

    public Users addUser(Location location) {
        this.users.add(location);
        location.setUsers(this);
        return this;
    }

    public Users removeUser(Location location) {
        this.users.remove(location);
        location.setUsers(null);
        return this;
    }

    public void setUsers(Set<Location> locations) {
        this.users = locations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Users users = (Users) o;
        if (users.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, users.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Users{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", fullName='" + fullName + "'" +
            ", username='" + username + "'" +
            ", password='" + password + "'" +
            ", email='" + email + "'" +
            ", contactNo='" + contactNo + "'" +
            ", address='" + address + "'" +
            ", pincode='" + pincode + "'" +
            ", activation='" + activation + "'" +
            ", vendorId='" + vendorId + "'" +
            ", addDatetime='" + addDatetime + "'" +
            ", addUserId='" + addUserId + "'" +
            ", updateDateTime='" + updateDateTime + "'" +
            ", updateUserId='" + updateUserId + "'" +
            ", parent='" + parent + "'" +
            '}';
    }
}
