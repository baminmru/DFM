package com.bami.ef.tent.web.rest;

import static com.bami.ef.tent.domain.RequestContentConfigAsserts.*;
import static com.bami.ef.tent.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.ef.tent.IntegrationTest;
import com.bami.ef.tent.domain.RequestContentConfig;
import com.bami.ef.tent.repository.RequestContentConfigRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link RequestContentConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestContentConfigResourceIT {

    private static final Integer DEFAULT_REQUEST_CONFIG_ID = 1;
    private static final Integer UPDATED_REQUEST_CONFIG_ID = 2;

    private static final Integer DEFAULT_PARAMETER = 1;
    private static final Integer UPDATED_PARAMETER = 2;

    private static final Boolean DEFAULT_IS_MANDATORY = false;
    private static final Boolean UPDATED_IS_MANDATORY = true;

    private static final String ENTITY_API_URL = "/api/request-content-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RequestContentConfigRepository requestContentConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestContentConfigMockMvc;

    private RequestContentConfig requestContentConfig;

    private RequestContentConfig insertedRequestContentConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestContentConfig createEntity(EntityManager em) {
        RequestContentConfig requestContentConfig = new RequestContentConfig()
            .requestConfigId(DEFAULT_REQUEST_CONFIG_ID)
            .parameter(DEFAULT_PARAMETER)
            .isMandatory(DEFAULT_IS_MANDATORY);
        return requestContentConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestContentConfig createUpdatedEntity(EntityManager em) {
        RequestContentConfig requestContentConfig = new RequestContentConfig()
            .requestConfigId(UPDATED_REQUEST_CONFIG_ID)
            .parameter(UPDATED_PARAMETER)
            .isMandatory(UPDATED_IS_MANDATORY);
        return requestContentConfig;
    }

    @BeforeEach
    public void initTest() {
        requestContentConfig = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRequestContentConfig != null) {
            requestContentConfigRepository.delete(insertedRequestContentConfig);
            insertedRequestContentConfig = null;
        }
    }

    @Test
    @Transactional
    void createRequestContentConfig() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RequestContentConfig
        var returnedRequestContentConfig = om.readValue(
            restRequestContentConfigMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestContentConfig)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RequestContentConfig.class
        );

        // Validate the RequestContentConfig in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRequestContentConfigUpdatableFieldsEquals(
            returnedRequestContentConfig,
            getPersistedRequestContentConfig(returnedRequestContentConfig)
        );

        insertedRequestContentConfig = returnedRequestContentConfig;
    }

    @Test
    @Transactional
    void createRequestContentConfigWithExistingId() throws Exception {
        // Create the RequestContentConfig with an existing ID
        requestContentConfig.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestContentConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestContentConfig)))
            .andExpect(status().isBadRequest());

        // Validate the RequestContentConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRequestConfigIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestContentConfig.setRequestConfigId(null);

        // Create the RequestContentConfig, which fails.

        restRequestContentConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestContentConfig)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequestContentConfigs() throws Exception {
        // Initialize the database
        insertedRequestContentConfig = requestContentConfigRepository.saveAndFlush(requestContentConfig);

        // Get all the requestContentConfigList
        restRequestContentConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestContentConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestConfigId").value(hasItem(DEFAULT_REQUEST_CONFIG_ID)))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER)))
            .andExpect(jsonPath("$.[*].isMandatory").value(hasItem(DEFAULT_IS_MANDATORY.booleanValue())));
    }

    @Test
    @Transactional
    void getRequestContentConfig() throws Exception {
        // Initialize the database
        insertedRequestContentConfig = requestContentConfigRepository.saveAndFlush(requestContentConfig);

        // Get the requestContentConfig
        restRequestContentConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, requestContentConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestContentConfig.getId().intValue()))
            .andExpect(jsonPath("$.requestConfigId").value(DEFAULT_REQUEST_CONFIG_ID))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER))
            .andExpect(jsonPath("$.isMandatory").value(DEFAULT_IS_MANDATORY.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRequestContentConfig() throws Exception {
        // Get the requestContentConfig
        restRequestContentConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestContentConfig() throws Exception {
        // Initialize the database
        insertedRequestContentConfig = requestContentConfigRepository.saveAndFlush(requestContentConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestContentConfig
        RequestContentConfig updatedRequestContentConfig = requestContentConfigRepository
            .findById(requestContentConfig.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedRequestContentConfig are not directly saved in db
        em.detach(updatedRequestContentConfig);
        updatedRequestContentConfig
            .requestConfigId(UPDATED_REQUEST_CONFIG_ID)
            .parameter(UPDATED_PARAMETER)
            .isMandatory(UPDATED_IS_MANDATORY);

        restRequestContentConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequestContentConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRequestContentConfig))
            )
            .andExpect(status().isOk());

        // Validate the RequestContentConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRequestContentConfigToMatchAllProperties(updatedRequestContentConfig);
    }

    @Test
    @Transactional
    void putNonExistingRequestContentConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContentConfig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestContentConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestContentConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestContentConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestContentConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestContentConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContentConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestContentConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestContentConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestContentConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestContentConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContentConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestContentConfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestContentConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestContentConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestContentConfigWithPatch() throws Exception {
        // Initialize the database
        insertedRequestContentConfig = requestContentConfigRepository.saveAndFlush(requestContentConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestContentConfig using partial update
        RequestContentConfig partialUpdatedRequestContentConfig = new RequestContentConfig();
        partialUpdatedRequestContentConfig.setId(requestContentConfig.getId());

        restRequestContentConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestContentConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestContentConfig))
            )
            .andExpect(status().isOk());

        // Validate the RequestContentConfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestContentConfigUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRequestContentConfig, requestContentConfig),
            getPersistedRequestContentConfig(requestContentConfig)
        );
    }

    @Test
    @Transactional
    void fullUpdateRequestContentConfigWithPatch() throws Exception {
        // Initialize the database
        insertedRequestContentConfig = requestContentConfigRepository.saveAndFlush(requestContentConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestContentConfig using partial update
        RequestContentConfig partialUpdatedRequestContentConfig = new RequestContentConfig();
        partialUpdatedRequestContentConfig.setId(requestContentConfig.getId());

        partialUpdatedRequestContentConfig
            .requestConfigId(UPDATED_REQUEST_CONFIG_ID)
            .parameter(UPDATED_PARAMETER)
            .isMandatory(UPDATED_IS_MANDATORY);

        restRequestContentConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestContentConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestContentConfig))
            )
            .andExpect(status().isOk());

        // Validate the RequestContentConfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestContentConfigUpdatableFieldsEquals(
            partialUpdatedRequestContentConfig,
            getPersistedRequestContentConfig(partialUpdatedRequestContentConfig)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRequestContentConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContentConfig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestContentConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestContentConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestContentConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestContentConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestContentConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContentConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestContentConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestContentConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestContentConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestContentConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContentConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestContentConfigMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(requestContentConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestContentConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestContentConfig() throws Exception {
        // Initialize the database
        insertedRequestContentConfig = requestContentConfigRepository.saveAndFlush(requestContentConfig);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the requestContentConfig
        restRequestContentConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestContentConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return requestContentConfigRepository.count();
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

    protected RequestContentConfig getPersistedRequestContentConfig(RequestContentConfig requestContentConfig) {
        return requestContentConfigRepository.findById(requestContentConfig.getId()).orElseThrow();
    }

    protected void assertPersistedRequestContentConfigToMatchAllProperties(RequestContentConfig expectedRequestContentConfig) {
        assertRequestContentConfigAllPropertiesEquals(
            expectedRequestContentConfig,
            getPersistedRequestContentConfig(expectedRequestContentConfig)
        );
    }

    protected void assertPersistedRequestContentConfigToMatchUpdatableProperties(RequestContentConfig expectedRequestContentConfig) {
        assertRequestContentConfigAllUpdatablePropertiesEquals(
            expectedRequestContentConfig,
            getPersistedRequestContentConfig(expectedRequestContentConfig)
        );
    }
}
