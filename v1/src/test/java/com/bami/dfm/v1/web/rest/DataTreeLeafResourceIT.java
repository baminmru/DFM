package com.bami.dfm.v1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.dfm.v1.IntegrationTest;
import com.bami.dfm.v1.domain.DataTreeLeaf;
import com.bami.dfm.v1.domain.enumeration.StereoTypeEnum;
import com.bami.dfm.v1.repository.DataTreeLeafRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DataTreeLeafResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DataTreeLeafResourceIT {

    private static final StereoTypeEnum DEFAULT_STEREO_TYPE = StereoTypeEnum.SINGLE_ROW;
    private static final StereoTypeEnum UPDATED_STEREO_TYPE = StereoTypeEnum.COLLECTION;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-tree-leaves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataTreeLeafRepository dataTreeLeafRepository;

    @Mock
    private DataTreeLeafRepository dataTreeLeafRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataTreeLeafMockMvc;

    private DataTreeLeaf dataTreeLeaf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeLeaf createEntity(EntityManager em) {
        DataTreeLeaf dataTreeLeaf = new DataTreeLeaf()
            .stereoType(DEFAULT_STEREO_TYPE)
            .name(DEFAULT_NAME)
            .caption(DEFAULT_CAPTION)
            .documentation(DEFAULT_DOCUMENTATION);
        return dataTreeLeaf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeLeaf createUpdatedEntity(EntityManager em) {
        DataTreeLeaf dataTreeLeaf = new DataTreeLeaf()
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);
        return dataTreeLeaf;
    }

    @BeforeEach
    public void initTest() {
        dataTreeLeaf = createEntity(em);
    }

    @Test
    @Transactional
    void createDataTreeLeaf() throws Exception {
        int databaseSizeBeforeCreate = dataTreeLeafRepository.findAll().size();
        // Create the DataTreeLeaf
        restDataTreeLeafMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataTreeLeaf)))
            .andExpect(status().isCreated());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeCreate + 1);
        DataTreeLeaf testDataTreeLeaf = dataTreeLeafList.get(dataTreeLeafList.size() - 1);
        assertThat(testDataTreeLeaf.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeLeaf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeLeaf.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeLeaf.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    @Transactional
    void createDataTreeLeafWithExistingId() throws Exception {
        // Create the DataTreeLeaf with an existing ID
        dataTreeLeaf.setId(1L);

        int databaseSizeBeforeCreate = dataTreeLeafRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataTreeLeafMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataTreeLeaf)))
            .andExpect(status().isBadRequest());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDataTreeLeaves() throws Exception {
        // Initialize the database
        dataTreeLeafRepository.saveAndFlush(dataTreeLeaf);

        // Get all the dataTreeLeafList
        restDataTreeLeafMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataTreeLeaf.getId().intValue())))
            .andExpect(jsonPath("$.[*].stereoType").value(hasItem(DEFAULT_STEREO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION)))
            .andExpect(jsonPath("$.[*].documentation").value(hasItem(DEFAULT_DOCUMENTATION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDataTreeLeavesWithEagerRelationshipsIsEnabled() throws Exception {
        when(dataTreeLeafRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDataTreeLeafMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dataTreeLeafRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDataTreeLeavesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dataTreeLeafRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDataTreeLeafMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dataTreeLeafRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDataTreeLeaf() throws Exception {
        // Initialize the database
        dataTreeLeafRepository.saveAndFlush(dataTreeLeaf);

        // Get the dataTreeLeaf
        restDataTreeLeafMockMvc
            .perform(get(ENTITY_API_URL_ID, dataTreeLeaf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataTreeLeaf.getId().intValue()))
            .andExpect(jsonPath("$.stereoType").value(DEFAULT_STEREO_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION))
            .andExpect(jsonPath("$.documentation").value(DEFAULT_DOCUMENTATION));
    }

    @Test
    @Transactional
    void getNonExistingDataTreeLeaf() throws Exception {
        // Get the dataTreeLeaf
        restDataTreeLeafMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDataTreeLeaf() throws Exception {
        // Initialize the database
        dataTreeLeafRepository.saveAndFlush(dataTreeLeaf);

        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().size();

        // Update the dataTreeLeaf
        DataTreeLeaf updatedDataTreeLeaf = dataTreeLeafRepository.findById(dataTreeLeaf.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDataTreeLeaf are not directly saved in db
        em.detach(updatedDataTreeLeaf);
        updatedDataTreeLeaf
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        restDataTreeLeafMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDataTreeLeaf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDataTreeLeaf))
            )
            .andExpect(status().isOk());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
        DataTreeLeaf testDataTreeLeaf = dataTreeLeafList.get(dataTreeLeafList.size() - 1);
        assertThat(testDataTreeLeaf.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeLeaf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeLeaf.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeLeaf.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void putNonExistingDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataTreeLeafMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataTreeLeaf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeLeafMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeLeafMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataTreeLeaf)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataTreeLeafWithPatch() throws Exception {
        // Initialize the database
        dataTreeLeafRepository.saveAndFlush(dataTreeLeaf);

        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().size();

        // Update the dataTreeLeaf using partial update
        DataTreeLeaf partialUpdatedDataTreeLeaf = new DataTreeLeaf();
        partialUpdatedDataTreeLeaf.setId(dataTreeLeaf.getId());

        restDataTreeLeafMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataTreeLeaf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeLeaf))
            )
            .andExpect(status().isOk());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
        DataTreeLeaf testDataTreeLeaf = dataTreeLeafList.get(dataTreeLeafList.size() - 1);
        assertThat(testDataTreeLeaf.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeLeaf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeLeaf.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeLeaf.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    @Transactional
    void fullUpdateDataTreeLeafWithPatch() throws Exception {
        // Initialize the database
        dataTreeLeafRepository.saveAndFlush(dataTreeLeaf);

        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().size();

        // Update the dataTreeLeaf using partial update
        DataTreeLeaf partialUpdatedDataTreeLeaf = new DataTreeLeaf();
        partialUpdatedDataTreeLeaf.setId(dataTreeLeaf.getId());

        partialUpdatedDataTreeLeaf
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        restDataTreeLeafMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataTreeLeaf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeLeaf))
            )
            .andExpect(status().isOk());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
        DataTreeLeaf testDataTreeLeaf = dataTreeLeafList.get(dataTreeLeafList.size() - 1);
        assertThat(testDataTreeLeaf.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeLeaf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeLeaf.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeLeaf.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void patchNonExistingDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataTreeLeafMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataTreeLeaf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeLeafMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeLeafMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataTreeLeaf() throws Exception {
        // Initialize the database
        dataTreeLeafRepository.saveAndFlush(dataTreeLeaf);

        int databaseSizeBeforeDelete = dataTreeLeafRepository.findAll().size();

        // Delete the dataTreeLeaf
        restDataTreeLeafMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataTreeLeaf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
