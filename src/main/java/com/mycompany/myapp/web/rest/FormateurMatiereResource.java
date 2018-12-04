package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.FormateurMatiere;
import com.mycompany.myapp.repository.FormateurMatiereRepository;
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
 * REST controller for managing FormateurMatiere.
 */
@RestController
@RequestMapping("/api")
public class FormateurMatiereResource {

    private final Logger log = LoggerFactory.getLogger(FormateurMatiereResource.class);

    private static final String ENTITY_NAME = "formateurMatiere";

    private final FormateurMatiereRepository formateurMatiereRepository;

    public FormateurMatiereResource(FormateurMatiereRepository formateurMatiereRepository) {
        this.formateurMatiereRepository = formateurMatiereRepository;
    }

    /**
     * POST  /formateur-matieres : Create a new formateurMatiere.
     *
     * @param formateurMatiere the formateurMatiere to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formateurMatiere, or with status 400 (Bad Request) if the formateurMatiere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/formateur-matieres")
    @Timed
    public ResponseEntity<FormateurMatiere> createFormateurMatiere(@RequestBody FormateurMatiere formateurMatiere) throws URISyntaxException {
        log.debug("REST request to save FormateurMatiere : {}", formateurMatiere);
        if (formateurMatiere.getId() != null) {
            throw new BadRequestAlertException("A new formateurMatiere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormateurMatiere result = formateurMatiereRepository.save(formateurMatiere);
        return ResponseEntity.created(new URI("/api/formateur-matieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /formateur-matieres : Updates an existing formateurMatiere.
     *
     * @param formateurMatiere the formateurMatiere to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formateurMatiere,
     * or with status 400 (Bad Request) if the formateurMatiere is not valid,
     * or with status 500 (Internal Server Error) if the formateurMatiere couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/formateur-matieres")
    @Timed
    public ResponseEntity<FormateurMatiere> updateFormateurMatiere(@RequestBody FormateurMatiere formateurMatiere) throws URISyntaxException {
        log.debug("REST request to update FormateurMatiere : {}", formateurMatiere);
        if (formateurMatiere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormateurMatiere result = formateurMatiereRepository.save(formateurMatiere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formateurMatiere.getId().toString()))
            .body(result);
    }

    /**
     * GET  /formateur-matieres : get all the formateurMatieres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of formateurMatieres in body
     */
    @GetMapping("/formateur-matieres")
    @Timed
    public List<FormateurMatiere> getAllFormateurMatieres() {
        log.debug("REST request to get all FormateurMatieres");
        return formateurMatiereRepository.findAll();
    }

    /**
     * GET  /formateur-matieres/:id : get the "id" formateurMatiere.
     *
     * @param id the id of the formateurMatiere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formateurMatiere, or with status 404 (Not Found)
     */
    @GetMapping("/formateur-matieres/{id}")
    @Timed
    public ResponseEntity<FormateurMatiere> getFormateurMatiere(@PathVariable Long id) {
        log.debug("REST request to get FormateurMatiere : {}", id);
        Optional<FormateurMatiere> formateurMatiere = formateurMatiereRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formateurMatiere);
    }

    /**
     * DELETE  /formateur-matieres/:id : delete the "id" formateurMatiere.
     *
     * @param id the id of the formateurMatiere to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/formateur-matieres/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormateurMatiere(@PathVariable Long id) {
        log.debug("REST request to delete FormateurMatiere : {}", id);

        formateurMatiereRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
