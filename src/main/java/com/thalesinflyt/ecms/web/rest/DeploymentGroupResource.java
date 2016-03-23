package com.thalesinflyt.ecms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thalesinflyt.ecms.domain.DeploymentGroup;
import com.thalesinflyt.ecms.repository.DeploymentGroupRepository;
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
 * REST controller for managing DeploymentGroup.
 */
@RestController
@RequestMapping("/api")
public class DeploymentGroupResource {

    private final Logger log = LoggerFactory.getLogger(DeploymentGroupResource.class);
        
    @Inject
    private DeploymentGroupRepository deploymentGroupRepository;
    
    /**
     * POST  /deploymentGroups -> Create a new deploymentGroup.
     */
    @RequestMapping(value = "/deploymentGroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeploymentGroup> createDeploymentGroup(@RequestBody DeploymentGroup deploymentGroup) throws URISyntaxException {
        log.debug("REST request to save DeploymentGroup : {}", deploymentGroup);
        if (deploymentGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("deploymentGroup", "idexists", "A new deploymentGroup cannot already have an ID")).body(null);
        }
        DeploymentGroup result = deploymentGroupRepository.save(deploymentGroup);
        return ResponseEntity.created(new URI("/api/deploymentGroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("deploymentGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deploymentGroups -> Updates an existing deploymentGroup.
     */
    @RequestMapping(value = "/deploymentGroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeploymentGroup> updateDeploymentGroup(@RequestBody DeploymentGroup deploymentGroup) throws URISyntaxException {
        log.debug("REST request to update DeploymentGroup : {}", deploymentGroup);
        if (deploymentGroup.getId() == null) {
            return createDeploymentGroup(deploymentGroup);
        }
        DeploymentGroup result = deploymentGroupRepository.save(deploymentGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("deploymentGroup", deploymentGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deploymentGroups -> get all the deploymentGroups.
     */
    @RequestMapping(value = "/deploymentGroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeploymentGroup> getAllDeploymentGroups() {
        log.debug("REST request to get all DeploymentGroups");
        return deploymentGroupRepository.findAll();
            }

    /**
     * GET  /deploymentGroups/:id -> get the "id" deploymentGroup.
     */
    @RequestMapping(value = "/deploymentGroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeploymentGroup> getDeploymentGroup(@PathVariable String id) {
        log.debug("REST request to get DeploymentGroup : {}", id);
        DeploymentGroup deploymentGroup = deploymentGroupRepository.findOne(id);
        return Optional.ofNullable(deploymentGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /deploymentGroups/:id -> delete the "id" deploymentGroup.
     */
    @RequestMapping(value = "/deploymentGroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeploymentGroup(@PathVariable String id) {
        log.debug("REST request to delete DeploymentGroup : {}", id);
        deploymentGroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("deploymentGroup", id.toString())).build();
    }
}
