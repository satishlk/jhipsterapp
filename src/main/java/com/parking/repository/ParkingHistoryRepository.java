package com.parking.repository;

import com.parking.domain.ParkingHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParkingHistory entity.
 */
@SuppressWarnings("unused")
public interface ParkingHistoryRepository extends JpaRepository<ParkingHistory,Long> {

}
