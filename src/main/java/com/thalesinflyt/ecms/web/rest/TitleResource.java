package com.thalesinflyt.ecms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thalesinflyt.ecms.domain.Title;
import com.thalesinflyt.ecms.service.TitleService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Title.
 */
@RestController
@RequestMapping("/api")
public class TitleResource {

    private final Logger log = LoggerFactory.getLogger(TitleResource.class);
        
    @Inject
    private TitleService titleService;
    
    /**
     * POST  /titles -> Create a new title.
     */
    @RequestMapping(value = "/titles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Title> createTitle(@RequestBody Title title) throws URISyntaxException {
        log.debug("REST request to save Title : {}", title);
        if (title.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("title", "idexists", "A new title cannot already have an ID")).body(null);
        }
        Title result = titleService.save(title);
        return ResponseEntity.created(new URI("/api/titles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("title", result.getId().toString()))
            .body(result);
    }
    
    /**
     * POST  /titles -> Create a list of new titles.
     */
    @RequestMapping(value = "/titlesbulk",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Title>> createTitle(@RequestBody List<Title> titles) throws URISyntaxException {
        log.debug("REST request to save {} Title", titles.size());
        List<Title> result = titleService.save(titles);
        return ResponseEntity.created(new URI("/api/titles/"))
                .headers(HeaderUtil.createEntityCreationAlert("title", "0"))
                .body(result);
    }

    /**
     * PUT  /titles -> Updates an existing title.
     */
    @RequestMapping(value = "/titles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Title> updateTitle(@RequestBody Title title) throws URISyntaxException {
        log.debug("REST request to update Title : {}", title);
        if (title.getId() == null) {
            return createTitle(title);
        }
        Title result = titleService.save(title);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("title", title.getId().toString()))
            .body(result);
    }
    
    /**
     * PUT  /titles -> Updates a list of existing titles.
     */
    @RequestMapping(value = "/titlesbulk",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Title>> updateTitle(@RequestBody List<Title> titles) throws URISyntaxException {
        log.debug("REST request to update {} Titles", titles.size());
        List<Title> result = titleService.save(titles);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("title", "0"))
                .body(result);
    }

    /**
     * GET  /titles -> get all the titles.
     */
    @RequestMapping(value = "/titles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Title> getAllTitles() {
        log.debug("REST request to get all Titles");
        return titleService.findAll();
            }

    /**
     * GET  /titles/:id -> get the "id" title.
     */
    @RequestMapping(value = "/titles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Title> getTitle(@PathVariable String id) {
        log.debug("REST request to get Title : {}", id);
        Title title = titleService.findOne(id);
        return Optional.ofNullable(title)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /titles/:id -> delete the "id" title.
     */
    @RequestMapping(value = "/titles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTitle(@PathVariable String id) {
        log.debug("REST request to delete Title : {}", id);
        titleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("title", id.toString())).build();
    }
    
}
