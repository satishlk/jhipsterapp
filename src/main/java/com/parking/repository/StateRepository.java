package com.parking.repository;

import com.parking.domain.State;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the State entity.
 */
@SuppressWarnings("unused")
public interface StateRepository extends JpaRepository<State,Long> {

}
