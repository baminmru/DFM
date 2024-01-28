package com.bami.dfm.v1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bami.dfm.v1.IntegrationTest;
import com.bami.dfm.v1.domain.DataField;
import com.bami.dfm.v1.domain.enumeration.FieldTypeEnum;
import com.bami.dfm.v1.domain.enumeration.InputTypeEnum;
import com.bami.dfm.v1.repository.DataFieldRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DataFieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restDataFieldMockMvc;

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

    @BeforeEach
    public void initTest() {
        dataField = createEntity(em);
    }

    @Test
    @Transactional
    void createDataField() throws Exception {
        int databaseSizeBeforeCreate = dataFieldRepository.findAll().size();
        // Create the DataField
        restDataFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataField)))
            .andExpect(status().isCreated());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
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
    @Transactional
    void createDataFieldWithExistingId() throws Exception {
        // Create the DataField with an existing ID
        dataField.setId(1L);

        int databaseSizeBeforeCreate = dataFieldRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataField)))
            .andExpect(status().isBadRequest());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDataFields() throws Exception {
        // Initialize the database
        dataFieldRepository.saveAndFlush(dataField);

        // Get all the dataFieldList
        restDataFieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataField.getId().intValue())))
            .andExpect(jsonPath("$.[*].inputType").value(hasItem(DEFAULT_INPUT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].referenceRoot").value(hasItem(DEFAULT_REFERENCE_ROOT)))
            .andExpect(jsonPath("$.[*].allowNull").value(hasItem(DEFAULT_ALLOW_NULL.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION)))
            .andExpect(jsonPath("$.[*].documentation").value(hasItem(DEFAULT_DOCUMENTATION)));
    }

    @Test
    @Transactional
    void getDataField() throws Exception {
        // Initialize the database
        dataFieldRepository.saveAndFlush(dataField);

        // Get the dataField
        restDataFieldMockMvc
            .perform(get(ENTITY_API_URL_ID, dataField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataField.getId().intValue()))
            .andExpect(jsonPath("$.inputType").value(DEFAULT_INPUT_TYPE.toString()))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE.toString()))
            .andExpect(jsonPath("$.referenceRoot").value(DEFAULT_REFERENCE_ROOT))
            .andExpect(jsonPath("$.allowNull").value(DEFAULT_ALLOW_NULL.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION))
            .andExpect(jsonPath("$.documentation").value(DEFAULT_DOCUMENTATION));
    }

    @Test
    @Transactional
    void getNonExistingDataField() throws Exception {
        // Get the dataField
        restDataFieldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDataField() throws Exception {
        // Initialize the database
        dataFieldRepository.saveAndFlush(dataField);

        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().size();

        // Update the dataField
        DataField updatedDataField = dataFieldRepository.findById(dataField.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDataField are not directly saved in db
        em.detach(updatedDataField);
        updatedDataField
            .inputType(UPDATED_INPUT_TYPE)
            .fieldType(UPDATED_FIELD_TYPE)
            .referenceRoot(UPDATED_REFERENCE_ROOT)
            .allowNull(UPDATED_ALLOW_NULL)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        restDataFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDataField.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDataField))
            )
            .andExpect(status().isOk());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
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
    @Transactional
    void putNonExistingDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().size();
        dataField.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataField.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataField))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().size();
        dataField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataField))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().size();
        dataField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataFieldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataField)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataFieldWithPatch() throws Exception {
        // Initialize the database
        dataFieldRepository.saveAndFlush(dataField);

        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().size();

        // Update the dataField using partial update
        DataField partialUpdatedDataField = new DataField();
        partialUpdatedDataField.setId(dataField.getId());

        partialUpdatedDataField
            .inputType(UPDATED_INPUT_TYPE)
            .referenceRoot(UPDATED_REFERENCE_ROOT)
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .documentation(UPDATED_DOCUMENTATION);

        restDataFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataField.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataField))
            )
            .andExpect(status().isOk());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
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
    @Transactional
    void fullUpdateDataFieldWithPatch() throws Exception {
        // Initialize the database
        dataFieldRepository.saveAndFlush(dataField);

        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().size();

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

        restDataFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataField.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataField))
            )
            .andExpect(status().isOk());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
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
    @Transactional
    void patchNonExistingDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().size();
        dataField.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataField.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataField))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().size();
        dataField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataField))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataField() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldRepository.findAll().size();
        dataField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataFieldMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataField))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataField in the database
        List<DataField> dataFieldList = dataFieldRepository.findAll();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataField() throws Exception {
        // Initialize the database
        dataFieldRepository.saveAndFlush(dataField);

        int databaseSizeBeforeDelete = dataFieldRepository.findAll().size();

        // Delete the dataField
        restDataFieldMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataField.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataField> dataFieldList = dataFieldRepository.findAll();
        assertThat(dataFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
