package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Formateur;
import com.mycompany.myapp.repository.FormateurRepository;
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
 * REST controller for managing Formateur.
 */
@RestController
@RequestMapping("/api")
public class FormateurResource {

    private final Logger log = LoggerFactory.getLogger(FormateurResource.class);

    private static final String ENTITY_NAME = "formateur";

    private final FormateurRepository formateurRepository;

    public FormateurResource(FormateurRepository formateurRepository) {
        this.formateurRepository = formateurRepository;
    }

    /**
     * POST  /formateurs : Create a new formateur.
     *
     * @param formateur the formateur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formateur, or with status 400 (Bad Request) if the formateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/formateurs")
    @Timed
    public ResponseEntity<Formateur> createFormateur(@RequestBody Formateur formateur) throws URISyntaxException {
        log.debug("REST request to save Formateur : {}", formateur);
        if (formateur.getId() != null) {
            throw new BadRequestAlertException("A new formateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Formateur result = formateurRepository.save(formateur);
        return ResponseEntity.created(new URI("/api/formateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /formateurs : Updates an existing formateur.
     *
     * @param formateur the formateur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formateur,
     * or with status 400 (Bad Request) if the formateur is not valid,
     * or with status 500 (Internal Server Error) if the formateur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/formateurs")
    @Timed
    public ResponseEntity<Formateur> updateFormateur(@RequestBody Formateur formateur) throws URISyntaxException {
        log.debug("REST request to update Formateur : {}", formateur);
        if (formateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Formateur result = formateurRepository.save(formateur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formateur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /formateurs : get all the formateurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of formateurs in body
     */
    @GetMapping("/formateurs")
    @Timed
    public List<Formateur> getAllFormateurs() {
        log.debug("REST request to get all Formateurs");
        return formateurRepository.findAll();
    }

    /**
     * GET  /formateurs/:id : get the "id" formateur.
     *
     * @param id the id of the formateur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formateur, or with status 404 (Not Found)
     */
    @GetMapping("/formateurs/{id}")
    @Timed
    public ResponseEntity<Formateur> getFormateur(@PathVariable Long id) {
        log.debug("REST request to get Formateur : {}", id);
        Optional<Formateur> formateur = formateurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formateur);
    }

    /**
     * DELETE  /formateurs/:id : delete the "id" formateur.
     *
     * @param id the id of the formateur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/formateurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormateur(@PathVariable Long id) {
        log.debug("REST request to delete Formateur : {}", id);

        formateurRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
