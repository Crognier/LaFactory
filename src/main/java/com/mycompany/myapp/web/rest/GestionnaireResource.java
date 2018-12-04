package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Gestionnaire;
import com.mycompany.myapp.repository.GestionnaireRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
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
 * REST controller for managing Gestionnaire.
 */
@RestController
@RequestMapping("/api")
public class GestionnaireResource {

    private final Logger log = LoggerFactory.getLogger(GestionnaireResource.class);

    private static final String ENTITY_NAME = "gestionnaire";

    private final GestionnaireRepository gestionnaireRepository;

    public GestionnaireResource(GestionnaireRepository gestionnaireRepository) {
        this.gestionnaireRepository = gestionnaireRepository;
    }

    /**
     * POST  /gestionnaires : Create a new gestionnaire.
     *
     * @param gestionnaire the gestionnaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gestionnaire, or with status 400 (Bad Request) if the gestionnaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gestionnaires")
    @Timed
    public ResponseEntity<Gestionnaire> createGestionnaire(@RequestBody Gestionnaire gestionnaire) throws URISyntaxException {
        log.debug("REST request to save Gestionnaire : {}", gestionnaire);
        if (gestionnaire.getId() != null) {
            throw new BadRequestAlertException("A new gestionnaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gestionnaire result = gestionnaireRepository.save(gestionnaire);
        return ResponseEntity.created(new URI("/api/gestionnaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gestionnaires : Updates an existing gestionnaire.
     *
     * @param gestionnaire the gestionnaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gestionnaire,
     * or with status 400 (Bad Request) if the gestionnaire is not valid,
     * or with status 500 (Internal Server Error) if the gestionnaire couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gestionnaires")
    @Timed
    public ResponseEntity<Gestionnaire> updateGestionnaire(@RequestBody Gestionnaire gestionnaire) throws URISyntaxException {
        log.debug("REST request to update Gestionnaire : {}", gestionnaire);
        if (gestionnaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gestionnaire result = gestionnaireRepository.save(gestionnaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gestionnaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gestionnaires : get all the gestionnaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gestionnaires in body
     */
    @GetMapping("/gestionnaires")
    @Timed
    public List<Gestionnaire> getAllGestionnaires() {
        log.debug("REST request to get all Gestionnaires");
        return gestionnaireRepository.findAll();
    }

    /**
     * GET  /gestionnaires/:id : get the "id" gestionnaire.
     *
     * @param id the id of the gestionnaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gestionnaire, or with status 404 (Not Found)
     */
    @GetMapping("/gestionnaires/{id}")
    @Timed
    public ResponseEntity<Gestionnaire> getGestionnaire(@PathVariable Long id) {
        log.debug("REST request to get Gestionnaire : {}", id);
        Optional<Gestionnaire> gestionnaire = gestionnaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gestionnaire);
    }

    /**
     * DELETE  /gestionnaires/:id : delete the "id" gestionnaire.
     *
     * @param id the id of the gestionnaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gestionnaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteGestionnaire(@PathVariable Long id) {
        log.debug("REST request to delete Gestionnaire : {}", id);

        gestionnaireRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
