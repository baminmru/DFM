package com.bami.tent.request.web.rest;

import static com.bami.tent.request.domain.RequestInfoAsserts.*;
import static com.bami.tent.request.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.tent.request.IntegrationTest;
import com.bami.tent.request.domain.RequestInfo;
import com.bami.tent.request.repository.RequestInfoRepository;
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
 * Integration tests for the {@link RequestInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestInfoResourceIT {

    private static final Integer DEFAULT_CONTRACT = 1;
    private static final Integer UPDATED_CONTRACT = 2;

    private static final LocalDate DEFAULT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_AT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_AT_SOURCE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/request-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RequestInfoRepository requestInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestInfoMockMvc;

    private RequestInfo requestInfo;

    private RequestInfo insertedRequestInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestInfo createEntity(EntityManager em) {
        RequestInfo requestInfo = new RequestInfo()
            .contract(DEFAULT_CONTRACT)
            .requestDate(DEFAULT_REQUEST_DATE)
            .codeAtSource(DEFAULT_CODE_AT_SOURCE)
            .effectiveDateStart(DEFAULT_EFFECTIVE_DATE_START)
            .effectiveDateEnd(DEFAULT_EFFECTIVE_DATE_END)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY);
        return requestInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestInfo createUpdatedEntity(EntityManager em) {
        RequestInfo requestInfo = new RequestInfo()
            .contract(UPDATED_CONTRACT)
            .requestDate(UPDATED_REQUEST_DATE)
            .codeAtSource(UPDATED_CODE_AT_SOURCE)
            .effectiveDateStart(UPDATED_EFFECTIVE_DATE_START)
            .effectiveDateEnd(UPDATED_EFFECTIVE_DATE_END)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);
        return requestInfo;
    }

    @BeforeEach
    public void initTest() {
        requestInfo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRequestInfo != null) {
            requestInfoRepository.delete(insertedRequestInfo);
            insertedRequestInfo = null;
        }
    }

    @Test
    @Transactional
    void createRequestInfo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RequestInfo
        var returnedRequestInfo = om.readValue(
            restRequestInfoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInfo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RequestInfo.class
        );

        // Validate the RequestInfo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRequestInfoUpdatableFieldsEquals(returnedRequestInfo, getPersistedRequestInfo(returnedRequestInfo));

        insertedRequestInfo = returnedRequestInfo;
    }

    @Test
    @Transactional
    void createRequestInfoWithExistingId() throws Exception {
        // Create the RequestInfo with an existing ID
        requestInfo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInfo)))
            .andExpect(status().isBadRequest());

        // Validate the RequestInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkContractIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestInfo.setContract(null);

        // Create the RequestInfo, which fails.

        restRequestInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInfo)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestInfo.setRequestDate(null);

        // Create the RequestInfo, which fails.

        restRequestInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInfo)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeAtSourceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestInfo.setCodeAtSource(null);

        // Create the RequestInfo, which fails.

        restRequestInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInfo)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequestInfos() throws Exception {
        // Initialize the database
        insertedRequestInfo = requestInfoRepository.saveAndFlush(requestInfo);

        // Get all the requestInfoList
        restRequestInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].contract").value(hasItem(DEFAULT_CONTRACT)))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].codeAtSource").value(hasItem(DEFAULT_CODE_AT_SOURCE)))
            .andExpect(jsonPath("$.[*].effectiveDateStart").value(hasItem(DEFAULT_EFFECTIVE_DATE_START.toString())))
            .andExpect(jsonPath("$.[*].effectiveDateEnd").value(hasItem(DEFAULT_EFFECTIVE_DATE_END.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getRequestInfo() throws Exception {
        // Initialize the database
        insertedRequestInfo = requestInfoRepository.saveAndFlush(requestInfo);

        // Get the requestInfo
        restRequestInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, requestInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestInfo.getId().intValue()))
            .andExpect(jsonPath("$.contract").value(DEFAULT_CONTRACT))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.codeAtSource").value(DEFAULT_CODE_AT_SOURCE))
            .andExpect(jsonPath("$.effectiveDateStart").value(DEFAULT_EFFECTIVE_DATE_START.toString()))
            .andExpect(jsonPath("$.effectiveDateEnd").value(DEFAULT_EFFECTIVE_DATE_END.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingRequestInfo() throws Exception {
        // Get the requestInfo
        restRequestInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestInfo() throws Exception {
        // Initialize the database
        insertedRequestInfo = requestInfoRepository.saveAndFlush(requestInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestInfo
        RequestInfo updatedRequestInfo = requestInfoRepository.findById(requestInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRequestInfo are not directly saved in db
        em.detach(updatedRequestInfo);
        updatedRequestInfo
            .contract(UPDATED_CONTRACT)
            .requestDate(UPDATED_REQUEST_DATE)
            .codeAtSource(UPDATED_CODE_AT_SOURCE)
            .effectiveDateStart(UPDATED_EFFECTIVE_DATE_START)
            .effectiveDateEnd(UPDATED_EFFECTIVE_DATE_END)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restRequestInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequestInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRequestInfo))
            )
            .andExpect(status().isOk());

        // Validate the RequestInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRequestInfoToMatchAllProperties(updatedRequestInfo);
    }

    @Test
    @Transactional
    void putNonExistingRequestInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInfo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInfo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInfo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestInfoWithPatch() throws Exception {
        // Initialize the database
        insertedRequestInfo = requestInfoRepository.saveAndFlush(requestInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestInfo using partial update
        RequestInfo partialUpdatedRequestInfo = new RequestInfo();
        partialUpdatedRequestInfo.setId(requestInfo.getId());

        partialUpdatedRequestInfo
            .contract(UPDATED_CONTRACT)
            .requestDate(UPDATED_REQUEST_DATE)
            .codeAtSource(UPDATED_CODE_AT_SOURCE)
            .effectiveDateEnd(UPDATED_EFFECTIVE_DATE_END)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restRequestInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestInfo))
            )
            .andExpect(status().isOk());

        // Validate the RequestInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestInfoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRequestInfo, requestInfo),
            getPersistedRequestInfo(requestInfo)
        );
    }

    @Test
    @Transactional
    void fullUpdateRequestInfoWithPatch() throws Exception {
        // Initialize the database
        insertedRequestInfo = requestInfoRepository.saveAndFlush(requestInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestInfo using partial update
        RequestInfo partialUpdatedRequestInfo = new RequestInfo();
        partialUpdatedRequestInfo.setId(requestInfo.getId());

        partialUpdatedRequestInfo
            .contract(UPDATED_CONTRACT)
            .requestDate(UPDATED_REQUEST_DATE)
            .codeAtSource(UPDATED_CODE_AT_SOURCE)
            .effectiveDateStart(UPDATED_EFFECTIVE_DATE_START)
            .effectiveDateEnd(UPDATED_EFFECTIVE_DATE_END)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restRequestInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestInfo))
            )
            .andExpect(status().isOk());

        // Validate the RequestInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestInfoUpdatableFieldsEquals(partialUpdatedRequestInfo, getPersistedRequestInfo(partialUpdatedRequestInfo));
    }

    @Test
    @Transactional
    void patchNonExistingRequestInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInfo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInfo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInfo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestInfoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(requestInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestInfo() throws Exception {
        // Initialize the database
        insertedRequestInfo = requestInfoRepository.saveAndFlush(requestInfo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the requestInfo
        restRequestInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return requestInfoRepository.count();
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

    protected RequestInfo getPersistedRequestInfo(RequestInfo requestInfo) {
        return requestInfoRepository.findById(requestInfo.getId()).orElseThrow();
    }

    protected void assertPersistedRequestInfoToMatchAllProperties(RequestInfo expectedRequestInfo) {
        assertRequestInfoAllPropertiesEquals(expectedRequestInfo, getPersistedRequestInfo(expectedRequestInfo));
    }

    protected void assertPersistedRequestInfoToMatchUpdatableProperties(RequestInfo expectedRequestInfo) {
        assertRequestInfoAllUpdatablePropertiesEquals(expectedRequestInfo, getPersistedRequestInfo(expectedRequestInfo));
    }
}
