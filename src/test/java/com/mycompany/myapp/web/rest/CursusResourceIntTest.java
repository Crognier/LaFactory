package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

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

import com.mycompany.myapp.LaFactoryApp;
import com.mycompany.myapp.domain.Cursus;
import com.mycompany.myapp.service.CursusService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the CursusResource REST controller.
 *
 * @see CursusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class CursusResourceIntTest {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;

    @Autowired
    private CursusService cursusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCursusMockMvc;

    private Cursus cursus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CursusResource cursusResource = new CursusResource(cursusService);
        this.restCursusMockMvc = MockMvcBuilders.standaloneSetup(cursusResource)
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
    public static Cursus createEntity(EntityManager em) {
        Cursus cursus = new Cursus()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .duree(DEFAULT_DUREE);
        return cursus;
    }

    @Before
    public void initTest() {
        cursus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCursus() throws Exception {
        int databaseSizeBeforeCreate = cursusService.findAll().size();

        // Create the Cursus
        restCursusMockMvc.perform(post("/api/cursuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursus)))
            .andExpect(status().isCreated());

        // Validate the Cursus in the database
        List<Cursus> cursusList = cursusService.findAll();
        assertThat(cursusList).hasSize(databaseSizeBeforeCreate + 1);
        Cursus testCursus = cursusList.get(cursusList.size() - 1);
        assertThat(testCursus.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testCursus.getDuree()).isEqualTo(DEFAULT_DUREE);
    }

    @Test
    @Transactional
    public void createCursusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cursusService.findAll().size();

        // Create the Cursus with an existing ID
        cursus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCursusMockMvc.perform(post("/api/cursuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursus)))
            .andExpect(status().isBadRequest());

        // Validate the Cursus in the database
        List<Cursus> cursusList = cursusService.findAll();
        assertThat(cursusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCursuses() throws Exception {
        // Initialize the database
        cursusService.saveAndFlush(cursus);

        // Get all the cursusList
        restCursusMockMvc.perform(get("/api/cursuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cursus.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)));
    }
    
    @Test
    @Transactional
    public void getCursus() throws Exception {
        // Initialize the database
    	cursusService.saveAndFlush(cursus);

        // Get the cursus
        restCursusMockMvc.perform(get("/api/cursuses/{id}", cursus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cursus.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE));
    }

    @Test
    @Transactional
    public void getNonExistingCursus() throws Exception {
        // Get the cursus
        restCursusMockMvc.perform(get("/api/cursuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCursus() throws Exception {
        // Initialize the database
    	cursusService.saveAndFlush(cursus);

        int databaseSizeBeforeUpdate = cursusService.findAll().size();

        // Update the cursus
        Cursus updatedCursus = cursusService.findById(cursus.getId()).get();
        // Disconnect from session so that the updates on updatedCursus are not directly saved in db
        em.detach(updatedCursus);
        updatedCursus
            .dateDebut(UPDATED_DATE_DEBUT)
            .duree(UPDATED_DUREE);

        restCursusMockMvc.perform(put("/api/cursuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCursus)))
            .andExpect(status().isOk());

        // Validate the Cursus in the database
        List<Cursus> cursusList = cursusService.findAll();
        assertThat(cursusList).hasSize(databaseSizeBeforeUpdate);
        Cursus testCursus = cursusList.get(cursusList.size() - 1);
        assertThat(testCursus.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testCursus.getDuree()).isEqualTo(UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void updateNonExistingCursus() throws Exception {
        int databaseSizeBeforeUpdate = cursusService.findAll().size();

        // Create the Cursus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCursusMockMvc.perform(put("/api/cursuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cursus)))
            .andExpect(status().isBadRequest());

        // Validate the Cursus in the database
        List<Cursus> cursusList = cursusService.findAll();
        assertThat(cursusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCursus() throws Exception {
        // Initialize the database
    	cursusService.saveAndFlush(cursus);

        int databaseSizeBeforeDelete = cursusService.findAll().size();

        // Get the cursus
        restCursusMockMvc.perform(delete("/api/cursuses/{id}", cursus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cursus> cursusList = cursusService.findAll();
        assertThat(cursusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cursus.class);
        Cursus cursus1 = new Cursus();
        cursus1.setId(1L);
        Cursus cursus2 = new Cursus();
        cursus2.setId(cursus1.getId());
        assertThat(cursus1).isEqualTo(cursus2);
        cursus2.setId(2L);
        assertThat(cursus1).isNotEqualTo(cursus2);
        cursus1.setId(null);
        assertThat(cursus1).isNotEqualTo(cursus2);
    }
}
