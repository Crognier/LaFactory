package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.LaFactoryApp;

import com.mycompany.myapp.domain.FormateurMatiere;
import com.mycompany.myapp.service.FormateurMatiereService;
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
 * Test class for the FormateurMatiereResource REST controller.
 *
 * @see FormateurMatiereResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaFactoryApp.class)
public class FormateurMatiereResourceIntTest {

    private static final Niveau DEFAULT_NIVEAU = Niveau.DEBUTANT;
    private static final Niveau UPDATED_NIVEAU = Niveau.INTERMEDIAIRE;

    @Autowired
    private FormateurMatiereService formateurMatiereService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormateurMatiereMockMvc;

    private FormateurMatiere formateurMatiere;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormateurMatiereResource formateurMatiereResource = new FormateurMatiereResource(formateurMatiereService);
        this.restFormateurMatiereMockMvc = MockMvcBuilders.standaloneSetup(formateurMatiereResource)
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
    public static FormateurMatiere createEntity(EntityManager em) {
        FormateurMatiere formateurMatiere = new FormateurMatiere()
            .niveau(DEFAULT_NIVEAU);
        return formateurMatiere;
    }

    @Before
    public void initTest() {
        formateurMatiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormateurMatiere() throws Exception {
        int databaseSizeBeforeCreate = formateurMatiereService.findAll().size();

        // Create the FormateurMatiere
        restFormateurMatiereMockMvc.perform(post("/api/formateur-matieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formateurMatiere)))
            .andExpect(status().isCreated());

        // Validate the FormateurMatiere in the database
        List<FormateurMatiere> formateurMatiereList = formateurMatiereService.findAll();
        assertThat(formateurMatiereList).hasSize(databaseSizeBeforeCreate + 1);
        FormateurMatiere testFormateurMatiere = formateurMatiereList.get(formateurMatiereList.size() - 1);
        assertThat(testFormateurMatiere.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
    }

    @Test
    @Transactional
    public void createFormateurMatiereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formateurMatiereService.findAll().size();

        // Create the FormateurMatiere with an existing ID
        formateurMatiere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormateurMatiereMockMvc.perform(post("/api/formateur-matieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formateurMatiere)))
            .andExpect(status().isBadRequest());

        // Validate the FormateurMatiere in the database
        List<FormateurMatiere> formateurMatiereList = formateurMatiereService.findAll();
        assertThat(formateurMatiereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormateurMatieres() throws Exception {
        // Initialize the database
        formateurMatiereService.saveAndFlush(formateurMatiere);

        // Get all the formateurMatiereList
        restFormateurMatiereMockMvc.perform(get("/api/formateur-matieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formateurMatiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())));
    }
    
    @Test
    @Transactional
    public void getFormateurMatiere() throws Exception {
        // Initialize the database
        formateurMatiereService.saveAndFlush(formateurMatiere);

        // Get the formateurMatiere
        restFormateurMatiereMockMvc.perform(get("/api/formateur-matieres/{id}", formateurMatiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formateurMatiere.getId().intValue()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormateurMatiere() throws Exception {
        // Get the formateurMatiere
        restFormateurMatiereMockMvc.perform(get("/api/formateur-matieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormateurMatiere() throws Exception {
        // Initialize the database
        formateurMatiereService.saveAndFlush(formateurMatiere);

        int databaseSizeBeforeUpdate = formateurMatiereService.findAll().size();

        // Update the formateurMatiere
        FormateurMatiere updatedFormateurMatiere = formateurMatiereService.findById(formateurMatiere.getId()).get();
        // Disconnect from session so that the updates on updatedFormateurMatiere are not directly saved in db
        em.detach(updatedFormateurMatiere);
        updatedFormateurMatiere
            .niveau(UPDATED_NIVEAU);

        restFormateurMatiereMockMvc.perform(put("/api/formateur-matieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormateurMatiere)))
            .andExpect(status().isOk());

        // Validate the FormateurMatiere in the database
        List<FormateurMatiere> formateurMatiereList = formateurMatiereService.findAll();
        assertThat(formateurMatiereList).hasSize(databaseSizeBeforeUpdate);
        FormateurMatiere testFormateurMatiere = formateurMatiereList.get(formateurMatiereList.size() - 1);
        assertThat(testFormateurMatiere.getNiveau()).isEqualTo(UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    public void updateNonExistingFormateurMatiere() throws Exception {
        int databaseSizeBeforeUpdate = formateurMatiereService.findAll().size();

        // Create the FormateurMatiere

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormateurMatiereMockMvc.perform(put("/api/formateur-matieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formateurMatiere)))
            .andExpect(status().isBadRequest());

        // Validate the FormateurMatiere in the database
        List<FormateurMatiere> formateurMatiereList = formateurMatiereService.findAll();
        assertThat(formateurMatiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormateurMatiere() throws Exception {
        // Initialize the database
        formateurMatiereService.saveAndFlush(formateurMatiere);

        int databaseSizeBeforeDelete = formateurMatiereService.findAll().size();

        // Get the formateurMatiere
        restFormateurMatiereMockMvc.perform(delete("/api/formateur-matieres/{id}", formateurMatiere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FormateurMatiere> formateurMatiereList = formateurMatiereService.findAll();
        assertThat(formateurMatiereList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormateurMatiere.class);
        FormateurMatiere formateurMatiere1 = new FormateurMatiere();
        formateurMatiere1.setId(1L);
        FormateurMatiere formateurMatiere2 = new FormateurMatiere();
        formateurMatiere2.setId(formateurMatiere1.getId());
        assertThat(formateurMatiere1).isEqualTo(formateurMatiere2);
        formateurMatiere2.setId(2L);
        assertThat(formateurMatiere1).isNotEqualTo(formateurMatiere2);
        formateurMatiere1.setId(null);
        assertThat(formateurMatiere1).isNotEqualTo(formateurMatiere2);
    }
}
