package com.bami.tent.request.web.rest;

import static com.bami.tent.request.domain.RequestTypeAsserts.*;
import static com.bami.tent.request.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.tent.request.IntegrationTest;
import com.bami.tent.request.domain.RequestType;
import com.bami.tent.request.repository.RequestTypeRepository;
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
 * Integration tests for the {@link RequestTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/request-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestTypeMockMvc;

    private RequestType requestType;

    private RequestType insertedRequestType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestType createEntity(EntityManager em) {
        RequestType requestType = new RequestType().code(DEFAULT_CODE).name(DEFAULT_NAME);
        return requestType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestType createUpdatedEntity(EntityManager em) {
        RequestType requestType = new RequestType().code(UPDATED_CODE).name(UPDATED_NAME);
        return requestType;
    }

    @BeforeEach
    public void initTest() {
        requestType = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRequestType != null) {
            requestTypeRepository.delete(insertedRequestType);
            insertedRequestType = null;
        }
    }

    @Test
    @Transactional
    void createRequestType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RequestType
        var returnedRequestType = om.readValue(
            restRequestTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestType)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RequestType.class
        );

        // Validate the RequestType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRequestTypeUpdatableFieldsEquals(returnedRequestType, getPersistedRequestType(returnedRequestType));

        insertedRequestType = returnedRequestType;
    }

    @Test
    @Transactional
    void createRequestTypeWithExistingId() throws Exception {
        // Create the RequestType with an existing ID
        requestType.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestType)))
            .andExpect(status().isBadRequest());

        // Validate the RequestType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestType.setCode(null);

        // Create the RequestType, which fails.

        restRequestTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestType)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestType.setName(null);

        // Create the RequestType, which fails.

        restRequestTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestType)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequestTypes() throws Exception {
        // Initialize the database
        insertedRequestType = requestTypeRepository.saveAndFlush(requestType);

        // Get all the requestTypeList
        restRequestTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRequestType() throws Exception {
        // Initialize the database
        insertedRequestType = requestTypeRepository.saveAndFlush(requestType);

        // Get the requestType
        restRequestTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, requestType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRequestType() throws Exception {
        // Get the requestType
        restRequestTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestType() throws Exception {
        // Initialize the database
        insertedRequestType = requestTypeRepository.saveAndFlush(requestType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestType
        RequestType updatedRequestType = requestTypeRepository.findById(requestType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRequestType are not directly saved in db
        em.detach(updatedRequestType);
        updatedRequestType.code(UPDATED_CODE).name(UPDATED_NAME);

        restRequestTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequestType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRequestType))
            )
            .andExpect(status().isOk());

        // Validate the RequestType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRequestTypeToMatchAllProperties(updatedRequestType);
    }

    @Test
    @Transactional
    void putNonExistingRequestType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestTypeWithPatch() throws Exception {
        // Initialize the database
        insertedRequestType = requestTypeRepository.saveAndFlush(requestType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestType using partial update
        RequestType partialUpdatedRequestType = new RequestType();
        partialUpdatedRequestType.setId(requestType.getId());

        partialUpdatedRequestType.code(UPDATED_CODE).name(UPDATED_NAME);

        restRequestTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestType))
            )
            .andExpect(status().isOk());

        // Validate the RequestType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRequestType, requestType),
            getPersistedRequestType(requestType)
        );
    }

    @Test
    @Transactional
    void fullUpdateRequestTypeWithPatch() throws Exception {
        // Initialize the database
        insertedRequestType = requestTypeRepository.saveAndFlush(requestType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestType using partial update
        RequestType partialUpdatedRequestType = new RequestType();
        partialUpdatedRequestType.setId(requestType.getId());

        partialUpdatedRequestType.code(UPDATED_CODE).name(UPDATED_NAME);

        restRequestTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestType))
            )
            .andExpect(status().isOk());

        // Validate the RequestType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestTypeUpdatableFieldsEquals(partialUpdatedRequestType, getPersistedRequestType(partialUpdatedRequestType));
    }

    @Test
    @Transactional
    void patchNonExistingRequestType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(requestType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestType() throws Exception {
        // Initialize the database
        insertedRequestType = requestTypeRepository.saveAndFlush(requestType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the requestType
        restRequestTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return requestTypeRepository.count();
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

    protected RequestType getPersistedRequestType(RequestType requestType) {
        return requestTypeRepository.findById(requestType.getId()).orElseThrow();
    }

    protected void assertPersistedRequestTypeToMatchAllProperties(RequestType expectedRequestType) {
        assertRequestTypeAllPropertiesEquals(expectedRequestType, getPersistedRequestType(expectedRequestType));
    }

    protected void assertPersistedRequestTypeToMatchUpdatableProperties(RequestType expectedRequestType) {
        assertRequestTypeAllUpdatablePropertiesEquals(expectedRequestType, getPersistedRequestType(expectedRequestType));
    }
}
