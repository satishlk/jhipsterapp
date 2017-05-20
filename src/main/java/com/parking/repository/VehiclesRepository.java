package com.parking.repository;

import com.parking.domain.Vehicles;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Vehicles entity.
 */
@SuppressWarnings("unused")
public interface VehiclesRepository extends JpaRepository<Vehicles,Long> {

}
