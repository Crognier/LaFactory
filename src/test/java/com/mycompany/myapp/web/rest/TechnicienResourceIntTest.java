package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.LaFactoryApp;

import com.mycompany.myapp.domain.Technicien;
import com.mycompany.myapp.repository.TechnicienRepository;
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
 * Test class for the TechnicienResource REST controller.
 *
 * @see TechnicienResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class TechnicienResourceIntTest {

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
    private TechnicienRepository technicienRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTechnicienMockMvc;

    private Technicien technicien;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechnicienResource technicienResource = new TechnicienResource(technicienRepository);
        this.restTechnicienMockMvc = MockMvcBuilders.standaloneSetup(technicienResource)
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
    public static Technicien createEntity(EntityManager em) {
        Technicien technicien = new Technicien()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numero(DEFAULT_NUMERO)
            .rue(DEFAULT_RUE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .numeroTelephone(DEFAULT_NUMERO_TELEPHONE)
            .eMail(DEFAULT_E_MAIL);
        return technicien;
    }

    @Before
    public void initTest() {
        technicien = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechnicien() throws Exception {
        int databaseSizeBeforeCreate = technicienRepository.findAll().size();

        // Create the Technicien
        restTechnicienMockMvc.perform(post("/api/techniciens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technicien)))
            .andExpect(status().isCreated());

        // Validate the Technicien in the database
        List<Technicien> technicienList = technicienRepository.findAll();
        assertThat(technicienList).hasSize(databaseSizeBeforeCreate + 1);
        Technicien testTechnicien = technicienList.get(technicienList.size() - 1);
        assertThat(testTechnicien.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTechnicien.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testTechnicien.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTechnicien.getRue()).isEqualTo(DEFAULT_RUE);
        assertThat(testTechnicien.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testTechnicien.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testTechnicien.getNumeroTelephone()).isEqualTo(DEFAULT_NUMERO_TELEPHONE);
        assertThat(testTechnicien.geteMail()).isEqualTo(DEFAULT_E_MAIL);
    }

    @Test
    @Transactional
    public void createTechnicienWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = technicienRepository.findAll().size();

        // Create the Technicien with an existing ID
        technicien.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechnicienMockMvc.perform(post("/api/techniciens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technicien)))
            .andExpect(status().isBadRequest());

        // Validate the Technicien in the database
        List<Technicien> technicienList = technicienRepository.findAll();
        assertThat(technicienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTechniciens() throws Exception {
        // Initialize the database
        technicienRepository.saveAndFlush(technicien);

        // Get all the technicienList
        restTechnicienMockMvc.perform(get("/api/techniciens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technicien.getId().intValue())))
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
    public void getTechnicien() throws Exception {
        // Initialize the database
        technicienRepository.saveAndFlush(technicien);

        // Get the technicien
        restTechnicienMockMvc.perform(get("/api/techniciens/{id}", technicien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(technicien.getId().intValue()))
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
    public void getNonExistingTechnicien() throws Exception {
        // Get the technicien
        restTechnicienMockMvc.perform(get("/api/techniciens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnicien() throws Exception {
        // Initialize the database
        technicienRepository.saveAndFlush(technicien);

        int databaseSizeBeforeUpdate = technicienRepository.findAll().size();

        // Update the technicien
        Technicien updatedTechnicien = technicienRepository.findById(technicien.getId()).get();
        // Disconnect from session so that the updates on updatedTechnicien are not directly saved in db
        em.detach(updatedTechnicien);
        updatedTechnicien
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numero(UPDATED_NUMERO)
            .rue(UPDATED_RUE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .eMail(UPDATED_E_MAIL);

        restTechnicienMockMvc.perform(put("/api/techniciens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTechnicien)))
            .andExpect(status().isOk());

        // Validate the Technicien in the database
        List<Technicien> technicienList = technicienRepository.findAll();
        assertThat(technicienList).hasSize(databaseSizeBeforeUpdate);
        Technicien testTechnicien = technicienList.get(technicienList.size() - 1);
        assertThat(testTechnicien.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTechnicien.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testTechnicien.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTechnicien.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testTechnicien.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testTechnicien.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testTechnicien.getNumeroTelephone()).isEqualTo(UPDATED_NUMERO_TELEPHONE);
        assertThat(testTechnicien.geteMail()).isEqualTo(UPDATED_E_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingTechnicien() throws Exception {
        int databaseSizeBeforeUpdate = technicienRepository.findAll().size();

        // Create the Technicien

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnicienMockMvc.perform(put("/api/techniciens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technicien)))
            .andExpect(status().isBadRequest());

        // Validate the Technicien in the database
        List<Technicien> technicienList = technicienRepository.findAll();
        assertThat(technicienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechnicien() throws Exception {
        // Initialize the database
        technicienRepository.saveAndFlush(technicien);

        int databaseSizeBeforeDelete = technicienRepository.findAll().size();

        // Get the technicien
        restTechnicienMockMvc.perform(delete("/api/techniciens/{id}", technicien.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Technicien> technicienList = technicienRepository.findAll();
        assertThat(technicienList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Technicien.class);
        Technicien technicien1 = new Technicien();
        technicien1.setId(1L);
        Technicien technicien2 = new Technicien();
        technicien2.setId(technicien1.getId());
        assertThat(technicien1).isEqualTo(technicien2);
        technicien2.setId(2L);
        assertThat(technicien1).isNotEqualTo(technicien2);
        technicien1.setId(null);
        assertThat(technicien1).isNotEqualTo(technicien2);
    }
}
