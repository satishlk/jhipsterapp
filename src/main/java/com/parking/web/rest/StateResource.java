package com.parking.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.parking.domain.State;

import com.parking.repository.StateRepository;
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
 * REST controller for managing State.
 */
@RestController
@RequestMapping("/api")
public class StateResource {

    private final Logger log = LoggerFactory.getLogger(StateResource.class);

    private static final String ENTITY_NAME = "state";
        
    private final StateRepository stateRepository;

    public StateResource(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    /**
     * POST  /states : Create a new state.
     *
     * @param state the state to create
     * @return the ResponseEntity with status 201 (Created) and with body the new state, or with status 400 (Bad Request) if the state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/states")
    @Timed
    public ResponseEntity<State> createState(@RequestBody State state) throws URISyntaxException {
        log.debug("REST request to save State : {}", state);
        if (state.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new state cannot already have an ID")).body(null);
        }
        State result = stateRepository.save(state);
        return ResponseEntity.created(new URI("/api/states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /states : Updates an existing state.
     *
     * @param state the state to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated state,
     * or with status 400 (Bad Request) if the state is not valid,
     * or with status 500 (Internal Server Error) if the state couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/states")
    @Timed
    public ResponseEntity<State> updateState(@RequestBody State state) throws URISyntaxException {
        log.debug("REST request to update State : {}", state);
        if (state.getId() == null) {
            return createState(state);
        }
        State result = stateRepository.save(state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/states")
    @Timed
    public List<State> getAllStates() {
        log.debug("REST request to get all States");
        List<State> states = stateRepository.findAll();
        return states;
    }

    /**
     * GET  /states/:id : get the "id" state.
     *
     * @param id the id of the state to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the state, or with status 404 (Not Found)
     */
    @GetMapping("/states/{id}")
    @Timed
    public ResponseEntity<State> getState(@PathVariable Long id) {
        log.debug("REST request to get State : {}", id);
        State state = stateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(state));
    }

    /**
     * DELETE  /states/:id : delete the "id" state.
     *
     * @param id the id of the state to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/states/{id}")
    @Timed
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        log.debug("REST request to delete State : {}", id);
        stateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
