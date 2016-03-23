package com.thalesinflyt.ecms.web.rest;

import com.thalesinflyt.ecms.Application;
import com.thalesinflyt.ecms.domain.ContentDefinition;
import com.thalesinflyt.ecms.repository.ContentDefinitionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ContentDefinitionResource REST controller.
 *
 * @see ContentDefinitionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContentDefinitionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Inject
    private ContentDefinitionRepository contentDefinitionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContentDefinitionMockMvc;

    private ContentDefinition contentDefinition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContentDefinitionResource contentDefinitionResource = new ContentDefinitionResource();
        ReflectionTestUtils.setField(contentDefinitionResource, "contentDefinitionRepository", contentDefinitionRepository);
        this.restContentDefinitionMockMvc = MockMvcBuilders.standaloneSetup(contentDefinitionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contentDefinitionRepository.deleteAll();
        contentDefinition = new ContentDefinition();
        contentDefinition.setName(DEFAULT_NAME);
        contentDefinition.setVersion(DEFAULT_VERSION);
    }

    @Test
    public void createContentDefinition() throws Exception {
        int databaseSizeBeforeCreate = contentDefinitionRepository.findAll().size();

        // Create the ContentDefinition

        restContentDefinitionMockMvc.perform(post("/api/contentDefinitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contentDefinition)))
                .andExpect(status().isCreated());

        // Validate the ContentDefinition in the database
        List<ContentDefinition> contentDefinitions = contentDefinitionRepository.findAll();
        assertThat(contentDefinitions).hasSize(databaseSizeBeforeCreate + 1);
        ContentDefinition testContentDefinition = contentDefinitions.get(contentDefinitions.size() - 1);
        assertThat(testContentDefinition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContentDefinition.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    public void getAllContentDefinitions() throws Exception {
        // Initialize the database
        contentDefinitionRepository.save(contentDefinition);

        // Get all the contentDefinitions
        restContentDefinitionMockMvc.perform(get("/api/contentDefinitions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contentDefinition.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    public void getContentDefinition() throws Exception {
        // Initialize the database
        contentDefinitionRepository.save(contentDefinition);

        // Get the contentDefinition
        restContentDefinitionMockMvc.perform(get("/api/contentDefinitions/{id}", contentDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contentDefinition.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    public void getNonExistingContentDefinition() throws Exception {
        // Get the contentDefinition
        restContentDefinitionMockMvc.perform(get("/api/contentDefinitions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateContentDefinition() throws Exception {
        // Initialize the database
        contentDefinitionRepository.save(contentDefinition);

		int databaseSizeBeforeUpdate = contentDefinitionRepository.findAll().size();

        // Update the contentDefinition
        contentDefinition.setName(UPDATED_NAME);
        contentDefinition.setVersion(UPDATED_VERSION);

        restContentDefinitionMockMvc.perform(put("/api/contentDefinitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contentDefinition)))
                .andExpect(status().isOk());

        // Validate the ContentDefinition in the database
        List<ContentDefinition> contentDefinitions = contentDefinitionRepository.findAll();
        assertThat(contentDefinitions).hasSize(databaseSizeBeforeUpdate);
        ContentDefinition testContentDefinition = contentDefinitions.get(contentDefinitions.size() - 1);
        assertThat(testContentDefinition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContentDefinition.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    public void deleteContentDefinition() throws Exception {
        // Initialize the database
        contentDefinitionRepository.save(contentDefinition);

		int databaseSizeBeforeDelete = contentDefinitionRepository.findAll().size();

        // Get the contentDefinition
        restContentDefinitionMockMvc.perform(delete("/api/contentDefinitions/{id}", contentDefinition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentDefinition> contentDefinitions = contentDefinitionRepository.findAll();
        assertThat(contentDefinitions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
