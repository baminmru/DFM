package com.bami.dfm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.bami.dfm.IntegrationTest;
import com.bami.dfm.domain.DataField;
import com.bami.dfm.domain.enumeration.FieldTypeEnum;
import com.bami.dfm.domain.enumeration.InputTypeEnum;
import com.bami.dfm.repository.DataFieldRepository;
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
 * Integration tests for the {@link DataFieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DataFieldResourceIT {

    private static final InputTypeEnum DEFAULT_INPUT_TYPE = InputTypeEnum.PLAINTEX_INPUT;
    private static final InputTypeEnum UPDATED_INPUT_TYPE = InputTypeEnum.MULTILINE_INPUT;

    private static final FieldTypeEnum DEFAULT_FIELD_TYPE = FieldTypeEnum.STRING_TYPE;
    private static final FieldTypeEnum UPDATED_FIELD_TYPE = FieldTypeEnum.BLOB_TYPE;

    private static final String DEFAULT_REFERENCE_ROOT = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_ROOT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALLOW_NULL = false;
    private static final Boolean UPDATED_ALLOW_NULL = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataFieldRepository dataFieldRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private DataField dataField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataField createEntity(EntityManager em) {
        DataField dataField = new DataField()
            .inputType(DEFAULT_INPUT_TYPE)
            .fieldType(DEFAULT_FIELD_TYPE)
            .referenceRoot(DEFAULT_REFERENCE_ROOT)
            .allowNull(DEFAULT_ALLOW_NULL)
            .name(DEFAULT_NAME)
            .caption(DEFAULT_CAPTION)
            .documentation(DEFAULT_DOCUMENTATION);
        return dataField;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataField createUpdatedEntity(EntityManager em) {
        DataField dataField = new DataField()
            .inputType(UPDATED_INPUT_TYPE)
            .fieldType(UPDATED_FIELD_TYPE)
            .referenceRoot(UPDATED_REFERENCE_ROOT)
            .allowNull(UPDATED_ALLOW_NULL)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);
        return dataField;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(DataField.class).block();
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
        dataField = createEntity(em);
    }

    @Test
    void createDataField() throws Exception {
        int databaseSizeBeforeCreate = dataFieldRepository.findAll().collectList().block().size();
        // Create the DataField
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataField))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeCreate + 1);
        DataField testDataField = dataFieldList.get(dataFieldList.size() - 1);
        assertThat(testDataField.getInputType()).isEqualTo(DEFAULT_INPUT_TYPE);
        assertThat(testDataField.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testDataField.getReferenceRoot()).isEqualTo(DEFAULT_REFERENCE_ROOT);
        assertThat(testDataField.getAllowNull()).isEqualTo(DEFAULT_ALLOW_NULL);
        assertThat(testDataField.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataField.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataField.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void createDataFieldWithExistingId() throws Exception {
        // Create the DataField with an existing ID
        dataField.setId(1L);

        int databaseSizeBeforeCreate = dataFieldRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataField))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDataFieldsAsStream() {
        // Initialize the database
        dataFieldRepository.save(dataField).block();

        List<DataField> dataFieldList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DataField.class)
            .getResponseBody()
            .filter(dataField::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(dataFieldList).isNotNull();
        assertThat(dataFieldList).hasSize(1);
        DataField testDataField = dataFieldList.get(0);
        assertThat(testDataField.getInputType()).isEqualTo(DEFAULT_INPUT_TYPE);
        assertThat(testDataField.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testDataField.getReferenceRoot()).isEqualTo(DEFAULT_REFERENCE_ROOT);
        assertThat(testDataField.getAllowNull()).isEqualTo(DEFAULT_ALLOW_NULL);
        assertThat(testDataField.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataField.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testDataField.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
    }

    @Test
    void getAllDataFields() {
        // Initialize the database
        dataFieldRepository.save(dataField).block();

        // Get all the dataFieldList
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
            .value(hasItem(dataField.getId().intValue()))
            .jsonPath("$.[*].inputType")
            .value(hasItem(DEFAULT_INPUT_TYPE.toString()))
            .jsonPath("$.[*].fieldType")
            .value(hasItem(DEFAULT_FIELD_TYPE.toString()))
            .jsonPath("$.[*].referenceRoot")
            .value(hasItem(DEFAULT_REFERENCE_ROOT))
            .jsonPath("$.[*].allowNull")
            .value(hasItem(DEFAULT_ALLOW_NULL.booleanValue()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].caption")
            .value(hasItem(DEFAULT_CAPTION))
            .jsonPath("$.[*].documentation")
            .value(hasItem(DEFAULT_DOCUMENTATION));
    }

    @Test
    void getDataField() {
        // Initialize the database
        dataFieldRepository.save(dataField).block();

        // Get the dataField
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, dataField.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(dataField.getId().intValue()))
            .jsonPath("$.inputType")
            .value(is(DEFAULT_INPUT_TYPE.toString()))
            .jsonPath("$.fieldType")
            .value(is(DEFAULT_FIELD_TYPE.toString()))
            .jsonPath("$.referenceRoot")
            .value(is(DEFAULT_REFERENCE_ROOT))
            .jsonPath("$.allowNull")
            .value(is(DEFAULT_ALLOW_NULL.booleanValue()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.caption")
            .value(is(DEFAULT_CAPTION))
            .jsonPath("$.documentation")
            .value(is(DEFAULT_DOCUMENTATION));
    }

    @Test
    void getNonExistingDataField() {
        // Get the dataField
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDataField() throws Exception {
        // Initialize the database
        dataFieldRepository.save(dataField).block();

        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().collectList().block().size();

        // Update the dataField
        DataField updatedDataField = dataFieldRepository.findById(dataField.getId()).block();
        updatedDataField
            .inputType(UPDATED_INPUT_TYPE)
            .fieldType(UPDATED_FIELD_TYPE)
            .referenceRoot(UPDATED_REFERENCE_ROOT)
            .allowNull(UPDATED_ALLOW_NULL)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedDataField.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedDataField))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
        DataField testDataField = dataFieldList.get(dataFieldList.size() - 1);
        assertThat(testDataField.getInputType()).isEqualTo(UPDATED_INPUT_TYPE);
        assertThat(testDataField.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testDataField.getReferenceRoot()).isEqualTo(UPDATED_REFERENCE_ROOT);
        assertThat(testDataField.getAllowNull()).isEqualTo(UPDATED_ALLOW_NULL);
        assertThat(testDataField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataField.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataField.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void putNonExistingDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().collectList().block().size();
        dataField.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, dataField.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataField))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().collectList().block().size();
        dataField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataField))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().collectList().block().size();
        dataField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataField))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDataFieldWithPatch() throws Exception {
        // Initialize the database
        dataFieldRepository.save(dataField).block();

        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().collectList().block().size();

        // Update the dataField using partial update
        DataField partialUpdatedDataField = new DataField();
        partialUpdatedDataField.setId(dataField.getId());

        partialUpdatedDataField
            .inputType(UPDATED_INPUT_TYPE)
            .referenceRoot(UPDATED_REFERENCE_ROOT)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataField.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataField))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
        DataField testDataField = dataFieldList.get(dataFieldList.size() - 1);
        assertThat(testDataField.getInputType()).isEqualTo(UPDATED_INPUT_TYPE);
        assertThat(testDataField.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testDataField.getReferenceRoot()).isEqualTo(UPDATED_REFERENCE_ROOT);
        assertThat(testDataField.getAllowNull()).isEqualTo(DEFAULT_ALLOW_NULL);
        assertThat(testDataField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataField.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataField.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void fullUpdateDataFieldWithPatch() throws Exception {
        // Initialize the database
        dataFieldRepository.save(dataField).block();

        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().collectList().block().size();

        // Update the dataField using partial update
        DataField partialUpdatedDataField = new DataField();
        partialUpdatedDataField.setId(dataField.getId());

        partialUpdatedDataField
            .inputType(UPDATED_INPUT_TYPE)
            .fieldType(UPDATED_FIELD_TYPE)
            .referenceRoot(UPDATED_REFERENCE_ROOT)
            .allowNull(UPDATED_ALLOW_NULL)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDataField.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDataField))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
        DataField testDataField = dataFieldList.get(dataFieldList.size() - 1);
        assertThat(testDataField.getInputType()).isEqualTo(UPDATED_INPUT_TYPE);
        assertThat(testDataField.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testDataField.getReferenceRoot()).isEqualTo(UPDATED_REFERENCE_ROOT);
        assertThat(testDataField.getAllowNull()).isEqualTo(UPDATED_ALLOW_NULL);
        assertThat(testDataField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataField.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testDataField.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
    }

    @Test
    void patchNonExistingDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().collectList().block().size();
        dataField.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, dataField.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataField))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().collectList().block().size();
        dataField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataField))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().collectList().block().size();
        dataField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(dataField))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDataField() {
        // Initialize the database
        dataFieldRepository.save(dataField).block();

        int databaseSizeBeforeDelete = dataFieldRepository.findAll().collectList().block().size();

        // Delete the dataField
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, dataField.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<DataField> dataFieldList = dataFieldRepository.findAll().collectList().block();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
