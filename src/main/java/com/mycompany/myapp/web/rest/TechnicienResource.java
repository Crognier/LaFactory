package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Technicien;
import com.mycompany.myapp.repository.TechnicienRepository;
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
 * REST controller for managing Technicien.
 */
@RestController
@RequestMapping("/api")
public class TechnicienResource {

    private final Logger log = LoggerFactory.getLogger(TechnicienResource.class);

    private static final String ENTITY_NAME = "technicien";

    private final TechnicienRepository technicienRepository;

    public TechnicienResource(TechnicienRepository technicienRepository) {
        this.technicienRepository = technicienRepository;
    }

    /**
     * POST  /techniciens : Create a new technicien.
     *
     * @param technicien the technicien to create
     * @return the ResponseEntity with status 201 (Created) and with body the new technicien, or with status 400 (Bad Request) if the technicien has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/techniciens")
    @Timed
    public ResponseEntity<Technicien> createTechnicien(@RequestBody Technicien technicien) throws URISyntaxException {
        log.debug("REST request to save Technicien : {}", technicien);
        if (technicien.getId() != null) {
            throw new BadRequestAlertException("A new technicien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Technicien result = technicienRepository.save(technicien);
        return ResponseEntity.created(new URI("/api/techniciens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /techniciens : Updates an existing technicien.
     *
     * @param technicien the technicien to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated technicien,
     * or with status 400 (Bad Request) if the technicien is not valid,
     * or with status 500 (Internal Server Error) if the technicien couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/techniciens")
    @Timed
    public ResponseEntity<Technicien> updateTechnicien(@RequestBody Technicien technicien) throws URISyntaxException {
        log.debug("REST request to update Technicien : {}", technicien);
        if (technicien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Technicien result = technicienRepository.save(technicien);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, technicien.getId().toString()))
            .body(result);
    }

    /**
     * GET  /techniciens : get all the techniciens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of techniciens in body
     */
    @GetMapping("/techniciens")
    @Timed
    public List<Technicien> getAllTechniciens() {
        log.debug("REST request to get all Techniciens");
        return technicienRepository.findAll();
    }

    /**
     * GET  /techniciens/:id : get the "id" technicien.
     *
     * @param id the id of the technicien to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the technicien, or with status 404 (Not Found)
     */
    @GetMapping("/techniciens/{id}")
    @Timed
    public ResponseEntity<Technicien> getTechnicien(@PathVariable Long id) {
        log.debug("REST request to get Technicien : {}", id);
        Optional<Technicien> technicien = technicienRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(technicien);
    }

    /**
     * DELETE  /techniciens/:id : delete the "id" technicien.
     *
     * @param id the id of the technicien to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/techniciens/{id}")
    @Timed
    public ResponseEntity<Void> deleteTechnicien(@PathVariable Long id) {
        log.debug("REST request to delete Technicien : {}", id);

        technicienRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
