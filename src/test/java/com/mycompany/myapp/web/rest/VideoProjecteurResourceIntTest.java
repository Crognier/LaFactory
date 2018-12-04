package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.LaFactoryApp;

import com.mycompany.myapp.domain.VideoProjecteur;
import com.mycompany.myapp.repository.VideoProjecteurRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VideoProjecteurResource REST controller.
 *
 * @see VideoProjecteurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class VideoProjecteurResourceIntTest {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final Double DEFAULT_COUT_PAR_JOUR = 1D;
    private static final Double UPDATED_COUT_PAR_JOUR = 2D;

    @Autowired
    private VideoProjecteurRepository videoProjecteurRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVideoProjecteurMockMvc;

    private VideoProjecteur videoProjecteur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VideoProjecteurResource videoProjecteurResource = new VideoProjecteurResource(videoProjecteurRepository);
        this.restVideoProjecteurMockMvc = MockMvcBuilders.standaloneSetup(videoProjecteurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoProjecteur createEntity(EntityManager em) {
        VideoProjecteur videoProjecteur = new VideoProjecteur()
            .code(DEFAULT_CODE)
            .coutParJour(DEFAULT_COUT_PAR_JOUR);
        return videoProjecteur;
    }

    @Before
    public void initTest() {
        videoProjecteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createVideoProjecteur() throws Exception {
        int databaseSizeBeforeCreate = videoProjecteurRepository.findAll().size();

        // Create the VideoProjecteur
        restVideoProjecteurMockMvc.perform(post("/api/video-projecteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoProjecteur)))
            .andExpect(status().isCreated());

        // Validate the VideoProjecteur in the database
        List<VideoProjecteur> videoProjecteurList = videoProjecteurRepository.findAll();
        assertThat(videoProjecteurList).hasSize(databaseSizeBeforeCreate + 1);
        VideoProjecteur testVideoProjecteur = videoProjecteurList.get(videoProjecteurList.size() - 1);
        assertThat(testVideoProjecteur.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVideoProjecteur.getCoutParJour()).isEqualTo(DEFAULT_COUT_PAR_JOUR);
    }

    @Test
    @Transactional
    public void createVideoProjecteurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = videoProjecteurRepository.findAll().size();

        // Create the VideoProjecteur with an existing ID
        videoProjecteur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoProjecteurMockMvc.perform(post("/api/video-projecteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoProjecteur)))
            .andExpect(status().isBadRequest());

        // Validate the VideoProjecteur in the database
        List<VideoProjecteur> videoProjecteurList = videoProjecteurRepository.findAll();
        assertThat(videoProjecteurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVideoProjecteurs() throws Exception {
        // Initialize the database
        videoProjecteurRepository.saveAndFlush(videoProjecteur);

        // Get all the videoProjecteurList
        restVideoProjecteurMockMvc.perform(get("/api/video-projecteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoProjecteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].coutParJour").value(hasItem(DEFAULT_COUT_PAR_JOUR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getVideoProjecteur() throws Exception {
        // Initialize the database
        videoProjecteurRepository.saveAndFlush(videoProjecteur);

        // Get the videoProjecteur
        restVideoProjecteurMockMvc.perform(get("/api/video-projecteurs/{id}", videoProjecteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(videoProjecteur.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.coutParJour").value(DEFAULT_COUT_PAR_JOUR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVideoProjecteur() throws Exception {
        // Get the videoProjecteur
        restVideoProjecteurMockMvc.perform(get("/api/video-projecteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideoProjecteur() throws Exception {
        // Initialize the database
        videoProjecteurRepository.saveAndFlush(videoProjecteur);

        int databaseSizeBeforeUpdate = videoProjecteurRepository.findAll().size();

        // Update the videoProjecteur
        VideoProjecteur updatedVideoProjecteur = videoProjecteurRepository.findById(videoProjecteur.getId()).get();
        // Disconnect from session so that the updates on updatedVideoProjecteur are not directly saved in db
        em.detach(updatedVideoProjecteur);
        updatedVideoProjecteur
            .code(UPDATED_CODE)
            .coutParJour(UPDATED_COUT_PAR_JOUR);

        restVideoProjecteurMockMvc.perform(put("/api/video-projecteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVideoProjecteur)))
            .andExpect(status().isOk());

        // Validate the VideoProjecteur in the database
        List<VideoProjecteur> videoProjecteurList = videoProjecteurRepository.findAll();
        assertThat(videoProjecteurList).hasSize(databaseSizeBeforeUpdate);
        VideoProjecteur testVideoProjecteur = videoProjecteurList.get(videoProjecteurList.size() - 1);
        assertThat(testVideoProjecteur.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVideoProjecteur.getCoutParJour()).isEqualTo(UPDATED_COUT_PAR_JOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingVideoProjecteur() throws Exception {
        int databaseSizeBeforeUpdate = videoProjecteurRepository.findAll().size();

        // Create the VideoProjecteur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoProjecteurMockMvc.perform(put("/api/video-projecteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoProjecteur)))
            .andExpect(status().isBadRequest());

        // Validate the VideoProjecteur in the database
        List<VideoProjecteur> videoProjecteurList = videoProjecteurRepository.findAll();
        assertThat(videoProjecteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVideoProjecteur() throws Exception {
        // Initialize the database
        videoProjecteurRepository.saveAndFlush(videoProjecteur);

        int databaseSizeBeforeDelete = videoProjecteurRepository.findAll().size();

        // Get the videoProjecteur
        restVideoProjecteurMockMvc.perform(delete("/api/video-projecteurs/{id}", videoProjecteur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VideoProjecteur> videoProjecteurList = videoProjecteurRepository.findAll();
        assertThat(videoProjecteurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoProjecteur.class);
        VideoProjecteur videoProjecteur1 = new VideoProjecteur();
        videoProjecteur1.setId(1L);
        VideoProjecteur videoProjecteur2 = new VideoProjecteur();
        videoProjecteur2.setId(videoProjecteur1.getId());
        assertThat(videoProjecteur1).isEqualTo(videoProjecteur2);
        videoProjecteur2.setId(2L);
        assertThat(videoProjecteur1).isNotEqualTo(videoProjecteur2);
        videoProjecteur1.setId(null);
        assertThat(videoProjecteur1).isNotEqualTo(videoProjecteur2);
    }
}
