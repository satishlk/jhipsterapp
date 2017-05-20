package com.parking.repository;

import com.parking.domain.RateCard;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RateCard entity.
 */
@SuppressWarnings("unused")
public interface RateCardRepository extends JpaRepository<RateCard,Long> {

}
