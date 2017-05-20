package com.parking.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.parking.domain.Users;

import com.parking.repository.UsersRepository;
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
 * REST controller for managing Users.
 */
@RestController
@RequestMapping("/api")
public class UsersResource {

    private final Logger log = LoggerFactory.getLogger(UsersResource.class);

    private static final String ENTITY_NAME = "users";
        
    private final UsersRepository usersRepository;

    public UsersResource(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * POST  /users : Create a new users.
     *
     * @param users the users to create
     * @return the ResponseEntity with status 201 (Created) and with body the new users, or with status 400 (Bad Request) if the users has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    public ResponseEntity<Users> createUsers(@RequestBody Users users) throws URISyntaxException {
        log.debug("REST request to save Users : {}", users);
        if (users.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new users cannot already have an ID")).body(null);
        }
        Users result = usersRepository.save(users);
        return ResponseEntity.created(new URI("/api/users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /users : Updates an existing users.
     *
     * @param users the users to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated users,
     * or with status 400 (Bad Request) if the users is not valid,
     * or with status 500 (Internal Server Error) if the users couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/users")
    @Timed
    public ResponseEntity<Users> updateUsers(@RequestBody Users users) throws URISyntaxException {
        log.debug("REST request to update Users : {}", users);
        if (users.getId() == null) {
            return createUsers(users);
        }
        Users result = usersRepository.save(users);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, users.getId().toString()))
            .body(result);
    }

    /**
     * GET  /users : get all the users.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of users in body
     */
    @GetMapping("/users")
    @Timed
    public List<Users> getAllUsers() {
        log.debug("REST request to get all Users");
        List<Users> users = usersRepository.findAll();
        return users;
    }

    /**
     * GET  /users/:id : get the "id" users.
     *
     * @param id the id of the users to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the users, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    @Timed
    public ResponseEntity<Users> getUsers(@PathVariable Long id) {
        log.debug("REST request to get Users : {}", id);
        Users users = usersRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(users));
    }

    /**
     * DELETE  /users/:id : delete the "id" users.
     *
     * @param id the id of the users to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    @Timed
    public ResponseEntity<Void> deleteUsers(@PathVariable Long id) {
        log.debug("REST request to delete Users : {}", id);
        usersRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
