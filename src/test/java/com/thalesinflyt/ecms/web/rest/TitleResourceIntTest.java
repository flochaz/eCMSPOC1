package com.thalesinflyt.ecms.web.rest;

import com.thalesinflyt.ecms.Application;
import com.thalesinflyt.ecms.domain.Title;
import com.thalesinflyt.ecms.repository.TitleRepository;
import com.thalesinflyt.ecms.service.TitleService;

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


/**
 * Test class for the TitleResource REST controller.
 *
 * @see TitleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TitleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_EXHIBITION_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXHIBITION_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXHIBITION_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXHIBITION_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LATEST_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LATEST_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private TitleRepository titleRepository;

    @Inject
    private TitleService titleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTitleMockMvc;

    private Title title;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TitleResource titleResource = new TitleResource();
        ReflectionTestUtils.setField(titleResource, "titleService", titleService);
        this.restTitleMockMvc = MockMvcBuilders.standaloneSetup(titleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        titleRepository.deleteAll();
        title = new Title();
        title.setName(DEFAULT_NAME);
        title.setExhibitionStartDate(DEFAULT_EXHIBITION_START_DATE);
        title.setExhibitionEndDate(DEFAULT_EXHIBITION_END_DATE);
        title.setCreationDate(DEFAULT_CREATION_DATE);
        title.setLatestModifiedDate(DEFAULT_LATEST_MODIFIED_DATE);
    }

    @Test
    public void createTitle() throws Exception {
        int databaseSizeBeforeCreate = titleRepository.findAll().size();

        // Create the Title

        restTitleMockMvc.perform(post("/api/titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(title)))
                .andExpect(status().isCreated());

        // Validate the Title in the database
        List<Title> titles = titleRepository.findAll();
        assertThat(titles).hasSize(databaseSizeBeforeCreate + 1);
        Title testTitle = titles.get(titles.size() - 1);
        assertThat(testTitle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTitle.getExhibitionStartDate()).isEqualTo(DEFAULT_EXHIBITION_START_DATE);
        assertThat(testTitle.getExhibitionEndDate()).isEqualTo(DEFAULT_EXHIBITION_END_DATE);
        assertThat(testTitle.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTitle.getLatestModifiedDate()).isEqualTo(DEFAULT_LATEST_MODIFIED_DATE);
    }

    @Test
    public void getAllTitles() throws Exception {
        // Initialize the database
        titleRepository.save(title);

        // Get all the titles
        restTitleMockMvc.perform(get("/api/titles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(title.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].exhibitionStartDate").value(hasItem(DEFAULT_EXHIBITION_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].exhibitionEndDate").value(hasItem(DEFAULT_EXHIBITION_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].latestModifiedDate").value(hasItem(DEFAULT_LATEST_MODIFIED_DATE.toString())));
    }

    @Test
    public void getTitle() throws Exception {
        // Initialize the database
        titleRepository.save(title);

        // Get the title
        restTitleMockMvc.perform(get("/api/titles/{id}", title.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(title.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.exhibitionStartDate").value(DEFAULT_EXHIBITION_START_DATE.toString()))
            .andExpect(jsonPath("$.exhibitionEndDate").value(DEFAULT_EXHIBITION_END_DATE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.latestModifiedDate").value(DEFAULT_LATEST_MODIFIED_DATE.toString()));
    }

    @Test
    public void getNonExistingTitle() throws Exception {
        // Get the title
        restTitleMockMvc.perform(get("/api/titles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateTitle() throws Exception {
        // Initialize the database
        titleRepository.save(title);

		int databaseSizeBeforeUpdate = titleRepository.findAll().size();

        // Update the title
        title.setName(UPDATED_NAME);
        title.setExhibitionStartDate(UPDATED_EXHIBITION_START_DATE);
        title.setExhibitionEndDate(UPDATED_EXHIBITION_END_DATE);
        title.setCreationDate(UPDATED_CREATION_DATE);
        title.setLatestModifiedDate(UPDATED_LATEST_MODIFIED_DATE);

        restTitleMockMvc.perform(put("/api/titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(title)))
                .andExpect(status().isOk());

        // Validate the Title in the database
        List<Title> titles = titleRepository.findAll();
        assertThat(titles).hasSize(databaseSizeBeforeUpdate);
        Title testTitle = titles.get(titles.size() - 1);
        assertThat(testTitle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTitle.getExhibitionStartDate()).isEqualTo(UPDATED_EXHIBITION_START_DATE);
        assertThat(testTitle.getExhibitionEndDate()).isEqualTo(UPDATED_EXHIBITION_END_DATE);
        assertThat(testTitle.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTitle.getLatestModifiedDate()).isEqualTo(UPDATED_LATEST_MODIFIED_DATE);
    }

    @Test
    public void deleteTitle() throws Exception {
        // Initialize the database
        titleRepository.save(title);

		int databaseSizeBeforeDelete = titleRepository.findAll().size();

        // Get the title
        restTitleMockMvc.perform(delete("/api/titles/{id}", title.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Title> titles = titleRepository.findAll();
        assertThat(titles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
