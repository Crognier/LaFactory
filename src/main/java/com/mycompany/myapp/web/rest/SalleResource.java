package com.mycompany.myapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Salle;
import com.mycompany.myapp.service.SalleService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Salle.
 */
@RestController
@RequestMapping("/api")
public class SalleResource {

    private final Logger log = LoggerFactory.getLogger(SalleResource.class);

    private static final String ENTITY_NAME = "salle";

    private final SalleService salleService;

    public SalleResource(SalleService salleService) {
        this.salleService = salleService;
    }

    /**
     * POST  /salles : Create a new salle.
     *
     * @param salle the salle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salle, or with status 400 (Bad Request) if the salle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salles")
    @Timed
    public ResponseEntity<Salle> createSalle(@RequestBody Salle salle) throws URISyntaxException {
        log.debug("REST request to save Salle : {}", salle);
        if (salle.getId() != null) {
            throw new BadRequestAlertException("A new salle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Salle result = salleService.save(salle);
        return ResponseEntity.created(new URI("/api/salles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salles : Updates an existing salle.
     *
     * @param salle the salle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salle,
     * or with status 400 (Bad Request) if the salle is not valid,
     * or with status 500 (Internal Server Error) if the salle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salles")
    @Timed
    public ResponseEntity<Salle> updateSalle(@RequestBody Salle salle) throws URISyntaxException {
        log.debug("REST request to update Salle : {}", salle);
        if (salle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Salle result = salleService.save(salle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salles : get all the salles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salles in body
     */
    @GetMapping("/salles")
    @Timed
    public List<Salle> getAllSalles() {
        log.debug("REST request to get all Salles");
        return salleService.findAll();
    }

    /**
     * GET  /salles/:id : get the "id" salle.
     *
     * @param id the id of the salle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salle, or with status 404 (Not Found)
     */
    @GetMapping("/salles/{id}")
    @Timed
    public ResponseEntity<Salle> getSalle(@PathVariable Long id) {
        log.debug("REST request to get Salle : {}", id);
        Optional<Salle> salle = salleService.findById(id);
        return ResponseUtil.wrapOrNotFound(salle);
    }

    /**
     * DELETE  /salles/:id : delete the "id" salle.
     *
     * @param id the id of the salle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salles/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalle(@PathVariable Long id) {
        log.debug("REST request to delete Salle : {}", id);

        salleService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
