package com.bami.dfm.v1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.dfm.v1.IntegrationTest;
import com.bami.dfm.v1.domain.DataTreeBranch;
import com.bami.dfm.v1.domain.enumeration.StereoTypeEnum;
import com.bami.dfm.v1.repository.DataTreeBranchRepository;
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
 * Integration tests for the {@link DataTreeBranchResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DataTreeBranchResourceIT {

    private static final StereoTypeEnum DEFAULT_STEREO_TYPE = StereoTypeEnum.SINGLE_ROW;
    private static final StereoTypeEnum UPDATED_STEREO_TYPE = StereoTypeEnum.COLLECTION;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-tree-branches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataTreeBranchRepository dataTreeBranchRepository;

    @Mock
    private DataTreeBranchRepository dataTreeBranchRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataTreeBranchMockMvc;

    private DataTreeBranch dataTreeBranch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeBranch createEntity(EntityManager em) {
        DataTreeBranch dataTreeBranch = new DataTreeBranch()
            .stereoType(DEFAULT_STEREO_TYPE)
            .name(DEFAULT_NAME)
            .caption(DEFAULT_CAPTION)
            .documentation(DEFAULT_DOCUMENTATION);
        return dataTreeBranch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeBranch createUpdatedEntity(EntityManager em) {
        DataTreeBranch dataTreeBranch = new DataTreeBranch()
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);
        return dataTreeBranch;
    }

    @BeforeEach
    public void initTest() {
        dataTreeBranch = createEntity(em);
    }

    @Test
    @Transactional
    void createDataTreeBranch() throws Exception {
        int databaseSizeBeforeCreate = dataTreeBranchRepository.findAll().size();
        // Create the DataTreeBranch
        restDataTreeBranchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            )
            .andExpect(status().isCreated());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeCreate + 1);
        DataTreeBranch testDataTreeBranch = dataTreeBranchList.get(dataTreeBranchList.size() - 1);
        assertThat(testDataTreeBranch.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeBranch.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeBranch.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeBranch.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    @Transactional
    void createDataTreeBranchWithExistingId() throws Exception {
        // Create the DataTreeBranch with an existing ID
        dataTreeBranch.setId(1L);

        int databaseSizeBeforeCreate = dataTreeBranchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataTreeBranchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDataTreeBranches() throws Exception {
        // Initialize the database
        dataTreeBranchRepository.saveAndFlush(dataTreeBranch);

        // Get all the dataTreeBranchList
        restDataTreeBranchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataTreeBranch.getId().intValue())))
            .andExpect(jsonPath("$.[*].stereoType").value(hasItem(DEFAULT_STEREO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION)))
            .andExpect(jsonPath("$.[*].documentation").value(hasItem(DEFAULT_DOCUMENTATION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDataTreeBranchesWithEagerRelationshipsIsEnabled() throws Exception {
        when(dataTreeBranchRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDataTreeBranchMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dataTreeBranchRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDataTreeBranchesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dataTreeBranchRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDataTreeBranchMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dataTreeBranchRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDataTreeBranch() throws Exception {
        // Initialize the database
        dataTreeBranchRepository.saveAndFlush(dataTreeBranch);

        // Get the dataTreeBranch
        restDataTreeBranchMockMvc
            .perform(get(ENTITY_API_URL_ID, dataTreeBranch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataTreeBranch.getId().intValue()))
            .andExpect(jsonPath("$.stereoType").value(DEFAULT_STEREO_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION))
            .andExpect(jsonPath("$.documentation").value(DEFAULT_DOCUMENTATION));
    }

    @Test
    @Transactional
    void getNonExistingDataTreeBranch() throws Exception {
        // Get the dataTreeBranch
        restDataTreeBranchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDataTreeBranch() throws Exception {
        // Initialize the database
        dataTreeBranchRepository.saveAndFlush(dataTreeBranch);

        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().size();

        // Update the dataTreeBranch
        DataTreeBranch updatedDataTreeBranch = dataTreeBranchRepository.findById(dataTreeBranch.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDataTreeBranch are not directly saved in db
        em.detach(updatedDataTreeBranch);
        updatedDataTreeBranch
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        restDataTreeBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDataTreeBranch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDataTreeBranch))
            )
            .andExpect(status().isOk());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
        DataTreeBranch testDataTreeBranch = dataTreeBranchList.get(dataTreeBranchList.size() - 1);
        assertThat(testDataTreeBranch.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeBranch.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeBranch.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void putNonExistingDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataTreeBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataTreeBranch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeBranchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataTreeBranch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataTreeBranchWithPatch() throws Exception {
        // Initialize the database
        dataTreeBranchRepository.saveAndFlush(dataTreeBranch);

        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().size();

        // Update the dataTreeBranch using partial update
        DataTreeBranch partialUpdatedDataTreeBranch = new DataTreeBranch();
        partialUpdatedDataTreeBranch.setId(dataTreeBranch.getId());

        partialUpdatedDataTreeBranch
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        restDataTreeBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataTreeBranch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeBranch))
            )
            .andExpect(status().isOk());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
        DataTreeBranch testDataTreeBranch = dataTreeBranchList.get(dataTreeBranchList.size() - 1);
        assertThat(testDataTreeBranch.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeBranch.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeBranch.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void fullUpdateDataTreeBranchWithPatch() throws Exception {
        // Initialize the database
        dataTreeBranchRepository.saveAndFlush(dataTreeBranch);

        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().size();

        // Update the dataTreeBranch using partial update
        DataTreeBranch partialUpdatedDataTreeBranch = new DataTreeBranch();
        partialUpdatedDataTreeBranch.setId(dataTreeBranch.getId());

        partialUpdatedDataTreeBranch
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        restDataTreeBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataTreeBranch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeBranch))
            )
            .andExpect(status().isOk());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
        DataTreeBranch testDataTreeBranch = dataTreeBranchList.get(dataTreeBranchList.size() - 1);
        assertThat(testDataTreeBranch.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeBranch.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeBranch.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void patchNonExistingDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataTreeBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataTreeBranch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeBranchMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataTreeBranch() throws Exception {
        // Initialize the database
        dataTreeBranchRepository.saveAndFlush(dataTreeBranch);

        int databaseSizeBeforeDelete = dataTreeBranchRepository.findAll().size();

        // Delete the dataTreeBranch
        restDataTreeBranchMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataTreeBranch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
