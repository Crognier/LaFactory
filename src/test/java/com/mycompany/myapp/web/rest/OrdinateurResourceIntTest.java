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
import com.mycompany.myapp.domain.Ordinateur;
import com.mycompany.myapp.service.OrdinateurService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the OrdinateurResource REST controller.
 *
 * @see OrdinateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class OrdinateurResourceIntTest {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final Double DEFAULT_COUT_PAR_JOUR = 1D;
    private static final Double UPDATED_COUT_PAR_JOUR = 2D;

    private static final String DEFAULT_PROCESSEUR = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSEUR = "BBBBBBBBBB";

    private static final Integer DEFAULT_RAM = 1;
    private static final Integer UPDATED_RAM = 2;

    private static final Integer DEFAULT_HDD = 1;
    private static final Integer UPDATED_HDD = 2;

    private static final LocalDate DEFAULT_ACHAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACHAT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrdinateurService ordinateurService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdinateurMockMvc;

    private Ordinateur ordinateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdinateurResource ordinateurResource = new OrdinateurResource(ordinateurService);
        this.restOrdinateurMockMvc = MockMvcBuilders.standaloneSetup(ordinateurResource)
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
    public static Ordinateur createEntity(EntityManager em) {
        Ordinateur ordinateur = new Ordinateur()
            .code(DEFAULT_CODE)
            .coutParJour(DEFAULT_COUT_PAR_JOUR)
            .processeur(DEFAULT_PROCESSEUR)
            .ram(DEFAULT_RAM)
            .hdd(DEFAULT_HDD)
            .achat(DEFAULT_ACHAT);
        return ordinateur;
    }

    @Before
    public void initTest() {
        ordinateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdinateur() throws Exception {
        int databaseSizeBeforeCreate = ordinateurService.findAll().size();

        // Create the Ordinateur
        restOrdinateurMockMvc.perform(post("/api/ordinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinateur)))
            .andExpect(status().isCreated());

        // Validate the Ordinateur in the database
        List<Ordinateur> ordinateurList = ordinateurService.findAll();
        assertThat(ordinateurList).hasSize(databaseSizeBeforeCreate + 1);
        Ordinateur testOrdinateur = ordinateurList.get(ordinateurList.size() - 1);
        assertThat(testOrdinateur.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOrdinateur.getCoutParJour()).isEqualTo(DEFAULT_COUT_PAR_JOUR);
        assertThat(testOrdinateur.getProcesseur()).isEqualTo(DEFAULT_PROCESSEUR);
        assertThat(testOrdinateur.getRam()).isEqualTo(DEFAULT_RAM);
        assertThat(testOrdinateur.getHdd()).isEqualTo(DEFAULT_HDD);
        assertThat(testOrdinateur.getAchat()).isEqualTo(DEFAULT_ACHAT);
    }

    @Test
    @Transactional
    public void createOrdinateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordinateurService.findAll().size();

        // Create the Ordinateur with an existing ID
        ordinateur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdinateurMockMvc.perform(post("/api/ordinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinateur)))
            .andExpect(status().isBadRequest());

        // Validate the Ordinateur in the database
        List<Ordinateur> ordinateurList = ordinateurService.findAll();
        assertThat(ordinateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrdinateurs() throws Exception {
        // Initialize the database
        ordinateurService.saveAndFlush(ordinateur);

        // Get all the ordinateurList
        restOrdinateurMockMvc.perform(get("/api/ordinateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordinateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].coutParJour").value(hasItem(DEFAULT_COUT_PAR_JOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].processeur").value(hasItem(DEFAULT_PROCESSEUR.toString())))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM)))
            .andExpect(jsonPath("$.[*].hdd").value(hasItem(DEFAULT_HDD)))
            .andExpect(jsonPath("$.[*].achat").value(hasItem(DEFAULT_ACHAT.toString())));
    }
    
    @Test
    @Transactional
    public void getOrdinateur() throws Exception {
        // Initialize the database
        ordinateurService.saveAndFlush(ordinateur);

        // Get the ordinateur
        restOrdinateurMockMvc.perform(get("/api/ordinateurs/{id}", ordinateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordinateur.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.coutParJour").value(DEFAULT_COUT_PAR_JOUR.doubleValue()))
            .andExpect(jsonPath("$.processeur").value(DEFAULT_PROCESSEUR.toString()))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM))
            .andExpect(jsonPath("$.hdd").value(DEFAULT_HDD))
            .andExpect(jsonPath("$.achat").value(DEFAULT_ACHAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdinateur() throws Exception {
        // Get the ordinateur
        restOrdinateurMockMvc.perform(get("/api/ordinateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdinateur() throws Exception {
        // Initialize the database
        ordinateurService.saveAndFlush(ordinateur);

        int databaseSizeBeforeUpdate = ordinateurService.findAll().size();

        // Update the ordinateur
        Ordinateur updatedOrdinateur = ordinateurService.findById(ordinateur.getId()).get();
        // Disconnect from session so that the updates on updatedOrdinateur are not directly saved in db
        em.detach(updatedOrdinateur);
        updatedOrdinateur
            .code(UPDATED_CODE)
            .coutParJour(UPDATED_COUT_PAR_JOUR)
            .processeur(UPDATED_PROCESSEUR)
            .ram(UPDATED_RAM)
            .hdd(UPDATED_HDD)
            .achat(UPDATED_ACHAT);

        restOrdinateurMockMvc.perform(put("/api/ordinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdinateur)))
            .andExpect(status().isOk());

        // Validate the Ordinateur in the database
        List<Ordinateur> ordinateurList = ordinateurService.findAll();
        assertThat(ordinateurList).hasSize(databaseSizeBeforeUpdate);
        Ordinateur testOrdinateur = ordinateurList.get(ordinateurList.size() - 1);
        assertThat(testOrdinateur.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOrdinateur.getCoutParJour()).isEqualTo(UPDATED_COUT_PAR_JOUR);
        assertThat(testOrdinateur.getProcesseur()).isEqualTo(UPDATED_PROCESSEUR);
        assertThat(testOrdinateur.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testOrdinateur.getHdd()).isEqualTo(UPDATED_HDD);
        assertThat(testOrdinateur.getAchat()).isEqualTo(UPDATED_ACHAT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdinateur() throws Exception {
        int databaseSizeBeforeUpdate = ordinateurService.findAll().size();

        // Create the Ordinateur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdinateurMockMvc.perform(put("/api/ordinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordinateur)))
            .andExpect(status().isBadRequest());

        // Validate the Ordinateur in the database
        List<Ordinateur> ordinateurList = ordinateurService.findAll();
        assertThat(ordinateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrdinateur() throws Exception {
        // Initialize the database
        ordinateurService.saveAndFlush(ordinateur);

        int databaseSizeBeforeDelete = ordinateurService.findAll().size();

        // Get the ordinateur
        restOrdinateurMockMvc.perform(delete("/api/ordinateurs/{id}", ordinateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ordinateur> ordinateurList = ordinateurService.findAll();
        assertThat(ordinateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ordinateur.class);
        Ordinateur ordinateur1 = new Ordinateur();
        ordinateur1.setId(1L);
        Ordinateur ordinateur2 = new Ordinateur();
        ordinateur2.setId(ordinateur1.getId());
        assertThat(ordinateur1).isEqualTo(ordinateur2);
        ordinateur2.setId(2L);
        assertThat(ordinateur1).isNotEqualTo(ordinateur2);
        ordinateur1.setId(null);
        assertThat(ordinateur1).isNotEqualTo(ordinateur2);
    }
}
