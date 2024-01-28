package com.bami.ef.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.bami.ef.gateway.IntegrationTest;
import com.bami.ef.gateway.domain.DataTreeRoot;
import com.bami.ef.gateway.domain.enumeration.StereoTypeEnum;
import com.bami.ef.gateway.repository.DataTreeRootRepository;
import com.bami.ef.gateway.repository.EntityManager;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

/**
 * Integration tests for the {@link DataTreeRootResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
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
    private WebTestClient webTestClient;

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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll("rel_data_tree_root__root_to_field").block();
            em.deleteAll(DataTreeRoot.class).block();
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
        dataTreeRoot = createEntity(em);
    }

    @Test
    void createDataTreeRoot() throws Exception {
        int databaseSizeBeforeCreate = dataTreeRootRepository.findAll().collectList().block().size();
        // Create the DataTreeRoot
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeCreate + 1);
        DataTreeRoot testDataTreeRoot = dataTreeRootList.get(dataTreeRootList.size() - 1);
        assertThat(testDataTreeRoot.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeRoot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeRoot.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeRoot.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void createDataTreeRootWithExistingId() throws Exception {
        // Create the DataTreeRoot with an existing ID
        dataTreeRoot.setId(1L);

        int databaseSizeBeforeCreate = dataTreeRootRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDataTreeRootsAsStream() {
        // Initialize the database
        dataTreeRootRepository.save(dataTreeRoot).block();

        List<DataTreeRoot> dataTreeRootList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DataTreeRoot.class)
            .getResponseBody()
            .filter(dataTreeRoot::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(dataTreeRootList).isNotNull();
        assertThat(dataTreeRootList).hasSize(1);
        DataTreeRoot testDataTreeRoot = dataTreeRootList.get(0);
        assertThat(testDataTreeRoot.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeRoot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeRoot.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeRoot.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void getAllDataTreeRoots() {
        // Initialize the database
        dataTreeRootRepository.save(dataTreeRoot).block();

        // Get all the dataTreeRootList
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
            .value(hasItem(dataTreeRoot.getId().intValue()))
            .jsonPath("$.[*].stereoType")
            .value(hasItem(DEFAULT_STEREO_TYPE.toString()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].caption")
            .value(hasItem(DEFAULT_CAPTION))
            .jsonPath("$.[*].documentation")
            .value(hasItem(DEFAULT_DOCUMENTATION));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDataTreeRootsWithEagerRelationshipsIsEnabled() {
        when(dataTreeRootRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(dataTreeRootRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDataTreeRootsWithEagerRelationshipsIsNotEnabled() {
        when(dataTreeRootRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(dataTreeRootRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getDataTreeRoot() {
        // Initialize the database
        dataTreeRootRepository.save(dataTreeRoot).block();

        // Get the dataTreeRoot
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, dataTreeRoot.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(dataTreeRoot.getId().intValue()))
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
    void getNonExistingDataTreeRoot() {
        // Get the dataTreeRoot
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDataTreeRoot() throws Exception {
        // Initialize the database
        dataTreeRootRepository.save(dataTreeRoot).block();

        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().collectList().block().size();

        // Update the dataTreeRoot
        DataTreeRoot updatedDataTreeRoot = dataTreeRootRepository.findById(dataTreeRoot.getId()).block();
        updatedDataTreeRoot
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedDataTreeRoot.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedDataTreeRoot))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
        DataTreeRoot testDataTreeRoot = dataTreeRootList.get(dataTreeRootList.size() - 1);
        assertThat(testDataTreeRoot.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeRoot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeRoot.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeRoot.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void putNonExistingDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().collectList().block().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, dataTreeRoot.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().collectList().block().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().collectList().block().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDataTreeRootWithPatch() throws Exception {
        // Initialize the database
        dataTreeRootRepository.save(dataTreeRoot).block();

        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().collectList().block().size();

        // Update the dataTreeRoot using partial update
        DataTreeRoot partialUpdatedDataTreeRoot = new DataTreeRoot();
        partialUpdatedDataTreeRoot.setId(dataTreeRoot.getId());

        partialUpdatedDataTreeRoot.name(UPDATED_NAME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataTreeRoot.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeRoot))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
        DataTreeRoot testDataTreeRoot = dataTreeRootList.get(dataTreeRootList.size() - 1);
        assertThat(testDataTreeRoot.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeRoot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeRoot.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeRoot.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void fullUpdateDataTreeRootWithPatch() throws Exception {
        // Initialize the database
        dataTreeRootRepository.save(dataTreeRoot).block();

        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().collectList().block().size();

        // Update the dataTreeRoot using partial update
        DataTreeRoot partialUpdatedDataTreeRoot = new DataTreeRoot();
        partialUpdatedDataTreeRoot.setId(dataTreeRoot.getId());

        partialUpdatedDataTreeRoot
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataTreeRoot.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeRoot))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
        DataTreeRoot testDataTreeRoot = dataTreeRootList.get(dataTreeRootList.size() - 1);
        assertThat(testDataTreeRoot.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeRoot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeRoot.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeRoot.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void patchNonExistingDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().collectList().block().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, dataTreeRoot.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().collectList().block().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDataTreeRoot() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeRootRepository.findAll().collectList().block().size();
        dataTreeRoot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeRoot))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataTreeRoot in the database
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDataTreeRoot() {
        // Initialize the database
        dataTreeRootRepository.save(dataTreeRoot).block();

        int databaseSizeBeforeDelete = dataTreeRootRepository.findAll().collectList().block().size();

        // Delete the dataTreeRoot
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, dataTreeRoot.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<DataTreeRoot> dataTreeRootList = dataTreeRootRepository.findAll().collectList().block();
        assertThat(dataTreeRootList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
