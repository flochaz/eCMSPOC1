package com.thalesinflyt.ecms.web.rest;

import com.thalesinflyt.ecms.Application;
import com.thalesinflyt.ecms.domain.DeploymentGroup;
import com.thalesinflyt.ecms.repository.DeploymentGroupRepository;

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
 * Test class for the DeploymentGroupResource REST controller.
 *
 * @see DeploymentGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeploymentGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PACKAGING_TYPE = "AAAAA";
    private static final String UPDATED_PACKAGING_TYPE = "BBBBB";

    @Inject
    private DeploymentGroupRepository deploymentGroupRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeploymentGroupMockMvc;

    private DeploymentGroup deploymentGroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeploymentGroupResource deploymentGroupResource = new DeploymentGroupResource();
        ReflectionTestUtils.setField(deploymentGroupResource, "deploymentGroupRepository", deploymentGroupRepository);
        this.restDeploymentGroupMockMvc = MockMvcBuilders.standaloneSetup(deploymentGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        deploymentGroupRepository.deleteAll();
        deploymentGroup = new DeploymentGroup();
        deploymentGroup.setName(DEFAULT_NAME);
        deploymentGroup.setPackagingType(DEFAULT_PACKAGING_TYPE);
    }

    @Test
    public void createDeploymentGroup() throws Exception {
        int databaseSizeBeforeCreate = deploymentGroupRepository.findAll().size();

        // Create the DeploymentGroup

        restDeploymentGroupMockMvc.perform(post("/api/deploymentGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deploymentGroup)))
                .andExpect(status().isCreated());

        // Validate the DeploymentGroup in the database
        List<DeploymentGroup> deploymentGroups = deploymentGroupRepository.findAll();
        assertThat(deploymentGroups).hasSize(databaseSizeBeforeCreate + 1);
        DeploymentGroup testDeploymentGroup = deploymentGroups.get(deploymentGroups.size() - 1);
        assertThat(testDeploymentGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeploymentGroup.getPackagingType()).isEqualTo(DEFAULT_PACKAGING_TYPE);
    }

    @Test
    public void getAllDeploymentGroups() throws Exception {
        // Initialize the database
        deploymentGroupRepository.save(deploymentGroup);

        // Get all the deploymentGroups
        restDeploymentGroupMockMvc.perform(get("/api/deploymentGroups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(deploymentGroup.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].packagingType").value(hasItem(DEFAULT_PACKAGING_TYPE.toString())));
    }

    @Test
    public void getDeploymentGroup() throws Exception {
        // Initialize the database
        deploymentGroupRepository.save(deploymentGroup);

        // Get the deploymentGroup
        restDeploymentGroupMockMvc.perform(get("/api/deploymentGroups/{id}", deploymentGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(deploymentGroup.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.packagingType").value(DEFAULT_PACKAGING_TYPE.toString()));
    }

    @Test
    public void getNonExistingDeploymentGroup() throws Exception {
        // Get the deploymentGroup
        restDeploymentGroupMockMvc.perform(get("/api/deploymentGroups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDeploymentGroup() throws Exception {
        // Initialize the database
        deploymentGroupRepository.save(deploymentGroup);

		int databaseSizeBeforeUpdate = deploymentGroupRepository.findAll().size();

        // Update the deploymentGroup
        deploymentGroup.setName(UPDATED_NAME);
        deploymentGroup.setPackagingType(UPDATED_PACKAGING_TYPE);

        restDeploymentGroupMockMvc.perform(put("/api/deploymentGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deploymentGroup)))
                .andExpect(status().isOk());

        // Validate the DeploymentGroup in the database
        List<DeploymentGroup> deploymentGroups = deploymentGroupRepository.findAll();
        assertThat(deploymentGroups).hasSize(databaseSizeBeforeUpdate);
        DeploymentGroup testDeploymentGroup = deploymentGroups.get(deploymentGroups.size() - 1);
        assertThat(testDeploymentGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeploymentGroup.getPackagingType()).isEqualTo(UPDATED_PACKAGING_TYPE);
    }

    @Test
    public void deleteDeploymentGroup() throws Exception {
        // Initialize the database
        deploymentGroupRepository.save(deploymentGroup);

		int databaseSizeBeforeDelete = deploymentGroupRepository.findAll().size();

        // Get the deploymentGroup
        restDeploymentGroupMockMvc.perform(delete("/api/deploymentGroups/{id}", deploymentGroup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DeploymentGroup> deploymentGroups = deploymentGroupRepository.findAll();
        assertThat(deploymentGroups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
