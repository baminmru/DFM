package com.bami.dfm.v1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.dfm.v1.IntegrationTest;
import com.bami.dfm.v1.domain.DataForest;
import com.bami.dfm.v1.repository.DataForestRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DataForestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DataForestResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-forests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataForestRepository dataForestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataForestMockMvc;

    private DataForest dataForest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataForest createEntity(EntityManager em) {
        DataForest dataForest = new DataForest().name(DEFAULT_NAME).caption(DEFAULT_CAPTION).documentation(DEFAULT_DOCUMENTATION);
        return dataForest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataForest createUpdatedEntity(EntityManager em) {
        DataForest dataForest = new DataForest().name(UPDATED_NAME).caption(UPDATED_CAPTION).documentation(UPDATED_DOCUMENTATION);
        return dataForest;
    }

    @BeforeEach
    public void initTest() {
        dataForest = createEntity(em);
    }

    @Test
    @Transactional
    void createDataForest() throws Exception {
        int databaseSizeBeforeCreate = dataForestRepository.findAll().size();
        // Create the DataForest
        restDataForestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataForest)))
            .andExpect(status().isCreated());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeCreate + 1);
        DataForest testDataForest = dataForestList.get(dataForestList.size() - 1);
        assertThat(testDataForest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataForest.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataForest.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    @Transactional
    void createDataForestWithExistingId() throws Exception {
        // Create the DataForest with an existing ID
        dataForest.setId(1L);

        int databaseSizeBeforeCreate = dataForestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataForestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataForest)))
            .andExpect(status().isBadRequest());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDataForests() throws Exception {
        // Initialize the database
        dataForestRepository.saveAndFlush(dataForest);

        // Get all the dataForestList
        restDataForestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataForest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION)))
            .andExpect(jsonPath("$.[*].documentation").value(hasItem(DEFAULT_DOCUMENTATION)));
    }

    @Test
    @Transactional
    void getDataForest() throws Exception {
        // Initialize the database
        dataForestRepository.saveAndFlush(dataForest);

        // Get the dataForest
        restDataForestMockMvc
            .perform(get(ENTITY_API_URL_ID, dataForest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataForest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION))
            .andExpect(jsonPath("$.documentation").value(DEFAULT_DOCUMENTATION));
    }

    @Test
    @Transactional
    void getNonExistingDataForest() throws Exception {
        // Get the dataForest
        restDataForestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDataForest() throws Exception {
        // Initialize the database
        dataForestRepository.saveAndFlush(dataForest);

        int databaseSizeBeforeUpdate = dataForestRepository.findAll().size();

        // Update the dataForest
        DataForest updatedDataForest = dataForestRepository.findById(dataForest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDataForest are not directly saved in db
        em.detach(updatedDataForest);
        updatedDataForest.name(UPDATED_NAME).caption(UPDATED_CAPTION).documentation(UPDATED_DOCUMENTATION);

        restDataForestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDataForest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDataForest))
            )
            .andExpect(status().isOk());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
        DataForest testDataForest = dataForestList.get(dataForestList.size() - 1);
        assertThat(testDataForest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataForest.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataForest.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void putNonExistingDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().size();
        dataForest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataForestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataForest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataForest))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().size();
        dataForest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataForestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataForest))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().size();
        dataForest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataForestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataForest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataForestWithPatch() throws Exception {
        // Initialize the database
        dataForestRepository.saveAndFlush(dataForest);

        int databaseSizeBeforeUpdate = dataForestRepository.findAll().size();

        // Update the dataForest using partial update
        DataForest partialUpdatedDataForest = new DataForest();
        partialUpdatedDataForest.setId(dataForest.getId());

        partialUpdatedDataForest.name(UPDATED_NAME).documentation(UPDATED_DOCUMENTATION);

        restDataForestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataForest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataForest))
            )
            .andExpect(status().isOk());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
        DataForest testDataForest = dataForestList.get(dataForestList.size() - 1);
        assertThat(testDataForest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataForest.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataForest.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void fullUpdateDataForestWithPatch() throws Exception {
        // Initialize the database
        dataForestRepository.saveAndFlush(dataForest);

        int databaseSizeBeforeUpdate = dataForestRepository.findAll().size();

        // Update the dataForest using partial update
        DataForest partialUpdatedDataForest = new DataForest();
        partialUpdatedDataForest.setId(dataForest.getId());

        partialUpdatedDataForest.name(UPDATED_NAME).caption(UPDATED_CAPTION).documentation(UPDATED_DOCUMENTATION);

        restDataForestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataForest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataForest))
            )
            .andExpect(status().isOk());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
        DataForest testDataForest = dataForestList.get(dataForestList.size() - 1);
        assertThat(testDataForest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataForest.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataForest.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void patchNonExistingDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().size();
        dataForest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataForestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataForest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataForest))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().size();
        dataForest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataForestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataForest))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().size();
        dataForest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataForestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataForest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataForest() throws Exception {
        // Initialize the database
        dataForestRepository.saveAndFlush(dataForest);

        int databaseSizeBeforeDelete = dataForestRepository.findAll().size();

        // Delete the dataForest
        restDataForestMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataForest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataForest> dataForestList = dataForestRepository.findAll();
        assertThat(dataForestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
