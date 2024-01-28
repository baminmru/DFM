package com.bami.dfm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.bami.dfm.IntegrationTest;
import com.bami.dfm.domain.DataTreeLeaf;
import com.bami.dfm.domain.enumeration.StereoTypeEnum;
import com.bami.dfm.repository.DataTreeLeafRepository;
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
 * Integration tests for the {@link DataTreeLeafResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DataTreeLeafResourceIT {

    private static final StereoTypeEnum DEFAULT_STEREO_TYPE = StereoTypeEnum.SINGLE_ROW;
    private static final StereoTypeEnum UPDATED_STEREO_TYPE = StereoTypeEnum.COLLECTION;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-tree-leaves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataTreeLeafRepository dataTreeLeafRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private DataTreeLeaf dataTreeLeaf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeLeaf createEntity(EntityManager em) {
        DataTreeLeaf dataTreeLeaf = new DataTreeLeaf()
            .stereoType(DEFAULT_STEREO_TYPE)
            .name(DEFAULT_NAME)
            .caption(DEFAULT_CAPTION)
            .documentation(DEFAULT_DOCUMENTATION);
        return dataTreeLeaf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeLeaf createUpdatedEntity(EntityManager em) {
        DataTreeLeaf dataTreeLeaf = new DataTreeLeaf()
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);
        return dataTreeLeaf;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(DataTreeLeaf.class).block();
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
        dataTreeLeaf = createEntity(em);
    }

    @Test
    void createDataTreeLeaf() throws Exception {
        int databaseSizeBeforeCreate = dataTreeLeafRepository.findAll().collectList().block().size();
        // Create the DataTreeLeaf
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeCreate + 1);
        DataTreeLeaf testDataTreeLeaf = dataTreeLeafList.get(dataTreeLeafList.size() - 1);
        assertThat(testDataTreeLeaf.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeLeaf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeLeaf.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeLeaf.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void createDataTreeLeafWithExistingId() throws Exception {
        // Create the DataTreeLeaf with an existing ID
        dataTreeLeaf.setId(1L);

        int databaseSizeBeforeCreate = dataTreeLeafRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkStereoTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataTreeLeafRepository.findAll().collectList().block().size();
        // set the field null
        dataTreeLeaf.setStereoType(null);

        // Create the DataTreeLeaf, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataTreeLeafRepository.findAll().collectList().block().size();
        // set the field null
        dataTreeLeaf.setName(null);

        // Create the DataTreeLeaf, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCaptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataTreeLeafRepository.findAll().collectList().block().size();
        // set the field null
        dataTreeLeaf.setCaption(null);

        // Create the DataTreeLeaf, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllDataTreeLeavesAsStream() {
        // Initialize the database
        dataTreeLeafRepository.save(dataTreeLeaf).block();

        List<DataTreeLeaf> dataTreeLeafList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DataTreeLeaf.class)
            .getResponseBody()
            .filter(dataTreeLeaf::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(dataTreeLeafList).isNotNull();
        assertThat(dataTreeLeafList).hasSize(1);
        DataTreeLeaf testDataTreeLeaf = dataTreeLeafList.get(0);
        assertThat(testDataTreeLeaf.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeLeaf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeLeaf.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeLeaf.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void getAllDataTreeLeaves() {
        // Initialize the database
        dataTreeLeafRepository.save(dataTreeLeaf).block();

        // Get all the dataTreeLeafList
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
            .value(hasItem(dataTreeLeaf.getId().intValue()))
            .jsonPath("$.[*].stereoType")
            .value(hasItem(DEFAULT_STEREO_TYPE.toString()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].caption")
            .value(hasItem(DEFAULT_CAPTION))
            .jsonPath("$.[*].documentation")
            .value(hasItem(DEFAULT_DOCUMENTATION));
    }

    @Test
    void getDataTreeLeaf() {
        // Initialize the database
        dataTreeLeafRepository.save(dataTreeLeaf).block();

        // Get the dataTreeLeaf
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, dataTreeLeaf.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(dataTreeLeaf.getId().intValue()))
            .jsonPath("$.stereoType")
            .value(is(DEFAULT_STEREO_TYPE.toString()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.caption")
            .value(is(DEFAULT_CAPTION))
            .jsonPath("$.documentation")
            .value(is(DEFAULT_DOCUMENTATION));
    }

    @Test
    void getNonExistingDataTreeLeaf() {
        // Get the dataTreeLeaf
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDataTreeLeaf() throws Exception {
        // Initialize the database
        dataTreeLeafRepository.save(dataTreeLeaf).block();

        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().collectList().block().size();

        // Update the dataTreeLeaf
        DataTreeLeaf updatedDataTreeLeaf = dataTreeLeafRepository.findById(dataTreeLeaf.getId()).block();
        updatedDataTreeLeaf
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedDataTreeLeaf.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedDataTreeLeaf))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
        DataTreeLeaf testDataTreeLeaf = dataTreeLeafList.get(dataTreeLeafList.size() - 1);
        assertThat(testDataTreeLeaf.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeLeaf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeLeaf.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeLeaf.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void putNonExistingDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().collectList().block().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, dataTreeLeaf.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().collectList().block().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().collectList().block().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDataTreeLeafWithPatch() throws Exception {
        // Initialize the database
        dataTreeLeafRepository.save(dataTreeLeaf).block();

        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().collectList().block().size();

        // Update the dataTreeLeaf using partial update
        DataTreeLeaf partialUpdatedDataTreeLeaf = new DataTreeLeaf();
        partialUpdatedDataTreeLeaf.setId(dataTreeLeaf.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataTreeLeaf.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeLeaf))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
        DataTreeLeaf testDataTreeLeaf = dataTreeLeafList.get(dataTreeLeafList.size() - 1);
        assertThat(testDataTreeLeaf.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeLeaf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeLeaf.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeLeaf.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void fullUpdateDataTreeLeafWithPatch() throws Exception {
        // Initialize the database
        dataTreeLeafRepository.save(dataTreeLeaf).block();

        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().collectList().block().size();

        // Update the dataTreeLeaf using partial update
        DataTreeLeaf partialUpdatedDataTreeLeaf = new DataTreeLeaf();
        partialUpdatedDataTreeLeaf.setId(dataTreeLeaf.getId());

        partialUpdatedDataTreeLeaf
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataTreeLeaf.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeLeaf))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
        DataTreeLeaf testDataTreeLeaf = dataTreeLeafList.get(dataTreeLeafList.size() - 1);
        assertThat(testDataTreeLeaf.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeLeaf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeLeaf.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeLeaf.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void patchNonExistingDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().collectList().block().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, dataTreeLeaf.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().collectList().block().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDataTreeLeaf() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeLeafRepository.findAll().collectList().block().size();
        dataTreeLeaf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeLeaf))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataTreeLeaf in the database
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDataTreeLeaf() {
        // Initialize the database
        dataTreeLeafRepository.save(dataTreeLeaf).block();

        int databaseSizeBeforeDelete = dataTreeLeafRepository.findAll().collectList().block().size();

        // Delete the dataTreeLeaf
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, dataTreeLeaf.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<DataTreeLeaf> dataTreeLeafList = dataTreeLeafRepository.findAll().collectList().block();
        assertThat(dataTreeLeafList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
