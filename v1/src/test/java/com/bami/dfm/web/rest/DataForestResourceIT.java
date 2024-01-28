package com.bami.dfm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.bami.dfm.IntegrationTest;
import com.bami.dfm.domain.DataForest;
import com.bami.dfm.repository.DataForestRepository;
import com.bami.dfm.repository.EntityManager;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link DataForestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DataForestResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-forests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataForestRepository dataForestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private DataForest dataForest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataForest createEntity(EntityManager em) {
        DataForest dataForest = new DataForest().name(DEFAULT_NAME).caption(DEFAULT_CAPTION).documentation(DEFAULT_DOCUMENTATION);
        return dataForest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataForest createUpdatedEntity(EntityManager em) {
        DataForest dataForest = new DataForest().name(UPDATED_NAME).caption(UPDATED_CAPTION).documentation(UPDATED_DOCUMENTATION);
        return dataForest;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(DataForest.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        dataForest = createEntity(em);
    }

    @Test
    void createDataForest() throws Exception {
        int databaseSizeBeforeCreate = dataForestRepository.findAll().collectList().block().size();
        // Create the DataForest
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataForest))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeCreate + 1);
        DataForest testDataForest = dataForestList.get(dataForestList.size() - 1);
        assertThat(testDataForest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataForest.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataForest.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void createDataForestWithExistingId() throws Exception {
        // Create the DataForest with an existing ID
        dataForest.setId(1L);

        int databaseSizeBeforeCreate = dataForestRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataForest))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDataForestsAsStream() {
        // Initialize the database
        dataForestRepository.save(dataForest).block();

        List<DataForest> dataForestList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DataForest.class)
            .getResponseBody()
            .filter(dataForest::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(dataForestList).isNotNull();
        assertThat(dataForestList).hasSize(1);
        DataForest testDataForest = dataForestList.get(0);
        assertThat(testDataForest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataForest.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataForest.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void getAllDataForests() {
        // Initialize the database
        dataForestRepository.save(dataForest).block();

        // Get all the dataForestList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(dataForest.getId().intValue()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].caption")
            .value(hasItem(DEFAULT_CAPTION))
            .jsonPath("$.[*].documentation")
            .value(hasItem(DEFAULT_DOCUMENTATION));
    }

    @Test
    void getDataForest() {
        // Initialize the database
        dataForestRepository.save(dataForest).block();

        // Get the dataForest
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, dataForest.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(dataForest.getId().intValue()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.caption")
            .value(is(DEFAULT_CAPTION))
            .jsonPath("$.documentation")
            .value(is(DEFAULT_DOCUMENTATION));
    }

    @Test
    void getNonExistingDataForest() {
        // Get the dataForest
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDataForest() throws Exception {
        // Initialize the database
        dataForestRepository.save(dataForest).block();

        int databaseSizeBeforeUpdate = dataForestRepository.findAll().collectList().block().size();

        // Update the dataForest
        DataForest updatedDataForest = dataForestRepository.findById(dataForest.getId()).block();
        updatedDataForest.name(UPDATED_NAME).caption(UPDATED_CAPTION).documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedDataForest.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedDataForest))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
        DataForest testDataForest = dataForestList.get(dataForestList.size() - 1);
        assertThat(testDataForest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataForest.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataForest.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void putNonExistingDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().collectList().block().size();
        dataForest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, dataForest.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataForest))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().collectList().block().size();
        dataForest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataForest))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().collectList().block().size();
        dataForest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataForest))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDataForestWithPatch() throws Exception {
        // Initialize the database
        dataForestRepository.save(dataForest).block();

        int databaseSizeBeforeUpdate = dataForestRepository.findAll().collectList().block().size();

        // Update the dataForest using partial update
        DataForest partialUpdatedDataForest = new DataForest();
        partialUpdatedDataForest.setId(dataForest.getId());

        partialUpdatedDataForest.name(UPDATED_NAME).documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataForest.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataForest))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
        DataForest testDataForest = dataForestList.get(dataForestList.size() - 1);
        assertThat(testDataForest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataForest.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataForest.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void fullUpdateDataForestWithPatch() throws Exception {
        // Initialize the database
        dataForestRepository.save(dataForest).block();

        int databaseSizeBeforeUpdate = dataForestRepository.findAll().collectList().block().size();

        // Update the dataForest using partial update
        DataForest partialUpdatedDataForest = new DataForest();
        partialUpdatedDataForest.setId(dataForest.getId());

        partialUpdatedDataForest.name(UPDATED_NAME).caption(UPDATED_CAPTION).documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataForest.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataForest))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
        DataForest testDataForest = dataForestList.get(dataForestList.size() - 1);
        assertThat(testDataForest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataForest.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataForest.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void patchNonExistingDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().collectList().block().size();
        dataForest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, dataForest.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataForest))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().collectList().block().size();
        dataForest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataForest))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDataForest() throws Exception {
        int databaseSizeBeforeUpdate = dataForestRepository.findAll().collectList().block().size();
        dataForest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataForest))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataForest in the database
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDataForest() {
        // Initialize the database
        dataForestRepository.save(dataForest).block();

        int databaseSizeBeforeDelete = dataForestRepository.findAll().collectList().block().size();

        // Delete the dataForest
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, dataForest.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<DataForest> dataForestList = dataForestRepository.findAll().collectList().block();
        assertThat(dataForestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
