package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Ordinateur;
import com.mycompany.myapp.repository.OrdinateurRepository;
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
 * REST controller for managing Ordinateur.
 */
@RestController
@RequestMapping("/api")
public class OrdinateurResource {

    private final Logger log = LoggerFactory.getLogger(OrdinateurResource.class);

    private static final String ENTITY_NAME = "ordinateur";

    private final OrdinateurRepository ordinateurRepository;

    public OrdinateurResource(OrdinateurRepository ordinateurRepository) {
        this.ordinateurRepository = ordinateurRepository;
    }

    /**
     * POST  /ordinateurs : Create a new ordinateur.
     *
     * @param ordinateur the ordinateur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordinateur, or with status 400 (Bad Request) if the ordinateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordinateurs")
    @Timed
    public ResponseEntity<Ordinateur> createOrdinateur(@RequestBody Ordinateur ordinateur) throws URISyntaxException {
        log.debug("REST request to save Ordinateur : {}", ordinateur);
        if (ordinateur.getId() != null) {
            throw new BadRequestAlertException("A new ordinateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ordinateur result = ordinateurRepository.save(ordinateur);
        return ResponseEntity.created(new URI("/api/ordinateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordinateurs : Updates an existing ordinateur.
     *
     * @param ordinateur the ordinateur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordinateur,
     * or with status 400 (Bad Request) if the ordinateur is not valid,
     * or with status 500 (Internal Server Error) if the ordinateur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordinateurs")
    @Timed
    public ResponseEntity<Ordinateur> updateOrdinateur(@RequestBody Ordinateur ordinateur) throws URISyntaxException {
        log.debug("REST request to update Ordinateur : {}", ordinateur);
        if (ordinateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ordinateur result = ordinateurRepository.save(ordinateur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordinateur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordinateurs : get all the ordinateurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ordinateurs in body
     */
    @GetMapping("/ordinateurs")
    @Timed
    public List<Ordinateur> getAllOrdinateurs() {
        log.debug("REST request to get all Ordinateurs");
        return ordinateurRepository.findAll();
    }

    /**
     * GET  /ordinateurs/:id : get the "id" ordinateur.
     *
     * @param id the id of the ordinateur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordinateur, or with status 404 (Not Found)
     */
    @GetMapping("/ordinateurs/{id}")
    @Timed
    public ResponseEntity<Ordinateur> getOrdinateur(@PathVariable Long id) {
        log.debug("REST request to get Ordinateur : {}", id);
        Optional<Ordinateur> ordinateur = ordinateurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ordinateur);
    }

    /**
     * DELETE  /ordinateurs/:id : delete the "id" ordinateur.
     *
     * @param id the id of the ordinateur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordinateurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdinateur(@PathVariable Long id) {
        log.debug("REST request to delete Ordinateur : {}", id);

        ordinateurRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
