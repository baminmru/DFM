package com.bami.tent.request.web.rest;

import static com.bami.tent.request.domain.RequestContentAsserts.*;
import static com.bami.tent.request.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.tent.request.IntegrationTest;
import com.bami.tent.request.domain.RequestContent;
import com.bami.tent.request.repository.RequestContentRepository;
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
 * Integration tests for the {@link RequestContentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestContentResourceIT {

    private static final String DEFAULT_PARAM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/request-contents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RequestContentRepository requestContentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestContentMockMvc;

    private RequestContent requestContent;

    private RequestContent insertedRequestContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestContent createEntity(EntityManager em) {
        RequestContent requestContent = new RequestContent().paramCode(DEFAULT_PARAM_CODE).paramValue(DEFAULT_PARAM_VALUE);
        return requestContent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestContent createUpdatedEntity(EntityManager em) {
        RequestContent requestContent = new RequestContent().paramCode(UPDATED_PARAM_CODE).paramValue(UPDATED_PARAM_VALUE);
        return requestContent;
    }

    @BeforeEach
    public void initTest() {
        requestContent = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRequestContent != null) {
            requestContentRepository.delete(insertedRequestContent);
            insertedRequestContent = null;
        }
    }

    @Test
    @Transactional
    void createRequestContent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RequestContent
        var returnedRequestContent = om.readValue(
            restRequestContentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestContent)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RequestContent.class
        );

        // Validate the RequestContent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRequestContentUpdatableFieldsEquals(returnedRequestContent, getPersistedRequestContent(returnedRequestContent));

        insertedRequestContent = returnedRequestContent;
    }

    @Test
    @Transactional
    void createRequestContentWithExistingId() throws Exception {
        // Create the RequestContent with an existing ID
        requestContent.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestContent)))
            .andExpect(status().isBadRequest());

        // Validate the RequestContent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkParamCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestContent.setParamCode(null);

        // Create the RequestContent, which fails.

        restRequestContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestContent)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequestContents() throws Exception {
        // Initialize the database
        insertedRequestContent = requestContentRepository.saveAndFlush(requestContent);

        // Get all the requestContentList
        restRequestContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].paramCode").value(hasItem(DEFAULT_PARAM_CODE)))
            .andExpect(jsonPath("$.[*].paramValue").value(hasItem(DEFAULT_PARAM_VALUE)));
    }

    @Test
    @Transactional
    void getRequestContent() throws Exception {
        // Initialize the database
        insertedRequestContent = requestContentRepository.saveAndFlush(requestContent);

        // Get the requestContent
        restRequestContentMockMvc
            .perform(get(ENTITY_API_URL_ID, requestContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestContent.getId().intValue()))
            .andExpect(jsonPath("$.paramCode").value(DEFAULT_PARAM_CODE))
            .andExpect(jsonPath("$.paramValue").value(DEFAULT_PARAM_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingRequestContent() throws Exception {
        // Get the requestContent
        restRequestContentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestContent() throws Exception {
        // Initialize the database
        insertedRequestContent = requestContentRepository.saveAndFlush(requestContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestContent
        RequestContent updatedRequestContent = requestContentRepository.findById(requestContent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRequestContent are not directly saved in db
        em.detach(updatedRequestContent);
        updatedRequestContent.paramCode(UPDATED_PARAM_CODE).paramValue(UPDATED_PARAM_VALUE);

        restRequestContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequestContent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRequestContent))
            )
            .andExpect(status().isOk());

        // Validate the RequestContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRequestContentToMatchAllProperties(updatedRequestContent);
    }

    @Test
    @Transactional
    void putNonExistingRequestContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContent.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestContent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestContent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestContent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestContentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestContent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestContentWithPatch() throws Exception {
        // Initialize the database
        insertedRequestContent = requestContentRepository.saveAndFlush(requestContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestContent using partial update
        RequestContent partialUpdatedRequestContent = new RequestContent();
        partialUpdatedRequestContent.setId(requestContent.getId());

        partialUpdatedRequestContent.paramCode(UPDATED_PARAM_CODE);

        restRequestContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestContent))
            )
            .andExpect(status().isOk());

        // Validate the RequestContent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestContentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRequestContent, requestContent),
            getPersistedRequestContent(requestContent)
        );
    }

    @Test
    @Transactional
    void fullUpdateRequestContentWithPatch() throws Exception {
        // Initialize the database
        insertedRequestContent = requestContentRepository.saveAndFlush(requestContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestContent using partial update
        RequestContent partialUpdatedRequestContent = new RequestContent();
        partialUpdatedRequestContent.setId(requestContent.getId());

        partialUpdatedRequestContent.paramCode(UPDATED_PARAM_CODE).paramValue(UPDATED_PARAM_VALUE);

        restRequestContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestContent))
            )
            .andExpect(status().isOk());

        // Validate the RequestContent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestContentUpdatableFieldsEquals(partialUpdatedRequestContent, getPersistedRequestContent(partialUpdatedRequestContent));
    }

    @Test
    @Transactional
    void patchNonExistingRequestContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContent.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestContent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestContent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestContent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestContentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(requestContent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestContent() throws Exception {
        // Initialize the database
        insertedRequestContent = requestContentRepository.saveAndFlush(requestContent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the requestContent
        restRequestContentMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestContent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return requestContentRepository.count();
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

    protected RequestContent getPersistedRequestContent(RequestContent requestContent) {
        return requestContentRepository.findById(requestContent.getId()).orElseThrow();
    }

    protected void assertPersistedRequestContentToMatchAllProperties(RequestContent expectedRequestContent) {
        assertRequestContentAllPropertiesEquals(expectedRequestContent, getPersistedRequestContent(expectedRequestContent));
    }

    protected void assertPersistedRequestContentToMatchUpdatableProperties(RequestContent expectedRequestContent) {
        assertRequestContentAllUpdatablePropertiesEquals(expectedRequestContent, getPersistedRequestContent(expectedRequestContent));
    }
}
