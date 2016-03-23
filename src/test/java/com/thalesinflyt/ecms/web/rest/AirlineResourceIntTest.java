package com.thalesinflyt.ecms.web.rest;

import com.thalesinflyt.ecms.Application;
import com.thalesinflyt.ecms.domain.Airline;
import com.thalesinflyt.ecms.repository.AirlineRepository;

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
 * Test class for the AirlineResource REST controller.
 *
 * @see AirlineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AirlineResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_ICAO_CODE = "AAAAA";
    private static final String UPDATED_ICAO_CODE = "BBBBB";

    @Inject
    private AirlineRepository airlineRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAirlineMockMvc;

    private Airline airline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AirlineResource airlineResource = new AirlineResource();
        ReflectionTestUtils.setField(airlineResource, "airlineRepository", airlineRepository);
        this.restAirlineMockMvc = MockMvcBuilders.standaloneSetup(airlineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        airlineRepository.deleteAll();
        airline = new Airline();
        airline.setName(DEFAULT_NAME);
        airline.setIcaoCode(DEFAULT_ICAO_CODE);
    }

    @Test
    public void createAirline() throws Exception {
        int databaseSizeBeforeCreate = airlineRepository.findAll().size();

        // Create the Airline

        restAirlineMockMvc.perform(post("/api/airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(airline)))
                .andExpect(status().isCreated());

        // Validate the Airline in the database
        List<Airline> airlines = airlineRepository.findAll();
        assertThat(airlines).hasSize(databaseSizeBeforeCreate + 1);
        Airline testAirline = airlines.get(airlines.size() - 1);
        assertThat(testAirline.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAirline.getIcaoCode()).isEqualTo(DEFAULT_ICAO_CODE);
    }

    @Test
    public void getAllAirlines() throws Exception {
        // Initialize the database
        airlineRepository.save(airline);

        // Get all the airlines
        restAirlineMockMvc.perform(get("/api/airlines?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(airline.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].icaoCode").value(hasItem(DEFAULT_ICAO_CODE.toString())));
    }

    @Test
    public void getAirline() throws Exception {
        // Initialize the database
        airlineRepository.save(airline);

        // Get the airline
        restAirlineMockMvc.perform(get("/api/airlines/{id}", airline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(airline.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.icaoCode").value(DEFAULT_ICAO_CODE.toString()));
    }

    @Test
    public void getNonExistingAirline() throws Exception {
        // Get the airline
        restAirlineMockMvc.perform(get("/api/airlines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAirline() throws Exception {
        // Initialize the database
        airlineRepository.save(airline);

		int databaseSizeBeforeUpdate = airlineRepository.findAll().size();

        // Update the airline
        airline.setName(UPDATED_NAME);
        airline.setIcaoCode(UPDATED_ICAO_CODE);

        restAirlineMockMvc.perform(put("/api/airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(airline)))
                .andExpect(status().isOk());

        // Validate the Airline in the database
        List<Airline> airlines = airlineRepository.findAll();
        assertThat(airlines).hasSize(databaseSizeBeforeUpdate);
        Airline testAirline = airlines.get(airlines.size() - 1);
        assertThat(testAirline.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAirline.getIcaoCode()).isEqualTo(UPDATED_ICAO_CODE);
    }

    @Test
    public void deleteAirline() throws Exception {
        // Initialize the database
        airlineRepository.save(airline);

		int databaseSizeBeforeDelete = airlineRepository.findAll().size();

        // Get the airline
        restAirlineMockMvc.perform(delete("/api/airlines/{id}", airline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Airline> airlines = airlineRepository.findAll();
        assertThat(airlines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
