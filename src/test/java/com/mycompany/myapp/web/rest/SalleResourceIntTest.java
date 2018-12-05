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
import com.mycompany.myapp.domain.Salle;
import com.mycompany.myapp.service.SalleService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the SalleResource REST controller.
 *
 * @see SalleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class SalleResourceIntTest {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final Double DEFAULT_COUT_PAR_JOUR = 1D;
    private static final Double UPDATED_COUT_PAR_JOUR = 2D;

    private static final Integer DEFAULT_CAPACITE = 1;
    private static final Integer UPDATED_CAPACITE = 2;

    @Autowired
    private SalleService salleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSalleMockMvc;

    private Salle salle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalleResource salleResource = new SalleResource(salleService);
        this.restSalleMockMvc = MockMvcBuilders.standaloneSetup(salleResource)
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
    public static Salle createEntity(EntityManager em) {
        Salle salle = new Salle()
            .code(DEFAULT_CODE)
            .coutParJour(DEFAULT_COUT_PAR_JOUR)
            .capacite(DEFAULT_CAPACITE);
        return salle;
    }

    @Before
    public void initTest() {
        salle = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalle() throws Exception {
        int databaseSizeBeforeCreate = salleService.findAll().size();

        // Create the Salle
        restSalleMockMvc.perform(post("/api/salles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salle)))
            .andExpect(status().isCreated());

        // Validate the Salle in the database
        List<Salle> salleList = salleService.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeCreate + 1);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSalle.getCoutParJour()).isEqualTo(DEFAULT_COUT_PAR_JOUR);
        assertThat(testSalle.getCapacite()).isEqualTo(DEFAULT_CAPACITE);
    }

    @Test
    @Transactional
    public void createSalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salleService.findAll().size();

        // Create the Salle with an existing ID
        salle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalleMockMvc.perform(post("/api/salles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salle)))
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleService.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSalles() throws Exception {
        // Initialize the database
        salleService.saveAndFlush(salle);

        // Get all the salleList
        restSalleMockMvc.perform(get("/api/salles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salle.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].coutParJour").value(hasItem(DEFAULT_COUT_PAR_JOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)));
    }
    
    @Test
    @Transactional
    public void getSalle() throws Exception {
        // Initialize the database
        salleService.saveAndFlush(salle);

        // Get the salle
        restSalleMockMvc.perform(get("/api/salles/{id}", salle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salle.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.coutParJour").value(DEFAULT_COUT_PAR_JOUR.doubleValue()))
            .andExpect(jsonPath("$.capacite").value(DEFAULT_CAPACITE));
    }

    @Test
    @Transactional
    public void getNonExistingSalle() throws Exception {
        // Get the salle
        restSalleMockMvc.perform(get("/api/salles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalle() throws Exception {
        // Initialize the database
        salleService.saveAndFlush(salle);

        int databaseSizeBeforeUpdate = salleService.findAll().size();

        // Update the salle
        Salle updatedSalle = salleService.findById(salle.getId()).get();
        // Disconnect from session so that the updates on updatedSalle are not directly saved in db
        em.detach(updatedSalle);
        updatedSalle
            .code(UPDATED_CODE)
            .coutParJour(UPDATED_COUT_PAR_JOUR)
            .capacite(UPDATED_CAPACITE);

        restSalleMockMvc.perform(put("/api/salles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalle)))
            .andExpect(status().isOk());

        // Validate the Salle in the database
        List<Salle> salleList = salleService.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSalle.getCoutParJour()).isEqualTo(UPDATED_COUT_PAR_JOUR);
        assertThat(testSalle.getCapacite()).isEqualTo(UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    public void updateNonExistingSalle() throws Exception {
        int databaseSizeBeforeUpdate = salleService.findAll().size();

        // Create the Salle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalleMockMvc.perform(put("/api/salles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salle)))
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleService.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalle() throws Exception {
        // Initialize the database
        salleService.saveAndFlush(salle);

        int databaseSizeBeforeDelete = salleService.findAll().size();

        // Get the salle
        restSalleMockMvc.perform(delete("/api/salles/{id}", salle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Salle> salleList = salleService.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Salle.class);
        Salle salle1 = new Salle();
        salle1.setId(1L);
        Salle salle2 = new Salle();
        salle2.setId(salle1.getId());
        assertThat(salle1).isEqualTo(salle2);
        salle2.setId(2L);
        assertThat(salle1).isNotEqualTo(salle2);
        salle1.setId(null);
        assertThat(salle1).isNotEqualTo(salle2);
    }
}
