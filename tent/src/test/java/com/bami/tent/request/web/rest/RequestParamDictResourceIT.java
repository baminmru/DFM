package com.bami.tent.request.web.rest;

import static com.bami.tent.request.domain.RequestParamDictAsserts.*;
import static com.bami.tent.request.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.tent.request.IntegrationTest;
import com.bami.tent.request.domain.RequestParamDict;
import com.bami.tent.request.repository.RequestParamDictRepository;
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
 * Integration tests for the {@link RequestParamDictResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestParamDictResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMTYPE = "AAAAAAAAAA";
    private static final String UPDATED_PARAMTYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALUE_ARRAY = false;
    private static final Boolean UPDATED_VALUE_ARRAY = true;

    private static final String DEFAULT_REFERENCE_TO = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_TO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/request-param-dicts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RequestParamDictRepository requestParamDictRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestParamDictMockMvc;

    private RequestParamDict requestParamDict;

    private RequestParamDict insertedRequestParamDict;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestParamDict createEntity(EntityManager em) {
        RequestParamDict requestParamDict = new RequestParamDict()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .paramtype(DEFAULT_PARAMTYPE)
            .valueArray(DEFAULT_VALUE_ARRAY)
            .referenceTo(DEFAULT_REFERENCE_TO)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY);
        return requestParamDict;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestParamDict createUpdatedEntity(EntityManager em) {
        RequestParamDict requestParamDict = new RequestParamDict()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .paramtype(UPDATED_PARAMTYPE)
            .valueArray(UPDATED_VALUE_ARRAY)
            .referenceTo(UPDATED_REFERENCE_TO)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);
        return requestParamDict;
    }

    @BeforeEach
    public void initTest() {
        requestParamDict = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRequestParamDict != null) {
            requestParamDictRepository.delete(insertedRequestParamDict);
            insertedRequestParamDict = null;
        }
    }

    @Test
    @Transactional
    void createRequestParamDict() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RequestParamDict
        var returnedRequestParamDict = om.readValue(
            restRequestParamDictMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestParamDict)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RequestParamDict.class
        );

        // Validate the RequestParamDict in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRequestParamDictUpdatableFieldsEquals(returnedRequestParamDict, getPersistedRequestParamDict(returnedRequestParamDict));

        insertedRequestParamDict = returnedRequestParamDict;
    }

    @Test
    @Transactional
    void createRequestParamDictWithExistingId() throws Exception {
        // Create the RequestParamDict with an existing ID
        requestParamDict.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestParamDictMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestParamDict)))
            .andExpect(status().isBadRequest());

        // Validate the RequestParamDict in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestParamDict.setCode(null);

        // Create the RequestParamDict, which fails.

        restRequestParamDictMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestParamDict)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestParamDict.setName(null);

        // Create the RequestParamDict, which fails.

        restRequestParamDictMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestParamDict)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequestParamDicts() throws Exception {
        // Initialize the database
        insertedRequestParamDict = requestParamDictRepository.saveAndFlush(requestParamDict);

        // Get all the requestParamDictList
        restRequestParamDictMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestParamDict.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].paramtype").value(hasItem(DEFAULT_PARAMTYPE)))
            .andExpect(jsonPath("$.[*].valueArray").value(hasItem(DEFAULT_VALUE_ARRAY.booleanValue())))
            .andExpect(jsonPath("$.[*].referenceTo").value(hasItem(DEFAULT_REFERENCE_TO)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getRequestParamDict() throws Exception {
        // Initialize the database
        insertedRequestParamDict = requestParamDictRepository.saveAndFlush(requestParamDict);

        // Get the requestParamDict
        restRequestParamDictMockMvc
            .perform(get(ENTITY_API_URL_ID, requestParamDict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestParamDict.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.paramtype").value(DEFAULT_PARAMTYPE))
            .andExpect(jsonPath("$.valueArray").value(DEFAULT_VALUE_ARRAY.booleanValue()))
            .andExpect(jsonPath("$.referenceTo").value(DEFAULT_REFERENCE_TO))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingRequestParamDict() throws Exception {
        // Get the requestParamDict
        restRequestParamDictMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestParamDict() throws Exception {
        // Initialize the database
        insertedRequestParamDict = requestParamDictRepository.saveAndFlush(requestParamDict);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestParamDict
        RequestParamDict updatedRequestParamDict = requestParamDictRepository.findById(requestParamDict.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRequestParamDict are not directly saved in db
        em.detach(updatedRequestParamDict);
        updatedRequestParamDict
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .paramtype(UPDATED_PARAMTYPE)
            .valueArray(UPDATED_VALUE_ARRAY)
            .referenceTo(UPDATED_REFERENCE_TO)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restRequestParamDictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequestParamDict.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRequestParamDict))
            )
            .andExpect(status().isOk());

        // Validate the RequestParamDict in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRequestParamDictToMatchAllProperties(updatedRequestParamDict);
    }

    @Test
    @Transactional
    void putNonExistingRequestParamDict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestParamDict.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestParamDictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestParamDict.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestParamDict))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestParamDict in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestParamDict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestParamDict.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestParamDictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestParamDict))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestParamDict in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestParamDict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestParamDict.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestParamDictMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestParamDict)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestParamDict in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestParamDictWithPatch() throws Exception {
        // Initialize the database
        insertedRequestParamDict = requestParamDictRepository.saveAndFlush(requestParamDict);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestParamDict using partial update
        RequestParamDict partialUpdatedRequestParamDict = new RequestParamDict();
        partialUpdatedRequestParamDict.setId(requestParamDict.getId());

        partialUpdatedRequestParamDict.name(UPDATED_NAME).updatedBy(UPDATED_UPDATED_BY);

        restRequestParamDictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestParamDict.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestParamDict))
            )
            .andExpect(status().isOk());

        // Validate the RequestParamDict in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestParamDictUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRequestParamDict, requestParamDict),
            getPersistedRequestParamDict(requestParamDict)
        );
    }

    @Test
    @Transactional
    void fullUpdateRequestParamDictWithPatch() throws Exception {
        // Initialize the database
        insertedRequestParamDict = requestParamDictRepository.saveAndFlush(requestParamDict);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestParamDict using partial update
        RequestParamDict partialUpdatedRequestParamDict = new RequestParamDict();
        partialUpdatedRequestParamDict.setId(requestParamDict.getId());

        partialUpdatedRequestParamDict
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .paramtype(UPDATED_PARAMTYPE)
            .valueArray(UPDATED_VALUE_ARRAY)
            .referenceTo(UPDATED_REFERENCE_TO)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restRequestParamDictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestParamDict.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestParamDict))
            )
            .andExpect(status().isOk());

        // Validate the RequestParamDict in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestParamDictUpdatableFieldsEquals(
            partialUpdatedRequestParamDict,
            getPersistedRequestParamDict(partialUpdatedRequestParamDict)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRequestParamDict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestParamDict.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestParamDictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestParamDict.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestParamDict))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestParamDict in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestParamDict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestParamDict.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestParamDictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestParamDict))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestParamDict in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestParamDict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestParamDict.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestParamDictMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(requestParamDict)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestParamDict in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestParamDict() throws Exception {
        // Initialize the database
        insertedRequestParamDict = requestParamDictRepository.saveAndFlush(requestParamDict);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the requestParamDict
        restRequestParamDictMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestParamDict.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return requestParamDictRepository.count();
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

    protected RequestParamDict getPersistedRequestParamDict(RequestParamDict requestParamDict) {
        return requestParamDictRepository.findById(requestParamDict.getId()).orElseThrow();
    }

    protected void assertPersistedRequestParamDictToMatchAllProperties(RequestParamDict expectedRequestParamDict) {
        assertRequestParamDictAllPropertiesEquals(expectedRequestParamDict, getPersistedRequestParamDict(expectedRequestParamDict));
    }

    protected void assertPersistedRequestParamDictToMatchUpdatableProperties(RequestParamDict expectedRequestParamDict) {
        assertRequestParamDictAllUpdatablePropertiesEquals(
            expectedRequestParamDict,
            getPersistedRequestParamDict(expectedRequestParamDict)
        );
    }
}
