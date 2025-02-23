package com.bami.tent.request.web.rest;

import static com.bami.tent.request.domain.SourceSystemAsserts.*;
import static com.bami.tent.request.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.tent.request.IntegrationTest;
import com.bami.tent.request.domain.SourceSystem;
import com.bami.tent.request.repository.SourceSystemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SourceSystemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SourceSystemResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/source-systems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SourceSystemRepository sourceSystemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSourceSystemMockMvc;

    private SourceSystem sourceSystem;

    private SourceSystem insertedSourceSystem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SourceSystem createEntity(EntityManager em) {
        SourceSystem sourceSystem = new SourceSystem()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY);
        return sourceSystem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SourceSystem createUpdatedEntity(EntityManager em) {
        SourceSystem sourceSystem = new SourceSystem()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);
        return sourceSystem;
    }

    @BeforeEach
    public void initTest() {
        sourceSystem = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSourceSystem != null) {
            sourceSystemRepository.delete(insertedSourceSystem);
            insertedSourceSystem = null;
        }
    }

    @Test
    @Transactional
    void createSourceSystem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SourceSystem
        var returnedSourceSystem = om.readValue(
            restSourceSystemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceSystem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SourceSystem.class
        );

        // Validate the SourceSystem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSourceSystemUpdatableFieldsEquals(returnedSourceSystem, getPersistedSourceSystem(returnedSourceSystem));

        insertedSourceSystem = returnedSourceSystem;
    }

    @Test
    @Transactional
    void createSourceSystemWithExistingId() throws Exception {
        // Create the SourceSystem with an existing ID
        sourceSystem.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceSystem)))
            .andExpect(status().isBadRequest());

        // Validate the SourceSystem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sourceSystem.setCode(null);

        // Create the SourceSystem, which fails.

        restSourceSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceSystem)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSourceSystems() throws Exception {
        // Initialize the database
        insertedSourceSystem = sourceSystemRepository.saveAndFlush(sourceSystem);

        // Get all the sourceSystemList
        restSourceSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getSourceSystem() throws Exception {
        // Initialize the database
        insertedSourceSystem = sourceSystemRepository.saveAndFlush(sourceSystem);

        // Get the sourceSystem
        restSourceSystemMockMvc
            .perform(get(ENTITY_API_URL_ID, sourceSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sourceSystem.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingSourceSystem() throws Exception {
        // Get the sourceSystem
        restSourceSystemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSourceSystem() throws Exception {
        // Initialize the database
        insertedSourceSystem = sourceSystemRepository.saveAndFlush(sourceSystem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sourceSystem
        SourceSystem updatedSourceSystem = sourceSystemRepository.findById(sourceSystem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSourceSystem are not directly saved in db
        em.detach(updatedSourceSystem);
        updatedSourceSystem
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restSourceSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSourceSystem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSourceSystem))
            )
            .andExpect(status().isOk());

        // Validate the SourceSystem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSourceSystemToMatchAllProperties(updatedSourceSystem);
    }

    @Test
    @Transactional
    void putNonExistingSourceSystem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceSystem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sourceSystem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sourceSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceSystem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSourceSystem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceSystem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sourceSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceSystem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSourceSystem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceSystem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceSystemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceSystem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SourceSystem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSourceSystemWithPatch() throws Exception {
        // Initialize the database
        insertedSourceSystem = sourceSystemRepository.saveAndFlush(sourceSystem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sourceSystem using partial update
        SourceSystem partialUpdatedSourceSystem = new SourceSystem();
        partialUpdatedSourceSystem.setId(sourceSystem.getId());

        partialUpdatedSourceSystem.code(UPDATED_CODE).name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedBy(UPDATED_UPDATED_BY);

        restSourceSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSourceSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSourceSystem))
            )
            .andExpect(status().isOk());

        // Validate the SourceSystem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSourceSystemUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSourceSystem, sourceSystem),
            getPersistedSourceSystem(sourceSystem)
        );
    }

    @Test
    @Transactional
    void fullUpdateSourceSystemWithPatch() throws Exception {
        // Initialize the database
        insertedSourceSystem = sourceSystemRepository.saveAndFlush(sourceSystem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sourceSystem using partial update
        SourceSystem partialUpdatedSourceSystem = new SourceSystem();
        partialUpdatedSourceSystem.setId(sourceSystem.getId());

        partialUpdatedSourceSystem
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restSourceSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSourceSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSourceSystem))
            )
            .andExpect(status().isOk());

        // Validate the SourceSystem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSourceSystemUpdatableFieldsEquals(partialUpdatedSourceSystem, getPersistedSourceSystem(partialUpdatedSourceSystem));
    }

    @Test
    @Transactional
    void patchNonExistingSourceSystem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceSystem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sourceSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sourceSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceSystem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSourceSystem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceSystem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sourceSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceSystem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSourceSystem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceSystem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceSystemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sourceSystem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SourceSystem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSourceSystem() throws Exception {
        // Initialize the database
        insertedSourceSystem = sourceSystemRepository.saveAndFlush(sourceSystem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sourceSystem
        restSourceSystemMockMvc
            .perform(delete(ENTITY_API_URL_ID, sourceSystem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sourceSystemRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected SourceSystem getPersistedSourceSystem(SourceSystem sourceSystem) {
        return sourceSystemRepository.findById(sourceSystem.getId()).orElseThrow();
    }

    protected void assertPersistedSourceSystemToMatchAllProperties(SourceSystem expectedSourceSystem) {
        assertSourceSystemAllPropertiesEquals(expectedSourceSystem, getPersistedSourceSystem(expectedSourceSystem));
    }

    protected void assertPersistedSourceSystemToMatchUpdatableProperties(SourceSystem expectedSourceSystem) {
        assertSourceSystemAllUpdatablePropertiesEquals(expectedSourceSystem, getPersistedSourceSystem(expectedSourceSystem));
    }
}
