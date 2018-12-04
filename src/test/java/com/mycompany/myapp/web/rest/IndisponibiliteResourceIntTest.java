package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.LaFactoryApp;

import com.mycompany.myapp.domain.Indisponibilite;
import com.mycompany.myapp.repository.IndisponibiliteRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IndisponibiliteResource REST controller.
 *
 * @see IndisponibiliteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class IndisponibiliteResourceIntTest {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;

    @Autowired
    private IndisponibiliteRepository indisponibiliteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndisponibiliteMockMvc;

    private Indisponibilite indisponibilite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndisponibiliteResource indisponibiliteResource = new IndisponibiliteResource(indisponibiliteRepository);
        this.restIndisponibiliteMockMvc = MockMvcBuilders.standaloneSetup(indisponibiliteResource)
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
    public static Indisponibilite createEntity(EntityManager em) {
        Indisponibilite indisponibilite = new Indisponibilite()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .duree(DEFAULT_DUREE);
        return indisponibilite;
    }

    @Before
    public void initTest() {
        indisponibilite = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndisponibilite() throws Exception {
        int databaseSizeBeforeCreate = indisponibiliteRepository.findAll().size();

        // Create the Indisponibilite
        restIndisponibiliteMockMvc.perform(post("/api/indisponibilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indisponibilite)))
            .andExpect(status().isCreated());

        // Validate the Indisponibilite in the database
        List<Indisponibilite> indisponibiliteList = indisponibiliteRepository.findAll();
        assertThat(indisponibiliteList).hasSize(databaseSizeBeforeCreate + 1);
        Indisponibilite testIndisponibilite = indisponibiliteList.get(indisponibiliteList.size() - 1);
        assertThat(testIndisponibilite.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testIndisponibilite.getDuree()).isEqualTo(DEFAULT_DUREE);
    }

    @Test
    @Transactional
    public void createIndisponibiliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indisponibiliteRepository.findAll().size();

        // Create the Indisponibilite with an existing ID
        indisponibilite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndisponibiliteMockMvc.perform(post("/api/indisponibilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indisponibilite)))
            .andExpect(status().isBadRequest());

        // Validate the Indisponibilite in the database
        List<Indisponibilite> indisponibiliteList = indisponibiliteRepository.findAll();
        assertThat(indisponibiliteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIndisponibilites() throws Exception {
        // Initialize the database
        indisponibiliteRepository.saveAndFlush(indisponibilite);

        // Get all the indisponibiliteList
        restIndisponibiliteMockMvc.perform(get("/api/indisponibilites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indisponibilite.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)));
    }
    
    @Test
    @Transactional
    public void getIndisponibilite() throws Exception {
        // Initialize the database
        indisponibiliteRepository.saveAndFlush(indisponibilite);

        // Get the indisponibilite
        restIndisponibiliteMockMvc.perform(get("/api/indisponibilites/{id}", indisponibilite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(indisponibilite.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE));
    }

    @Test
    @Transactional
    public void getNonExistingIndisponibilite() throws Exception {
        // Get the indisponibilite
        restIndisponibiliteMockMvc.perform(get("/api/indisponibilites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndisponibilite() throws Exception {
        // Initialize the database
        indisponibiliteRepository.saveAndFlush(indisponibilite);

        int databaseSizeBeforeUpdate = indisponibiliteRepository.findAll().size();

        // Update the indisponibilite
        Indisponibilite updatedIndisponibilite = indisponibiliteRepository.findById(indisponibilite.getId()).get();
        // Disconnect from session so that the updates on updatedIndisponibilite are not directly saved in db
        em.detach(updatedIndisponibilite);
        updatedIndisponibilite
            .dateDebut(UPDATED_DATE_DEBUT)
            .duree(UPDATED_DUREE);

        restIndisponibiliteMockMvc.perform(put("/api/indisponibilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndisponibilite)))
            .andExpect(status().isOk());

        // Validate the Indisponibilite in the database
        List<Indisponibilite> indisponibiliteList = indisponibiliteRepository.findAll();
        assertThat(indisponibiliteList).hasSize(databaseSizeBeforeUpdate);
        Indisponibilite testIndisponibilite = indisponibiliteList.get(indisponibiliteList.size() - 1);
        assertThat(testIndisponibilite.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testIndisponibilite.getDuree()).isEqualTo(UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void updateNonExistingIndisponibilite() throws Exception {
        int databaseSizeBeforeUpdate = indisponibiliteRepository.findAll().size();

        // Create the Indisponibilite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndisponibiliteMockMvc.perform(put("/api/indisponibilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indisponibilite)))
            .andExpect(status().isBadRequest());

        // Validate the Indisponibilite in the database
        List<Indisponibilite> indisponibiliteList = indisponibiliteRepository.findAll();
        assertThat(indisponibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIndisponibilite() throws Exception {
        // Initialize the database
        indisponibiliteRepository.saveAndFlush(indisponibilite);

        int databaseSizeBeforeDelete = indisponibiliteRepository.findAll().size();

        // Get the indisponibilite
        restIndisponibiliteMockMvc.perform(delete("/api/indisponibilites/{id}", indisponibilite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Indisponibilite> indisponibiliteList = indisponibiliteRepository.findAll();
        assertThat(indisponibiliteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indisponibilite.class);
        Indisponibilite indisponibilite1 = new Indisponibilite();
        indisponibilite1.setId(1L);
        Indisponibilite indisponibilite2 = new Indisponibilite();
        indisponibilite2.setId(indisponibilite1.getId());
        assertThat(indisponibilite1).isEqualTo(indisponibilite2);
        indisponibilite2.setId(2L);
        assertThat(indisponibilite1).isNotEqualTo(indisponibilite2);
        indisponibilite1.setId(null);
        assertThat(indisponibilite1).isNotEqualTo(indisponibilite2);
    }
}
