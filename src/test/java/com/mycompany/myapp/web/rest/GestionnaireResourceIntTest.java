package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.LaFactoryApp;

import com.mycompany.myapp.domain.Gestionnaire;
import com.mycompany.myapp.repository.GestionnaireRepository;
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
 * Test class for the GestionnaireResource REST controller.
 *
 * @see GestionnaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class GestionnaireResourceIntTest {

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

    @Autowired
    private GestionnaireRepository gestionnaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGestionnaireMockMvc;

    private Gestionnaire gestionnaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GestionnaireResource gestionnaireResource = new GestionnaireResource(gestionnaireRepository);
        this.restGestionnaireMockMvc = MockMvcBuilders.standaloneSetup(gestionnaireResource)
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
    public static Gestionnaire createEntity(EntityManager em) {
        Gestionnaire gestionnaire = new Gestionnaire()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numero(DEFAULT_NUMERO)
            .rue(DEFAULT_RUE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .numeroTelephone(DEFAULT_NUMERO_TELEPHONE)
            .eMail(DEFAULT_E_MAIL);
        return gestionnaire;
    }

    @Before
    public void initTest() {
        gestionnaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createGestionnaire() throws Exception {
        int databaseSizeBeforeCreate = gestionnaireRepository.findAll().size();

        // Create the Gestionnaire
        restGestionnaireMockMvc.perform(post("/api/gestionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gestionnaire)))
            .andExpect(status().isCreated());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeCreate + 1);
        Gestionnaire testGestionnaire = gestionnaireList.get(gestionnaireList.size() - 1);
        assertThat(testGestionnaire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testGestionnaire.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testGestionnaire.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testGestionnaire.getRue()).isEqualTo(DEFAULT_RUE);
        assertThat(testGestionnaire.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testGestionnaire.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testGestionnaire.getNumeroTelephone()).isEqualTo(DEFAULT_NUMERO_TELEPHONE);
        assertThat(testGestionnaire.geteMail()).isEqualTo(DEFAULT_E_MAIL);
    }

    @Test
    @Transactional
    public void createGestionnaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gestionnaireRepository.findAll().size();

        // Create the Gestionnaire with an existing ID
        gestionnaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGestionnaireMockMvc.perform(post("/api/gestionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gestionnaire)))
            .andExpect(status().isBadRequest());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGestionnaires() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        // Get all the gestionnaireList
        restGestionnaireMockMvc.perform(get("/api/gestionnaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestionnaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].rue").value(hasItem(DEFAULT_RUE.toString())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].numeroTelephone").value(hasItem(DEFAULT_NUMERO_TELEPHONE)))
            .andExpect(jsonPath("$.[*].eMail").value(hasItem(DEFAULT_E_MAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getGestionnaire() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        // Get the gestionnaire
        restGestionnaireMockMvc.perform(get("/api/gestionnaires/{id}", gestionnaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gestionnaire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.rue").value(DEFAULT_RUE.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.numeroTelephone").value(DEFAULT_NUMERO_TELEPHONE))
            .andExpect(jsonPath("$.eMail").value(DEFAULT_E_MAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGestionnaire() throws Exception {
        // Get the gestionnaire
        restGestionnaireMockMvc.perform(get("/api/gestionnaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGestionnaire() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();

        // Update the gestionnaire
        Gestionnaire updatedGestionnaire = gestionnaireRepository.findById(gestionnaire.getId()).get();
        // Disconnect from session so that the updates on updatedGestionnaire are not directly saved in db
        em.detach(updatedGestionnaire);
        updatedGestionnaire
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numero(UPDATED_NUMERO)
            .rue(UPDATED_RUE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .eMail(UPDATED_E_MAIL);

        restGestionnaireMockMvc.perform(put("/api/gestionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGestionnaire)))
            .andExpect(status().isOk());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
        Gestionnaire testGestionnaire = gestionnaireList.get(gestionnaireList.size() - 1);
        assertThat(testGestionnaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testGestionnaire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testGestionnaire.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testGestionnaire.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testGestionnaire.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testGestionnaire.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testGestionnaire.getNumeroTelephone()).isEqualTo(UPDATED_NUMERO_TELEPHONE);
        assertThat(testGestionnaire.geteMail()).isEqualTo(UPDATED_E_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingGestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();

        // Create the Gestionnaire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestionnaireMockMvc.perform(put("/api/gestionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gestionnaire)))
            .andExpect(status().isBadRequest());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGestionnaire() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        int databaseSizeBeforeDelete = gestionnaireRepository.findAll().size();

        // Get the gestionnaire
        restGestionnaireMockMvc.perform(delete("/api/gestionnaires/{id}", gestionnaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gestionnaire.class);
        Gestionnaire gestionnaire1 = new Gestionnaire();
        gestionnaire1.setId(1L);
        Gestionnaire gestionnaire2 = new Gestionnaire();
        gestionnaire2.setId(gestionnaire1.getId());
        assertThat(gestionnaire1).isEqualTo(gestionnaire2);
        gestionnaire2.setId(2L);
        assertThat(gestionnaire1).isNotEqualTo(gestionnaire2);
        gestionnaire1.setId(null);
        assertThat(gestionnaire1).isNotEqualTo(gestionnaire2);
    }
}
