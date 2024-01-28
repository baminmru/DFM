package com.bami.ef.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.bami.ef.gateway.IntegrationTest;
import com.bami.ef.gateway.domain.DataTreeBranch;
import com.bami.ef.gateway.domain.enumeration.StereoTypeEnum;
import com.bami.ef.gateway.repository.DataTreeBranchRepository;
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
 * Integration tests for the {@link DataTreeBranchResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DataTreeBranchResourceIT {

    private static final StereoTypeEnum DEFAULT_STEREO_TYPE = StereoTypeEnum.SINGLE_ROW;
    private static final StereoTypeEnum UPDATED_STEREO_TYPE = StereoTypeEnum.COLLECTION;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-tree-branches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataTreeBranchRepository dataTreeBranchRepository;

    @Mock
    private DataTreeBranchRepository dataTreeBranchRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private DataTreeBranch dataTreeBranch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeBranch createEntity(EntityManager em) {
        DataTreeBranch dataTreeBranch = new DataTreeBranch()
            .stereoType(DEFAULT_STEREO_TYPE)
            .name(DEFAULT_NAME)
            .caption(DEFAULT_CAPTION)
            .documentation(DEFAULT_DOCUMENTATION);
        return dataTreeBranch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataTreeBranch createUpdatedEntity(EntityManager em) {
        DataTreeBranch dataTreeBranch = new DataTreeBranch()
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);
        return dataTreeBranch;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll("rel_data_tree_branch__branch_to_field").block();
            em.deleteAll("rel_data_tree_branch__branch_parent").block();
            em.deleteAll(DataTreeBranch.class).block();
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
        dataTreeBranch = createEntity(em);
    }

    @Test
    void createDataTreeBranch() throws Exception {
        int databaseSizeBeforeCreate = dataTreeBranchRepository.findAll().collectList().block().size();
        // Create the DataTreeBranch
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeCreate + 1);
        DataTreeBranch testDataTreeBranch = dataTreeBranchList.get(dataTreeBranchList.size() - 1);
        assertThat(testDataTreeBranch.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeBranch.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeBranch.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeBranch.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void createDataTreeBranchWithExistingId() throws Exception {
        // Create the DataTreeBranch with an existing ID
        dataTreeBranch.setId(1L);

        int databaseSizeBeforeCreate = dataTreeBranchRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDataTreeBranchesAsStream() {
        // Initialize the database
        dataTreeBranchRepository.save(dataTreeBranch).block();

        List<DataTreeBranch> dataTreeBranchList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DataTreeBranch.class)
            .getResponseBody()
            .filter(dataTreeBranch::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(dataTreeBranchList).isNotNull();
        assertThat(dataTreeBranchList).hasSize(1);
        DataTreeBranch testDataTreeBranch = dataTreeBranchList.get(0);
        assertThat(testDataTreeBranch.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeBranch.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataTreeBranch.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataTreeBranch.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void getAllDataTreeBranches() {
        // Initialize the database
        dataTreeBranchRepository.save(dataTreeBranch).block();

        // Get all the dataTreeBranchList
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
            .value(hasItem(dataTreeBranch.getId().intValue()))
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
    void getAllDataTreeBranchesWithEagerRelationshipsIsEnabled() {
        when(dataTreeBranchRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(dataTreeBranchRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDataTreeBranchesWithEagerRelationshipsIsNotEnabled() {
        when(dataTreeBranchRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(dataTreeBranchRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getDataTreeBranch() {
        // Initialize the database
        dataTreeBranchRepository.save(dataTreeBranch).block();

        // Get the dataTreeBranch
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, dataTreeBranch.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(dataTreeBranch.getId().intValue()))
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
    void getNonExistingDataTreeBranch() {
        // Get the dataTreeBranch
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDataTreeBranch() throws Exception {
        // Initialize the database
        dataTreeBranchRepository.save(dataTreeBranch).block();

        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().collectList().block().size();

        // Update the dataTreeBranch
        DataTreeBranch updatedDataTreeBranch = dataTreeBranchRepository.findById(dataTreeBranch.getId()).block();
        updatedDataTreeBranch
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedDataTreeBranch.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedDataTreeBranch))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
        DataTreeBranch testDataTreeBranch = dataTreeBranchList.get(dataTreeBranchList.size() - 1);
        assertThat(testDataTreeBranch.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeBranch.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeBranch.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void putNonExistingDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().collectList().block().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, dataTreeBranch.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().collectList().block().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().collectList().block().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDataTreeBranchWithPatch() throws Exception {
        // Initialize the database
        dataTreeBranchRepository.save(dataTreeBranch).block();

        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().collectList().block().size();

        // Update the dataTreeBranch using partial update
        DataTreeBranch partialUpdatedDataTreeBranch = new DataTreeBranch();
        partialUpdatedDataTreeBranch.setId(dataTreeBranch.getId());

        partialUpdatedDataTreeBranch.name(UPDATED_NAME).caption(UPDATED_CAPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataTreeBranch.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeBranch))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
        DataTreeBranch testDataTreeBranch = dataTreeBranchList.get(dataTreeBranchList.size() - 1);
        assertThat(testDataTreeBranch.getStereoType()).isEqualTo(DEFAULT_STEREO_TYPE);
        assertThat(testDataTreeBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeBranch.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeBranch.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void fullUpdateDataTreeBranchWithPatch() throws Exception {
        // Initialize the database
        dataTreeBranchRepository.save(dataTreeBranch).block();

        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().collectList().block().size();

        // Update the dataTreeBranch using partial update
        DataTreeBranch partialUpdatedDataTreeBranch = new DataTreeBranch();
        partialUpdatedDataTreeBranch.setId(dataTreeBranch.getId());

        partialUpdatedDataTreeBranch
            .stereoType(UPDATED_STEREO_TYPE)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataTreeBranch.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataTreeBranch))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
        DataTreeBranch testDataTreeBranch = dataTreeBranchList.get(dataTreeBranchList.size() - 1);
        assertThat(testDataTreeBranch.getStereoType()).isEqualTo(UPDATED_STEREO_TYPE);
        assertThat(testDataTreeBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataTreeBranch.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataTreeBranch.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void patchNonExistingDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().collectList().block().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, dataTreeBranch.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().collectList().block().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDataTreeBranch() throws Exception {
        int databaseSizeBeforeUpdate = dataTreeBranchRepository.findAll().collectList().block().size();
        dataTreeBranch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataTreeBranch))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataTreeBranch in the database
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDataTreeBranch() {
        // Initialize the database
        dataTreeBranchRepository.save(dataTreeBranch).block();

        int databaseSizeBeforeDelete = dataTreeBranchRepository.findAll().collectList().block().size();

        // Delete the dataTreeBranch
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, dataTreeBranch.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<DataTreeBranch> dataTreeBranchList = dataTreeBranchRepository.findAll().collectList().block();
        assertThat(dataTreeBranchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
