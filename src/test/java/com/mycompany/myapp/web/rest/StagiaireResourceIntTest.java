package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.LaFactoryApp;

import com.mycompany.myapp.domain.Stagiaire;
import com.mycompany.myapp.repository.StagiaireRepository;
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

import com.mycompany.myapp.domain.enumeration.Niveau;
/**
 * Test class for the StagiaireResource REST controller.
 *
 * @see StagiaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class StagiaireResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_RUE = "AAAAAAAAAA";
    private static final String UPDATED_RUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE_POSTAL = 1;
    private static final Integer UPDATED_CODE_POSTAL = 2;

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_TELEPHONE = 1;
    private static final Integer UPDATED_NUMERO_TELEPHONE = 2;

    private static final String DEFAULT_E_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_E_MAIL = "BBBBBBBBBB";

    private static final Niveau DEFAULT_NIVEAU = Niveau.DEBUTANT;
    private static final Niveau UPDATED_NIVEAU = Niveau.INTERMEDIAIRE;

    @Autowired
    private StagiaireRepository stagiaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStagiaireMockMvc;

    private Stagiaire stagiaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StagiaireResource stagiaireResource = new StagiaireResource(stagiaireRepository);
        this.restStagiaireMockMvc = MockMvcBuilders.standaloneSetup(stagiaireResource)
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
    public static Stagiaire createEntity(EntityManager em) {
        Stagiaire stagiaire = new Stagiaire()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numero(DEFAULT_NUMERO)
            .rue(DEFAULT_RUE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .numeroTelephone(DEFAULT_NUMERO_TELEPHONE)
            .eMail(DEFAULT_E_MAIL)
            .niveau(DEFAULT_NIVEAU);
        return stagiaire;
    }

    @Before
    public void initTest() {
        stagiaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createStagiaire() throws Exception {
        int databaseSizeBeforeCreate = stagiaireRepository.findAll().size();

        // Create the Stagiaire
        restStagiaireMockMvc.perform(post("/api/stagiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stagiaire)))
            .andExpect(status().isCreated());

        // Validate the Stagiaire in the database
        List<Stagiaire> stagiaireList = stagiaireRepository.findAll();
        assertThat(stagiaireList).hasSize(databaseSizeBeforeCreate + 1);
        Stagiaire testStagiaire = stagiaireList.get(stagiaireList.size() - 1);
        assertThat(testStagiaire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testStagiaire.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testStagiaire.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testStagiaire.getRue()).isEqualTo(DEFAULT_RUE);
        assertThat(testStagiaire.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testStagiaire.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testStagiaire.getNumeroTelephone()).isEqualTo(DEFAULT_NUMERO_TELEPHONE);
        assertThat(testStagiaire.geteMail()).isEqualTo(DEFAULT_E_MAIL);
        assertThat(testStagiaire.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
    }

    @Test
    @Transactional
    public void createStagiaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stagiaireRepository.findAll().size();

        // Create the Stagiaire with an existing ID
        stagiaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStagiaireMockMvc.perform(post("/api/stagiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stagiaire)))
            .andExpect(status().isBadRequest());

        // Validate the Stagiaire in the database
        List<Stagiaire> stagiaireList = stagiaireRepository.findAll();
        assertThat(stagiaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStagiaires() throws Exception {
        // Initialize the database
        stagiaireRepository.saveAndFlush(stagiaire);

        // Get all the stagiaireList
        restStagiaireMockMvc.perform(get("/api/stagiaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stagiaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].rue").value(hasItem(DEFAULT_RUE.toString())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].numeroTelephone").value(hasItem(DEFAULT_NUMERO_TELEPHONE)))
            .andExpect(jsonPath("$.[*].eMail").value(hasItem(DEFAULT_E_MAIL.toString())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())));
    }
    
    @Test
    @Transactional
    public void getStagiaire() throws Exception {
        // Initialize the database
        stagiaireRepository.saveAndFlush(stagiaire);

        // Get the stagiaire
        restStagiaireMockMvc.perform(get("/api/stagiaires/{id}", stagiaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stagiaire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.rue").value(DEFAULT_RUE.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.numeroTelephone").value(DEFAULT_NUMERO_TELEPHONE))
            .andExpect(jsonPath("$.eMail").value(DEFAULT_E_MAIL.toString()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStagiaire() throws Exception {
        // Get the stagiaire
        restStagiaireMockMvc.perform(get("/api/stagiaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStagiaire() throws Exception {
        // Initialize the database
        stagiaireRepository.saveAndFlush(stagiaire);

        int databaseSizeBeforeUpdate = stagiaireRepository.findAll().size();

        // Update the stagiaire
        Stagiaire updatedStagiaire = stagiaireRepository.findById(stagiaire.getId()).get();
        // Disconnect from session so that the updates on updatedStagiaire are not directly saved in db
        em.detach(updatedStagiaire);
        updatedStagiaire
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numero(UPDATED_NUMERO)
            .rue(UPDATED_RUE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .eMail(UPDATED_E_MAIL)
            .niveau(UPDATED_NIVEAU);

        restStagiaireMockMvc.perform(put("/api/stagiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStagiaire)))
            .andExpect(status().isOk());

        // Validate the Stagiaire in the database
        List<Stagiaire> stagiaireList = stagiaireRepository.findAll();
        assertThat(stagiaireList).hasSize(databaseSizeBeforeUpdate);
        Stagiaire testStagiaire = stagiaireList.get(stagiaireList.size() - 1);
        assertThat(testStagiaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testStagiaire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testStagiaire.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testStagiaire.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testStagiaire.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testStagiaire.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testStagiaire.getNumeroTelephone()).isEqualTo(UPDATED_NUMERO_TELEPHONE);
        assertThat(testStagiaire.geteMail()).isEqualTo(UPDATED_E_MAIL);
        assertThat(testStagiaire.getNiveau()).isEqualTo(UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    public void updateNonExistingStagiaire() throws Exception {
        int databaseSizeBeforeUpdate = stagiaireRepository.findAll().size();

        // Create the Stagiaire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStagiaireMockMvc.perform(put("/api/stagiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stagiaire)))
            .andExpect(status().isBadRequest());

        // Validate the Stagiaire in the database
        List<Stagiaire> stagiaireList = stagiaireRepository.findAll();
        assertThat(stagiaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStagiaire() throws Exception {
        // Initialize the database
        stagiaireRepository.saveAndFlush(stagiaire);

        int databaseSizeBeforeDelete = stagiaireRepository.findAll().size();

        // Get the stagiaire
        restStagiaireMockMvc.perform(delete("/api/stagiaires/{id}", stagiaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stagiaire> stagiaireList = stagiaireRepository.findAll();
        assertThat(stagiaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stagiaire.class);
        Stagiaire stagiaire1 = new Stagiaire();
        stagiaire1.setId(1L);
        Stagiaire stagiaire2 = new Stagiaire();
        stagiaire2.setId(stagiaire1.getId());
        assertThat(stagiaire1).isEqualTo(stagiaire2);
        stagiaire2.setId(2L);
        assertThat(stagiaire1).isNotEqualTo(stagiaire2);
        stagiaire1.setId(null);
        assertThat(stagiaire1).isNotEqualTo(stagiaire2);
    }
}
