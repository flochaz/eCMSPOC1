package com.thalesinflyt.ecms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thalesinflyt.ecms.domain.ContentDefinition;
import com.thalesinflyt.ecms.repository.ContentDefinitionRepository;
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
 * REST controller for managing ContentDefinition.
 */
@RestController
@RequestMapping("/api")
public class ContentDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(ContentDefinitionResource.class);
        
    @Inject
    private ContentDefinitionRepository contentDefinitionRepository;
    
    /**
     * POST  /contentDefinitions -> Create a new contentDefinition.
     */
    @RequestMapping(value = "/contentDefinitions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentDefinition> createContentDefinition(@RequestBody ContentDefinition contentDefinition) throws URISyntaxException {
        log.debug("REST request to save ContentDefinition : {}", contentDefinition);
        if (contentDefinition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contentDefinition", "idexists", "A new contentDefinition cannot already have an ID")).body(null);
        }
        ContentDefinition result = contentDefinitionRepository.save(contentDefinition);
        return ResponseEntity.created(new URI("/api/contentDefinitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contentDefinition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contentDefinitions -> Updates an existing contentDefinition.
     */
    @RequestMapping(value = "/contentDefinitions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentDefinition> updateContentDefinition(@RequestBody ContentDefinition contentDefinition) throws URISyntaxException {
        log.debug("REST request to update ContentDefinition : {}", contentDefinition);
        if (contentDefinition.getId() == null) {
            return createContentDefinition(contentDefinition);
        }
        ContentDefinition result = contentDefinitionRepository.save(contentDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contentDefinition", contentDefinition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contentDefinitions -> get all the contentDefinitions.
     */
    @RequestMapping(value = "/contentDefinitions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ContentDefinition> getAllContentDefinitions() {
        log.debug("REST request to get all ContentDefinitions");
        return contentDefinitionRepository.findAll();
            }

    /**
     * GET  /contentDefinitions/:id -> get the "id" contentDefinition.
     */
    @RequestMapping(value = "/contentDefinitions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentDefinition> getContentDefinition(@PathVariable String id) {
        log.debug("REST request to get ContentDefinition : {}", id);
        ContentDefinition contentDefinition = contentDefinitionRepository.findOne(id);
        return Optional.ofNullable(contentDefinition)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contentDefinitions/:id -> delete the "id" contentDefinition.
     */
    @RequestMapping(value = "/contentDefinitions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContentDefinition(@PathVariable String id) {
        log.debug("REST request to delete ContentDefinition : {}", id);
        contentDefinitionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contentDefinition", id.toString())).build();
    }
}
