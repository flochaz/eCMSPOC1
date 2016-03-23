package com.thalesinflyt.ecms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thalesinflyt.ecms.domain.Airline;
import com.thalesinflyt.ecms.repository.AirlineRepository;
import com.thalesinflyt.ecms.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Airline.
 */
@RestController
@RequestMapping("/api")
public class AirlineResource {

    private final Logger log = LoggerFactory.getLogger(AirlineResource.class);
        
    @Inject
    private AirlineRepository airlineRepository;
    
    /**
     * POST  /airlines -> Create a new airline.
     */
    @RequestMapping(value = "/airlines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Airline> createAirline(@RequestBody Airline airline) throws URISyntaxException {
        log.debug("REST request to save Airline : {}", airline);
        if (airline.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("airline", "idexists", "A new airline cannot already have an ID")).body(null);
        }
        Airline result = airlineRepository.save(airline);
        return ResponseEntity.created(new URI("/api/airlines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("airline", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /airlines -> Updates an existing airline.
     */
    @RequestMapping(value = "/airlines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Airline> updateAirline(@RequestBody Airline airline) throws URISyntaxException {
        log.debug("REST request to update Airline : {}", airline);
        if (airline.getId() == null) {
            return createAirline(airline);
        }
        Airline result = airlineRepository.save(airline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("airline", airline.getId().toString()))
            .body(result);
    }

    /**
     * GET  /airlines -> get all the airlines.
     */
    @RequestMapping(value = "/airlines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Airline> getAllAirlines() {
        log.debug("REST request to get all Airlines");
        return airlineRepository.findAll();
            }

    /**
     * GET  /airlines/:id -> get the "id" airline.
     */
    @RequestMapping(value = "/airlines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Airline> getAirline(@PathVariable String id) {
        log.debug("REST request to get Airline : {}", id);
        Airline airline = airlineRepository.findOne(id);
        return Optional.ofNullable(airline)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /airlines/:id -> delete the "id" airline.
     */
    @RequestMapping(value = "/airlines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAirline(@PathVariable String id) {
        log.debug("REST request to delete Airline : {}", id);
        airlineRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("airline", id.toString())).build();
    }
}
