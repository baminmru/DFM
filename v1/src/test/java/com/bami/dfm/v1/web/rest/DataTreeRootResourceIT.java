package com.bami.dfm.v1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.dfm.v1.IntegrationTest;
import com.bami.dfm.v1.domain.DataTreeRoot;
import com.bami.dfm.v1.domain.enumeration.StereoTypeEnum;
import com.bami.dfm.v1.repository.DataTreeRootRepository;
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
 * Integration tests for the {@link DataTreeRootResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DataTreeRootResourceIT {

    private static final StereoTypeEnum DEFAULT_STEREO_TYPE = StereoTypeEnum.SINGLE_ROW;
    private static final StereoTypeEnum UPDATED_STEREO_TYPE = StereoTypeEnum.COLLECTION;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-tree-roots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataTreeRootRepository dataTreeRootRepository;

    @Mock
    private DataTreeRootRepository dataTreeRootRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataTreeRootMockMvc;

    private DataTreeRoot dataTreeRoot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeRoot createEntity(EntityManager em) {
        DataTreeRoot dataTreeRoot = new DataTreeRoot()
            .stereoType(DEFAULT_STEREO_TYPE)
            .name(DEFAULT_NAME)
            .caption(DEFAULT_CAPTION)
            .documentation(DEFAULT_DOCUMENTATION);
        return dataTreeRoot;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeRoot createUpdatedEntity(EntityManager em) {
        DataTreeRoot dataTreeRoot = new DataTreeRoot()
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);
        return dataTreeRoot;
    }

    @BeforeEach
    public void initTest() {
        dataTreeRoot = createEntity(em);
    }

    @Test
    @Transactional
    void createDataTreeRoot() throws Exception {
        int databaseSizeBeforeCreate = dataTreeRootRepository.findAll().size();
        // Create the DataTreeRoot
        restDataTreeRootMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataTreeRoot)))
            .andExpect(status().isCreated());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeCreate + 1);
        DataTreeRoot testDataTreeRoot = dataTreeRootList.get(dataTreeRootList.size() - 1);
        assertThat(testDataTreeRoot.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeRoot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeRoot.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeRoot.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    @Transactional
    void createDataTreeRootWithExistingId() throws Exception {
        // Create the DataTreeRoot with an existing ID
        dataTreeRoot.setId(1L);

        int databaseSizeBeforeCreate = dataTreeRootRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataTreeRootMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataTreeRoot)))
            .andExpect(status().isBadRequest());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDataTreeRoots() throws Exception {
        // Initialize the database
        dataTreeRootRepository.saveAndFlush(dataTreeRoot);

        // Get all the dataTreeRootList
        restDataTreeRootMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataTreeRoot.getId().intValue())))
            .andExpect(jsonPath("$.[*].stereoType").value(hasItem(DEFAULT_STEREO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION)))
            .andExpect(jsonPath("$.[*].documentation").value(hasItem(DEFAULT_DOCUMENTATION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDataTreeRootsWithEagerRelationshipsIsEnabled() throws Exception {
        when(dataTreeRootRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDataTreeRootMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dataTreeRootRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDataTreeRootsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dataTreeRootRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDataTreeRootMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dataTreeRootRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDataTreeRoot() throws Exception {
        // Initialize the database
        dataTreeRootRepository.saveAndFlush(dataTreeRoot);

        // Get the dataTreeRoot
        restDataTreeRootMockMvc
            .perform(get(ENTITY_API_URL_ID, dataTreeRoot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataTreeRoot.getId().intValue()))
            .andExpect(jsonPath("$.stereoType").value(DEFAULT_STEREO_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION))
            .andExpect(jsonPath("$.documentation").value(DEFAULT_DOCUMENTATION));
    }

    @Test
    @Transactional
    void getNonExistingDataTreeRoot() throws Exception {
        // Get the dataTreeRoot
        restDataTreeRootMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDataTreeRoot() throws Exception {
        // Initialize the database
        dataTreeRootRepository.saveAndFlush(dataTreeRoot);

        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().size();

        // Update the dataTreeRoot
        DataTreeRoot updatedDataTreeRoot = dataTreeRootRepository.findById(dataTreeRoot.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDataTreeRoot are not directly saved in db
        em.detach(updatedDataTreeRoot);
        updatedDataTreeRoot
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        restDataTreeRootMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDataTreeRoot.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDataTreeRoot))
            )
            .andExpect(status().isOk());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
        DataTreeRoot testDataTreeRoot = dataTreeRootList.get(dataTreeRootList.size() - 1);
        assertThat(testDataTreeRoot.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeRoot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeRoot.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeRoot.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void putNonExistingDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataTreeRootMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataTreeRoot.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeRootMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeRootMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataTreeRoot)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataTreeRootWithPatch() throws Exception {
        // Initialize the database
        dataTreeRootRepository.saveAndFlush(dataTreeRoot);

        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().size();

        // Update the dataTreeRoot using partial update
        DataTreeRoot partialUpdatedDataTreeRoot = new DataTreeRoot();
        partialUpdatedDataTreeRoot.setId(dataTreeRoot.getId());

        partialUpdatedDataTreeRoot.caption(UPDATED_CAPTION);

        restDataTreeRootMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataTreeRoot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeRoot))
            )
            .andExpect(status().isOk());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
        DataTreeRoot testDataTreeRoot = dataTreeRootList.get(dataTreeRootList.size() - 1);
        assertThat(testDataTreeRoot.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeRoot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeRoot.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeRoot.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    @Transactional
    void fullUpdateDataTreeRootWithPatch() throws Exception {
        // Initialize the database
        dataTreeRootRepository.saveAndFlush(dataTreeRoot);

        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().size();

        // Update the dataTreeRoot using partial update
        DataTreeRoot partialUpdatedDataTreeRoot = new DataTreeRoot();
        partialUpdatedDataTreeRoot.setId(dataTreeRoot.getId());

        partialUpdatedDataTreeRoot
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        restDataTreeRootMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataTreeRoot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeRoot))
            )
            .andExpect(status().isOk());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
        DataTreeRoot testDataTreeRoot = dataTreeRootList.get(dataTreeRootList.size() - 1);
        assertThat(testDataTreeRoot.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeRoot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeRoot.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeRoot.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    @Transactional
    void patchNonExistingDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataTreeRootMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataTreeRoot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeRootMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataTreeRootMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataTreeRoot() throws Exception {
        // Initialize the database
        dataTreeRootRepository.saveAndFlush(dataTreeRoot);

        int databaseSizeBeforeDelete = dataTreeRootRepository.findAll().size();

        // Delete the dataTreeRoot
        restDataTreeRootMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataTreeRoot.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
