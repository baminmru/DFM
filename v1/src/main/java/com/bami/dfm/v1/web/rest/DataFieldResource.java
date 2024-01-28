package com.bami.dfm.v1.web.rest;

import com.bami.dfm.v1.domain.DataField;
import com.bami.dfm.v1.repository.DataFieldRepository;
import com.bami.dfm.v1.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bami.dfm.v1.domain.DataField}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DataFieldResource {

    private final Logger log = LoggerFactory.getLogger(DataFieldResource.class);

    private static final String ENTITY_NAME = "dataField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataFieldRepository dataFieldRepository;

    public DataFieldResource(DataFieldRepository dataFieldRepository) {
        this.dataFieldRepository = dataFieldRepository;
    }

    /**
     * {@code POST  /data-fields} : Create a new dataField.
     *
     * @param dataField the dataField to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataField, or with status {@code 400 (Bad Request)} if the dataField has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-fields")
    public ResponseEntity<DataField> createDataField(@RequestBody DataField dataField) throws URISyntaxException {
        log.debug("REST request to save DataField : {}", dataField);
        if (dataField.getId() != null) {
            throw new BadRequestAlertException("A new dataField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataField result = dataFieldRepository.save(dataField);
        return ResponseEntity
            .created(new URI("/api/data-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-fields/:id} : Updates an existing dataField.
     *
     * @param id the id of the dataField to save.
     * @param dataField the dataField to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataField,
     * or with status {@code 400 (Bad Request)} if the dataField is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataField couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-fields/{id}")
    public ResponseEntity<DataField> updateDataField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataField dataField
    ) throws URISyntaxException {
        log.debug("REST request to update DataField : {}, {}", id, dataField);
        if (dataField.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataField.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataFieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DataField result = dataFieldRepository.save(dataField);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataField.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /data-fields/:id} : Partial updates given fields of an existing dataField, field will ignore if it is null
     *
     * @param id the id of the dataField to save.
     * @param dataField the dataField to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataField,
     * or with status {@code 400 (Bad Request)} if the dataField is not valid,
     * or with status {@code 404 (Not Found)} if the dataField is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataField couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/data-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DataField> partialUpdateDataField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataField dataField
    ) throws URISyntaxException {
        log.debug("REST request to partial update DataField partially : {}, {}", id, dataField);
        if (dataField.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataField.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataFieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DataField> result = dataFieldRepository
            .findById(dataField.getId())
            .map(existingDataField -> {
                if (dataField.getInputType() != null) {
                    existingDataField.setInputType(dataField.getInputType());
                }
                if (dataField.getFieldType() != null) {
                    existingDataField.setFieldType(dataField.getFieldType());
                }
                if (dataField.getReferenceRoot() != null) {
                    existingDataField.setReferenceRoot(dataField.getReferenceRoot());
                }
                if (dataField.getAllowNull() != null) {
                    existingDataField.setAllowNull(dataField.getAllowNull());
                }
                if (dataField.getName() != null) {
                    existingDataField.setName(dataField.getName());
                }
                if (dataField.getCaption() != null) {
                    existingDataField.setCaption(dataField.getCaption());
                }
                if (dataField.getDocumentation() != null) {
                    existingDataField.setDocumentation(dataField.getDocumentation());
                }

                return existingDataField;
            })
            .map(dataFieldRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataField.getId().toString())
        );
    }

    /**
     * {@code GET  /data-fields} : get all the dataFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataFields in body.
     */
    @GetMapping("/data-fields")
    public List<DataField> getAllDataFields() {
        log.debug("REST request to get all DataFields");
        return dataFieldRepository.findAll();
    }

    /**
     * {@code GET  /data-fields/:id} : get the "id" dataField.
     *
     * @param id the id of the dataField to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataField, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-fields/{id}")
    public ResponseEntity<DataField> getDataField(@PathVariable Long id) {
        log.debug("REST request to get DataField : {}", id);
        Optional<DataField> dataField = dataFieldRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dataField);
    }

    /**
     * {@code DELETE  /data-fields/:id} : delete the "id" dataField.
     *
     * @param id the id of the dataField to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-fields/{id}")
    public ResponseEntity<Void> deleteDataField(@PathVariable Long id) {
        log.debug("REST request to delete DataField : {}", id);
        dataFieldRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
