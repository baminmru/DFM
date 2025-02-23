package com.bami.tent.request.web.rest;

import static com.bami.tent.request.domain.RequestConfigAsserts.*;
import static com.bami.tent.request.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.tent.request.IntegrationTest;
import com.bami.tent.request.domain.RequestConfig;
import com.bami.tent.request.repository.RequestConfigRepository;
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
 * Integration tests for the {@link RequestConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestConfigResourceIT {

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EFFECTIVE_DATE_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EFFECTIVE_DATE_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE_END = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/request-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RequestConfigRepository requestConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestConfigMockMvc;

    private RequestConfig requestConfig;

    private RequestConfig insertedRequestConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestConfig createEntity(EntityManager em) {
        RequestConfig requestConfig = new RequestConfig()
            .version(DEFAULT_VERSION)
            .effectiveDateStart(DEFAULT_EFFECTIVE_DATE_START)
            .effectiveDateEnd(DEFAULT_EFFECTIVE_DATE_END)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY);
        return requestConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestConfig createUpdatedEntity(EntityManager em) {
        RequestConfig requestConfig = new RequestConfig()
            .version(UPDATED_VERSION)
            .effectiveDateStart(UPDATED_EFFECTIVE_DATE_START)
            .effectiveDateEnd(UPDATED_EFFECTIVE_DATE_END)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);
        return requestConfig;
    }

    @BeforeEach
    public void initTest() {
        requestConfig = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRequestConfig != null) {
            requestConfigRepository.delete(insertedRequestConfig);
            insertedRequestConfig = null;
        }
    }

    @Test
    @Transactional
    void createRequestConfig() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RequestConfig
        var returnedRequestConfig = om.readValue(
            restRequestConfigMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestConfig)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RequestConfig.class
        );

        // Validate the RequestConfig in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRequestConfigUpdatableFieldsEquals(returnedRequestConfig, getPersistedRequestConfig(returnedRequestConfig));

        insertedRequestConfig = returnedRequestConfig;
    }

    @Test
    @Transactional
    void createRequestConfigWithExistingId() throws Exception {
        // Create the RequestConfig with an existing ID
        requestConfig.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestConfig)))
            .andExpect(status().isBadRequest());

        // Validate the RequestConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequestConfigs() throws Exception {
        // Initialize the database
        insertedRequestConfig = requestConfigRepository.saveAndFlush(requestConfig);

        // Get all the requestConfigList
        restRequestConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].effectiveDateStart").value(hasItem(DEFAULT_EFFECTIVE_DATE_START.toString())))
            .andExpect(jsonPath("$.[*].effectiveDateEnd").value(hasItem(DEFAULT_EFFECTIVE_DATE_END.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getRequestConfig() throws Exception {
        // Initialize the database
        insertedRequestConfig = requestConfigRepository.saveAndFlush(requestConfig);

        // Get the requestConfig
        restRequestConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, requestConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestConfig.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.effectiveDateStart").value(DEFAULT_EFFECTIVE_DATE_START.toString()))
            .andExpect(jsonPath("$.effectiveDateEnd").value(DEFAULT_EFFECTIVE_DATE_END.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingRequestConfig() throws Exception {
        // Get the requestConfig
        restRequestConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestConfig() throws Exception {
        // Initialize the database
        insertedRequestConfig = requestConfigRepository.saveAndFlush(requestConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestConfig
        RequestConfig updatedRequestConfig = requestConfigRepository.findById(requestConfig.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRequestConfig are not directly saved in db
        em.detach(updatedRequestConfig);
        updatedRequestConfig
            .version(UPDATED_VERSION)
            .effectiveDateStart(UPDATED_EFFECTIVE_DATE_START)
            .effectiveDateEnd(UPDATED_EFFECTIVE_DATE_END)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restRequestConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequestConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRequestConfig))
            )
            .andExpect(status().isOk());

        // Validate the RequestConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRequestConfigToMatchAllProperties(updatedRequestConfig);
    }

    @Test
    @Transactional
    void putNonExistingRequestConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestConfig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestConfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestConfigWithPatch() throws Exception {
        // Initialize the database
        insertedRequestConfig = requestConfigRepository.saveAndFlush(requestConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestConfig using partial update
        RequestConfig partialUpdatedRequestConfig = new RequestConfig();
        partialUpdatedRequestConfig.setId(requestConfig.getId());

        partialUpdatedRequestConfig.createdAt(UPDATED_CREATED_AT).createdBy(UPDATED_CREATED_BY).updatedBy(UPDATED_UPDATED_BY);

        restRequestConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestConfig))
            )
            .andExpect(status().isOk());

        // Validate the RequestConfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestConfigUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRequestConfig, requestConfig),
            getPersistedRequestConfig(requestConfig)
        );
    }

    @Test
    @Transactional
    void fullUpdateRequestConfigWithPatch() throws Exception {
        // Initialize the database
        insertedRequestConfig = requestConfigRepository.saveAndFlush(requestConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestConfig using partial update
        RequestConfig partialUpdatedRequestConfig = new RequestConfig();
        partialUpdatedRequestConfig.setId(requestConfig.getId());

        partialUpdatedRequestConfig
            .version(UPDATED_VERSION)
            .effectiveDateStart(UPDATED_EFFECTIVE_DATE_START)
            .effectiveDateEnd(UPDATED_EFFECTIVE_DATE_END)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restRequestConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestConfig))
            )
            .andExpect(status().isOk());

        // Validate the RequestConfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestConfigUpdatableFieldsEquals(partialUpdatedRequestConfig, getPersistedRequestConfig(partialUpdatedRequestConfig));
    }

    @Test
    @Transactional
    void patchNonExistingRequestConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestConfig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestConfigMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(requestConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestConfig() throws Exception {
        // Initialize the database
        insertedRequestConfig = requestConfigRepository.saveAndFlush(requestConfig);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the requestConfig
        restRequestConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return requestConfigRepository.count();
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

    protected RequestConfig getPersistedRequestConfig(RequestConfig requestConfig) {
        return requestConfigRepository.findById(requestConfig.getId()).orElseThrow();
    }

    protected void assertPersistedRequestConfigToMatchAllProperties(RequestConfig expectedRequestConfig) {
        assertRequestConfigAllPropertiesEquals(expectedRequestConfig, getPersistedRequestConfig(expectedRequestConfig));
    }

    protected void assertPersistedRequestConfigToMatchUpdatableProperties(RequestConfig expectedRequestConfig) {
        assertRequestConfigAllUpdatablePropertiesEquals(expectedRequestConfig, getPersistedRequestConfig(expectedRequestConfig));
    }
}
