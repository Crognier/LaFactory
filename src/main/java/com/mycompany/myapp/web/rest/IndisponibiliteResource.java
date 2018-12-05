package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Indisponibilite;
import com.mycompany.myapp.service.IndisponibiliteService;
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
 * REST controller for managing Indisponibilite.
 */
@RestController
@RequestMapping("/api")
public class IndisponibiliteResource {

    private final Logger log = LoggerFactory.getLogger(IndisponibiliteResource.class);

    private static final String ENTITY_NAME = "indisponibilite";

    private final IndisponibiliteService indisponibiliteService;

    public IndisponibiliteResource(IndisponibiliteService indisponibiliteService) {
        this.indisponibiliteService = indisponibiliteService;
    }

    /**
     * POST  /indisponibilites : Create a new indisponibilite.
     *
     * @param indisponibilite the indisponibilite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new indisponibilite, or with status 400 (Bad Request) if the indisponibilite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/indisponibilites")
    @Timed
    public ResponseEntity<Indisponibilite> createIndisponibilite(@RequestBody Indisponibilite indisponibilite) throws URISyntaxException {
        log.debug("REST request to save Indisponibilite : {}", indisponibilite);
        if (indisponibilite.getId() != null) {
            throw new BadRequestAlertException("A new indisponibilite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Indisponibilite result = indisponibiliteService.save(indisponibilite);
        return ResponseEntity.created(new URI("/api/indisponibilites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /indisponibilites : Updates an existing indisponibilite.
     *
     * @param indisponibilite the indisponibilite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated indisponibilite,
     * or with status 400 (Bad Request) if the indisponibilite is not valid,
     * or with status 500 (Internal Server Error) if the indisponibilite couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/indisponibilites")
    @Timed
    public ResponseEntity<Indisponibilite> updateIndisponibilite(@RequestBody Indisponibilite indisponibilite) throws URISyntaxException {
        log.debug("REST request to update Indisponibilite : {}", indisponibilite);
        if (indisponibilite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Indisponibilite result = indisponibiliteService.save(indisponibilite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, indisponibilite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /indisponibilites : get all the indisponibilites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of indisponibilites in body
     */
    @GetMapping("/indisponibilites")
    @Timed
    public List<Indisponibilite> getAllIndisponibilites() {
        log.debug("REST request to get all Indisponibilites");
        return indisponibiliteService.findAll();
    }

    /**
     * GET  /indisponibilites/:id : get the "id" indisponibilite.
     *
     * @param id the id of the indisponibilite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the indisponibilite, or with status 404 (Not Found)
     */
    @GetMapping("/indisponibilites/{id}")
    @Timed
    public ResponseEntity<Indisponibilite> getIndisponibilite(@PathVariable Long id) {
        log.debug("REST request to get Indisponibilite : {}", id);
        Optional<Indisponibilite> indisponibilite = indisponibiliteService.findById(id);
        return ResponseUtil.wrapOrNotFound(indisponibilite);
    }

    /**
     * DELETE  /indisponibilites/:id : delete the "id" indisponibilite.
     *
     * @param id the id of the indisponibilite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/indisponibilites/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndisponibilite(@PathVariable Long id) {
        log.debug("REST request to delete Indisponibilite : {}", id);

        indisponibiliteService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
