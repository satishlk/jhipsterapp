package com.parking.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.parking.domain.ParkingHistory;

import com.parking.repository.ParkingHistoryRepository;
import com.parking.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ParkingHistory.
 */
@RestController
@RequestMapping("/api")
public class ParkingHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ParkingHistoryResource.class);

    private static final String ENTITY_NAME = "parkingHistory";
        
    private final ParkingHistoryRepository parkingHistoryRepository;

    public ParkingHistoryResource(ParkingHistoryRepository parkingHistoryRepository) {
        this.parkingHistoryRepository = parkingHistoryRepository;
    }

    /**
     * POST  /parking-histories : Create a new parkingHistory.
     *
     * @param parkingHistory the parkingHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parkingHistory, or with status 400 (Bad Request) if the parkingHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parking-histories")
    @Timed
    public ResponseEntity<ParkingHistory> createParkingHistory(@RequestBody ParkingHistory parkingHistory) throws URISyntaxException {
        log.debug("REST request to save ParkingHistory : {}", parkingHistory);
        if (parkingHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new parkingHistory cannot already have an ID")).body(null);
        }
        ParkingHistory result = parkingHistoryRepository.save(parkingHistory);
        return ResponseEntity.created(new URI("/api/parking-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parking-histories : Updates an existing parkingHistory.
     *
     * @param parkingHistory the parkingHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parkingHistory,
     * or with status 400 (Bad Request) if the parkingHistory is not valid,
     * or with status 500 (Internal Server Error) if the parkingHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parking-histories")
    @Timed
    public ResponseEntity<ParkingHistory> updateParkingHistory(@RequestBody ParkingHistory parkingHistory) throws URISyntaxException {
        log.debug("REST request to update ParkingHistory : {}", parkingHistory);
        if (parkingHistory.getId() == null) {
            return createParkingHistory(parkingHistory);
        }
        ParkingHistory result = parkingHistoryRepository.save(parkingHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parkingHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parking-histories : get all the parkingHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of parkingHistories in body
     */
    @GetMapping("/parking-histories")
    @Timed
    public List<ParkingHistory> getAllParkingHistories() {
        log.debug("REST request to get all ParkingHistories");
        List<ParkingHistory> parkingHistories = parkingHistoryRepository.findAll();
        return parkingHistories;
    }

    /**
     * GET  /parking-histories/:id : get the "id" parkingHistory.
     *
     * @param id the id of the parkingHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parkingHistory, or with status 404 (Not Found)
     */
    @GetMapping("/parking-histories/{id}")
    @Timed
    public ResponseEntity<ParkingHistory> getParkingHistory(@PathVariable Long id) {
        log.debug("REST request to get ParkingHistory : {}", id);
        ParkingHistory parkingHistory = parkingHistoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parkingHistory));
    }

    /**
     * DELETE  /parking-histories/:id : delete the "id" parkingHistory.
     *
     * @param id the id of the parkingHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parking-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteParkingHistory(@PathVariable Long id) {
        log.debug("REST request to delete ParkingHistory : {}", id);
        parkingHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
