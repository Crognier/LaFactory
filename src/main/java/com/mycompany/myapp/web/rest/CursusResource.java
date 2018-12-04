package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Cursus;
import com.mycompany.myapp.repository.CursusRepository;
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
 * REST controller for managing Cursus.
 */
@RestController
@RequestMapping("/api")
public class CursusResource {

    private final Logger log = LoggerFactory.getLogger(CursusResource.class);

    private static final String ENTITY_NAME = "cursus";

    private final CursusRepository cursusRepository;

    public CursusResource(CursusRepository cursusRepository) {
        this.cursusRepository = cursusRepository;
    }

    /**
     * POST  /cursuses : Create a new cursus.
     *
     * @param cursus the cursus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cursus, or with status 400 (Bad Request) if the cursus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cursuses")
    @Timed
    public ResponseEntity<Cursus> createCursus(@RequestBody Cursus cursus) throws URISyntaxException {
        log.debug("REST request to save Cursus : {}", cursus);
        if (cursus.getId() != null) {
            throw new BadRequestAlertException("A new cursus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cursus result = cursusRepository.save(cursus);
        return ResponseEntity.created(new URI("/api/cursuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cursuses : Updates an existing cursus.
     *
     * @param cursus the cursus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cursus,
     * or with status 400 (Bad Request) if the cursus is not valid,
     * or with status 500 (Internal Server Error) if the cursus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cursuses")
    @Timed
    public ResponseEntity<Cursus> updateCursus(@RequestBody Cursus cursus) throws URISyntaxException {
        log.debug("REST request to update Cursus : {}", cursus);
        if (cursus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cursus result = cursusRepository.save(cursus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cursus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cursuses : get all the cursuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cursuses in body
     */
    @GetMapping("/cursuses")
    @Timed
    public List<Cursus> getAllCursuses() {
        log.debug("REST request to get all Cursuses");
        return cursusRepository.findAll();
    }

    /**
     * GET  /cursuses/:id : get the "id" cursus.
     *
     * @param id the id of the cursus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cursus, or with status 404 (Not Found)
     */
    @GetMapping("/cursuses/{id}")
    @Timed
    public ResponseEntity<Cursus> getCursus(@PathVariable Long id) {
        log.debug("REST request to get Cursus : {}", id);
        Optional<Cursus> cursus = cursusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cursus);
    }

    /**
     * DELETE  /cursuses/:id : delete the "id" cursus.
     *
     * @param id the id of the cursus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cursuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCursus(@PathVariable Long id) {
        log.debug("REST request to delete Cursus : {}", id);

        cursusRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
