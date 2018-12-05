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
import com.mycompany.myapp.domain.VideoProjecteur;
import com.mycompany.myapp.service.VideoProjecteurService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing VideoProjecteur.
 */
@RestController
@RequestMapping("/api")
public class VideoProjecteurResource {

    private final Logger log = LoggerFactory.getLogger(VideoProjecteurResource.class);

    private static final String ENTITY_NAME = "videoProjecteur";

    private final VideoProjecteurService videoProjecteurService;

    public VideoProjecteurResource(VideoProjecteurService videoProjecteurService) {
        this.videoProjecteurService = videoProjecteurService;
    }

    /**
     * POST  /video-projecteurs : Create a new videoProjecteur.
     *
     * @param videoProjecteur the videoProjecteur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new videoProjecteur, or with status 400 (Bad Request) if the videoProjecteur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/video-projecteurs")
    @Timed
    public ResponseEntity<VideoProjecteur> createVideoProjecteur(@RequestBody VideoProjecteur videoProjecteur) throws URISyntaxException {
        log.debug("REST request to save VideoProjecteur : {}", videoProjecteur);
        if (videoProjecteur.getId() != null) {
            throw new BadRequestAlertException("A new videoProjecteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideoProjecteur result = videoProjecteurService.save(videoProjecteur);
        return ResponseEntity.created(new URI("/api/video-projecteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /video-projecteurs : Updates an existing videoProjecteur.
     *
     * @param videoProjecteur the videoProjecteur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated videoProjecteur,
     * or with status 400 (Bad Request) if the videoProjecteur is not valid,
     * or with status 500 (Internal Server Error) if the videoProjecteur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/video-projecteurs")
    @Timed
    public ResponseEntity<VideoProjecteur> updateVideoProjecteur(@RequestBody VideoProjecteur videoProjecteur) throws URISyntaxException {
        log.debug("REST request to update VideoProjecteur : {}", videoProjecteur);
        if (videoProjecteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VideoProjecteur result = videoProjecteurService.save(videoProjecteur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, videoProjecteur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /video-projecteurs : get all the videoProjecteurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of videoProjecteurs in body
     */
    @GetMapping("/video-projecteurs")
    @Timed
    public List<VideoProjecteur> getAllVideoProjecteurs() {
        log.debug("REST request to get all VideoProjecteurs");
        return videoProjecteurService.findAll();
    }

    /**
     * GET  /video-projecteurs/:id : get the "id" videoProjecteur.
     *
     * @param id the id of the videoProjecteur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the videoProjecteur, or with status 404 (Not Found)
     */
    @GetMapping("/video-projecteurs/{id}")
    @Timed
    public ResponseEntity<VideoProjecteur> getVideoProjecteur(@PathVariable Long id) {
        log.debug("REST request to get VideoProjecteur : {}", id);
        Optional<VideoProjecteur> videoProjecteur = videoProjecteurService.findById(id);
        return ResponseUtil.wrapOrNotFound(videoProjecteur);
    }

    /**
     * DELETE  /video-projecteurs/:id : delete the "id" videoProjecteur.
     *
     * @param id the id of the videoProjecteur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/video-projecteurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteVideoProjecteur(@PathVariable Long id) {
        log.debug("REST request to delete VideoProjecteur : {}", id);

        videoProjecteurService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
