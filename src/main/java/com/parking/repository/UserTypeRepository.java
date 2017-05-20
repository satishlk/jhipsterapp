package com.parking.repository;

import com.parking.domain.UserType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserType entity.
 */
@SuppressWarnings("unused")
public interface UserTypeRepository extends JpaRepository<UserType,Long> {

}
