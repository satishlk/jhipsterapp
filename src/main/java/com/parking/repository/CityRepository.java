package com.parking.repository;

import com.parking.domain.City;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the City entity.
 */
@SuppressWarnings("unused")
public interface CityRepository extends JpaRepository<City,Long> {

}
