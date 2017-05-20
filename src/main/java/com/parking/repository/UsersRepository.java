package com.parking.repository;

import com.parking.domain.Users;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Users entity.
 */
@SuppressWarnings("unused")
public interface UsersRepository extends JpaRepository<Users,Long> {

}
