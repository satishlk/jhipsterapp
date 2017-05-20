package com.parking.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.parking.domain.Vehicles;

import com.parking.repository.VehiclesRepository;
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
 * REST controller for managing Vehicles.
 */
@RestController
@RequestMapping("/api")
public class VehiclesResource {

    private final Logger log = LoggerFactory.getLogger(VehiclesResource.class);

    private static final String ENTITY_NAME = "vehicles";
        
    private final VehiclesRepository vehiclesRepository;

    public VehiclesResource(VehiclesRepository vehiclesRepository) {
        this.vehiclesRepository = vehiclesRepository;
    }

    /**
     * POST  /vehicles : Create a new vehicles.
     *
     * @param vehicles the vehicles to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicles, or with status 400 (Bad Request) if the vehicles has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicles")
    @Timed
    public ResponseEntity<Vehicles> createVehicles(@RequestBody Vehicles vehicles) throws URISyntaxException {
        log.debug("REST request to save Vehicles : {}", vehicles);
        if (vehicles.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehicles cannot already have an ID")).body(null);
        }
        Vehicles result = vehiclesRepository.save(vehicles);
        return ResponseEntity.created(new URI("/api/vehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicles : Updates an existing vehicles.
     *
     * @param vehicles the vehicles to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicles,
     * or with status 400 (Bad Request) if the vehicles is not valid,
     * or with status 500 (Internal Server Error) if the vehicles couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicles")
    @Timed
    public ResponseEntity<Vehicles> updateVehicles(@RequestBody Vehicles vehicles) throws URISyntaxException {
        log.debug("REST request to update Vehicles : {}", vehicles);
        if (vehicles.getId() == null) {
            return createVehicles(vehicles);
        }
        Vehicles result = vehiclesRepository.save(vehicles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicles.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicles : get all the vehicles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicles in body
     */
    @GetMapping("/vehicles")
    @Timed
    public List<Vehicles> getAllVehicles() {
        log.debug("REST request to get all Vehicles");
        List<Vehicles> vehicles = vehiclesRepository.findAll();
        return vehicles;
    }

    /**
     * GET  /vehicles/:id : get the "id" vehicles.
     *
     * @param id the id of the vehicles to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicles, or with status 404 (Not Found)
     */
    @GetMapping("/vehicles/{id}")
    @Timed
    public ResponseEntity<Vehicles> getVehicles(@PathVariable Long id) {
        log.debug("REST request to get Vehicles : {}", id);
        Vehicles vehicles = vehiclesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicles));
    }

    /**
     * DELETE  /vehicles/:id : delete the "id" vehicles.
     *
     * @param id the id of the vehicles to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicles/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicles(@PathVariable Long id) {
        log.debug("REST request to delete Vehicles : {}", id);
        vehiclesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
