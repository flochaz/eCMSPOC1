package com.thalesinflyt.ecms.web.rest;

import com.thalesinflyt.ecms.Application;
import com.thalesinflyt.ecms.domain.Catalog;
import com.thalesinflyt.ecms.repository.CatalogRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thalesinflyt.ecms.domain.enumeration.CatalogStatus;

/**
 * Test class for the CatalogResource REST controller.
 *
 * @see CatalogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CatalogResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    
    private static final CatalogStatus DEFAULT_STATUS = CatalogStatus.INCOMPLETE;
    private static final CatalogStatus UPDATED_STATUS = CatalogStatus.PACKAGED;

    private static final LocalDate DEFAULT_LAST_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CATALOG_SHIPMENT_DEADLINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CATALOG_SHIPMENT_DEADLINE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private CatalogRepository catalogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCatalogMockMvc;

    private Catalog catalog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CatalogResource catalogResource = new CatalogResource();
        ReflectionTestUtils.setField(catalogResource, "catalogRepository", catalogRepository);
        this.restCatalogMockMvc = MockMvcBuilders.standaloneSetup(catalogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        catalogRepository.deleteAll();
        catalog = new Catalog();
        catalog.setName(DEFAULT_NAME);
        catalog.setStatus(DEFAULT_STATUS);
        catalog.setLastModified(DEFAULT_LAST_MODIFIED);
        catalog.setStartDate(DEFAULT_START_DATE);
        catalog.setEndDate(DEFAULT_END_DATE);
        catalog.setCatalogShipmentDeadline(DEFAULT_CATALOG_SHIPMENT_DEADLINE);
        catalog.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createCatalog() throws Exception {
        int databaseSizeBeforeCreate = catalogRepository.findAll().size();

        // Create the Catalog

        restCatalogMockMvc.perform(post("/api/catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalog)))
                .andExpect(status().isCreated());

        // Validate the Catalog in the database
        List<Catalog> catalogs = catalogRepository.findAll();
        assertThat(catalogs).hasSize(databaseSizeBeforeCreate + 1);
        Catalog testCatalog = catalogs.get(catalogs.size() - 1);
        assertThat(testCatalog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCatalog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCatalog.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testCatalog.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCatalog.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testCatalog.getCatalogShipmentDeadline()).isEqualTo(DEFAULT_CATALOG_SHIPMENT_DEADLINE);
        assertThat(testCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void getAllCatalogs() throws Exception {
        // Initialize the database
        catalogRepository.save(catalog);

        // Get all the catalogs
        restCatalogMockMvc.perform(get("/api/catalogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(catalog.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].catalogShipmentDeadline").value(hasItem(DEFAULT_CATALOG_SHIPMENT_DEADLINE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getCatalog() throws Exception {
        // Initialize the database
        catalogRepository.save(catalog);

        // Get the catalog
        restCatalogMockMvc.perform(get("/api/catalogs/{id}", catalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(catalog.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.catalogShipmentDeadline").value(DEFAULT_CATALOG_SHIPMENT_DEADLINE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingCatalog() throws Exception {
        // Get the catalog
        restCatalogMockMvc.perform(get("/api/catalogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCatalog() throws Exception {
        // Initialize the database
        catalogRepository.save(catalog);

		int databaseSizeBeforeUpdate = catalogRepository.findAll().size();

        // Update the catalog
        catalog.setName(UPDATED_NAME);
        catalog.setStatus(UPDATED_STATUS);
        catalog.setLastModified(UPDATED_LAST_MODIFIED);
        catalog.setStartDate(UPDATED_START_DATE);
        catalog.setEndDate(UPDATED_END_DATE);
        catalog.setCatalogShipmentDeadline(UPDATED_CATALOG_SHIPMENT_DEADLINE);
        catalog.setDescription(UPDATED_DESCRIPTION);

        restCatalogMockMvc.perform(put("/api/catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalog)))
                .andExpect(status().isOk());

        // Validate the Catalog in the database
        List<Catalog> catalogs = catalogRepository.findAll();
        assertThat(catalogs).hasSize(databaseSizeBeforeUpdate);
        Catalog testCatalog = catalogs.get(catalogs.size() - 1);
        assertThat(testCatalog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCatalog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCatalog.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCatalog.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCatalog.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testCatalog.getCatalogShipmentDeadline()).isEqualTo(UPDATED_CATALOG_SHIPMENT_DEADLINE);
        assertThat(testCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void deleteCatalog() throws Exception {
        // Initialize the database
        catalogRepository.save(catalog);

		int databaseSizeBeforeDelete = catalogRepository.findAll().size();

        // Get the catalog
        restCatalogMockMvc.perform(delete("/api/catalogs/{id}", catalog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Catalog> catalogs = catalogRepository.findAll();
        assertThat(catalogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
