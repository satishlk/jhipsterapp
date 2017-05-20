package com.parking.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.parking.domain.RateCard;

import com.parking.repository.RateCardRepository;
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
 * REST controller for managing RateCard.
 */
@RestController
@RequestMapping("/api")
public class RateCardResource {

    private final Logger log = LoggerFactory.getLogger(RateCardResource.class);

    private static final String ENTITY_NAME = "rateCard";
        
    private final RateCardRepository rateCardRepository;

    public RateCardResource(RateCardRepository rateCardRepository) {
        this.rateCardRepository = rateCardRepository;
    }

    /**
     * POST  /rate-cards : Create a new rateCard.
     *
     * @param rateCard the rateCard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rateCard, or with status 400 (Bad Request) if the rateCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rate-cards")
    @Timed
    public ResponseEntity<RateCard> createRateCard(@RequestBody RateCard rateCard) throws URISyntaxException {
        log.debug("REST request to save RateCard : {}", rateCard);
        if (rateCard.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rateCard cannot already have an ID")).body(null);
        }
        RateCard result = rateCardRepository.save(rateCard);
        return ResponseEntity.created(new URI("/api/rate-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rate-cards : Updates an existing rateCard.
     *
     * @param rateCard the rateCard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rateCard,
     * or with status 400 (Bad Request) if the rateCard is not valid,
     * or with status 500 (Internal Server Error) if the rateCard couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rate-cards")
    @Timed
    public ResponseEntity<RateCard> updateRateCard(@RequestBody RateCard rateCard) throws URISyntaxException {
        log.debug("REST request to update RateCard : {}", rateCard);
        if (rateCard.getId() == null) {
            return createRateCard(rateCard);
        }
        RateCard result = rateCardRepository.save(rateCard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rateCard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rate-cards : get all the rateCards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rateCards in body
     */
    @GetMapping("/rate-cards")
    @Timed
    public List<RateCard> getAllRateCards() {
        log.debug("REST request to get all RateCards");
        List<RateCard> rateCards = rateCardRepository.findAll();
        return rateCards;
    }

    /**
     * GET  /rate-cards/:id : get the "id" rateCard.
     *
     * @param id the id of the rateCard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rateCard, or with status 404 (Not Found)
     */
    @GetMapping("/rate-cards/{id}")
    @Timed
    public ResponseEntity<RateCard> getRateCard(@PathVariable Long id) {
        log.debug("REST request to get RateCard : {}", id);
        RateCard rateCard = rateCardRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rateCard));
    }

    /**
     * DELETE  /rate-cards/:id : delete the "id" rateCard.
     *
     * @param id the id of the rateCard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rate-cards/{id}")
    @Timed
    public ResponseEntity<Void> deleteRateCard(@PathVariable Long id) {
        log.debug("REST request to delete RateCard : {}", id);
        rateCardRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
