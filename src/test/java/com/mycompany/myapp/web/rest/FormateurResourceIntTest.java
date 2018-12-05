package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.LaFactoryApp;

import com.mycompany.myapp.domain.Formateur;
import com.mycompany.myapp.service.FormateurService;
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
 * Test class for the FormateurResource REST controller.
 *
 * @see FormateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class FormateurResourceIntTest {

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
    private FormateurService formateurService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormateurMockMvc;

    private Formateur formateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormateurResource formateurResource = new FormateurResource(formateurService);
        this.restFormateurMockMvc = MockMvcBuilders.standaloneSetup(formateurResource)
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
    public static Formateur createEntity(EntityManager em) {
        Formateur formateur = new Formateur()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numero(DEFAULT_NUMERO)
            .rue(DEFAULT_RUE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .numeroTelephone(DEFAULT_NUMERO_TELEPHONE)
            .eMail(DEFAULT_E_MAIL);
        return formateur;
    }

    @Before
    public void initTest() {
        formateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormateur() throws Exception {
        int databaseSizeBeforeCreate = formateurService.findAll().size();

        // Create the Formateur
        restFormateurMockMvc.perform(post("/api/formateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formateur)))
            .andExpect(status().isCreated());

        // Validate the Formateur in the database
        List<Formateur> formateurList = formateurService.findAll();
        assertThat(formateurList).hasSize(databaseSizeBeforeCreate + 1);
        Formateur testFormateur = formateurList.get(formateurList.size() - 1);
        assertThat(testFormateur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFormateur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testFormateur.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testFormateur.getRue()).isEqualTo(DEFAULT_RUE);
        assertThat(testFormateur.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testFormateur.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testFormateur.getNumeroTelephone()).isEqualTo(DEFAULT_NUMERO_TELEPHONE);
        assertThat(testFormateur.geteMail()).isEqualTo(DEFAULT_E_MAIL);
    }

    @Test
    @Transactional
    public void createFormateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formateurService.findAll().size();

        // Create the Formateur with an existing ID
        formateur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormateurMockMvc.perform(post("/api/formateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formateur)))
            .andExpect(status().isBadRequest());

        // Validate the Formateur in the database
        List<Formateur> formateurList = formateurService.findAll();
        assertThat(formateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormateurs() throws Exception {
        // Initialize the database
        formateurService.saveAndFlush(formateur);

        // Get all the formateurList
        restFormateurMockMvc.perform(get("/api/formateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formateur.getId().intValue())))
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
    public void getFormateur() throws Exception {
        // Initialize the database
        formateurService.saveAndFlush(formateur);

        // Get the formateur
        restFormateurMockMvc.perform(get("/api/formateurs/{id}", formateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formateur.getId().intValue()))
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
    public void getNonExistingFormateur() throws Exception {
        // Get the formateur
        restFormateurMockMvc.perform(get("/api/formateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormateur() throws Exception {
        // Initialize the database
        formateurService.saveAndFlush(formateur);

        int databaseSizeBeforeUpdate = formateurService.findAll().size();

        // Update the formateur
        Formateur updatedFormateur = formateurService.findById(formateur.getId()).get();
        // Disconnect from session so that the updates on updatedFormateur are not directly saved in db
        em.detach(updatedFormateur);
        updatedFormateur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numero(UPDATED_NUMERO)
            .rue(UPDATED_RUE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .eMail(UPDATED_E_MAIL);

        restFormateurMockMvc.perform(put("/api/formateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormateur)))
            .andExpect(status().isOk());

        // Validate the Formateur in the database
        List<Formateur> formateurList = formateurService.findAll();
        assertThat(formateurList).hasSize(databaseSizeBeforeUpdate);
        Formateur testFormateur = formateurList.get(formateurList.size() - 1);
        assertThat(testFormateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFormateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testFormateur.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testFormateur.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testFormateur.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testFormateur.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testFormateur.getNumeroTelephone()).isEqualTo(UPDATED_NUMERO_TELEPHONE);
        assertThat(testFormateur.geteMail()).isEqualTo(UPDATED_E_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingFormateur() throws Exception {
        int databaseSizeBeforeUpdate = formateurService.findAll().size();

        // Create the Formateur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormateurMockMvc.perform(put("/api/formateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formateur)))
            .andExpect(status().isBadRequest());

        // Validate the Formateur in the database
        List<Formateur> formateurList = formateurService.findAll();
        assertThat(formateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormateur() throws Exception {
        // Initialize the database
        formateurService.saveAndFlush(formateur);

        int databaseSizeBeforeDelete = formateurService.findAll().size();

        // Get the formateur
        restFormateurMockMvc.perform(delete("/api/formateurs/{id}", formateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Formateur> formateurList = formateurService.findAll();
        assertThat(formateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formateur.class);
        Formateur formateur1 = new Formateur();
        formateur1.setId(1L);
        Formateur formateur2 = new Formateur();
        formateur2.setId(formateur1.getId());
        assertThat(formateur1).isEqualTo(formateur2);
        formateur2.setId(2L);
        assertThat(formateur1).isNotEqualTo(formateur2);
        formateur1.setId(null);
        assertThat(formateur1).isNotEqualTo(formateur2);
    }
}
