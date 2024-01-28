package com.bami.dfm.v1.web.rest;

import com.bami.dfm.v1.domain.DataForest;
import com.bami.dfm.v1.repository.DataForestRepository;
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
 * REST controller for managing {@link com.bami.dfm.v1.domain.DataForest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DataForestResource {

    private final Logger log = LoggerFactory.getLogger(DataForestResource.class);

    private static final String ENTITY_NAME = "dataForest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataForestRepository dataForestRepository;

    public DataForestResource(DataForestRepository dataForestRepository) {
        this.dataForestRepository = dataForestRepository;
    }

    /**
     * {@code POST  /data-forests} : Create a new dataForest.
     *
     * @param dataForest the dataForest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataForest, or with status {@code 400 (Bad Request)} if the dataForest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-forests")
    public ResponseEntity<DataForest> createDataForest(@RequestBody DataForest dataForest) throws URISyntaxException {
        log.debug("REST request to save DataForest : {}", dataForest);
        if (dataForest.getId() != null) {
            throw new BadRequestAlertException("A new dataForest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataForest result = dataForestRepository.save(dataForest);
        return ResponseEntity
            .created(new URI("/api/data-forests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-forests/:id} : Updates an existing dataForest.
     *
     * @param id the id of the dataForest to save.
     * @param dataForest the dataForest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataForest,
     * or with status {@code 400 (Bad Request)} if the dataForest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataForest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-forests/{id}")
    public ResponseEntity<DataForest> updateDataForest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataForest dataForest
    ) throws URISyntaxException {
        log.debug("REST request to update DataForest : {}, {}", id, dataForest);
        if (dataForest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataForest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataForestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DataForest result = dataForestRepository.save(dataForest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataForest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /data-forests/:id} : Partial updates given fields of an existing dataForest, field will ignore if it is null
     *
     * @param id the id of the dataForest to save.
     * @param dataForest the dataForest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataForest,
     * or with status {@code 400 (Bad Request)} if the dataForest is not valid,
     * or with status {@code 404 (Not Found)} if the dataForest is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataForest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/data-forests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DataForest> partialUpdateDataForest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataForest dataForest
    ) throws URISyntaxException {
        log.debug("REST request to partial update DataForest partially : {}, {}", id, dataForest);
        if (dataForest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataForest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataForestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DataForest> result = dataForestRepository
            .findById(dataForest.getId())
            .map(existingDataForest -> {
                if (dataForest.getName() != null) {
                    existingDataForest.setName(dataForest.getName());
                }
                if (dataForest.getCaption() != null) {
                    existingDataForest.setCaption(dataForest.getCaption());
                }
                if (dataForest.getDocumentation() != null) {
                    existingDataForest.setDocumentation(dataForest.getDocumentation());
                }

                return existingDataForest;
            })
            .map(dataForestRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataForest.getId().toString())
        );
    }

    /**
     * {@code GET  /data-forests} : get all the dataForests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataForests in body.
     */
    @GetMapping("/data-forests")
    public List<DataForest> getAllDataForests() {
        log.debug("REST request to get all DataForests");
        return dataForestRepository.findAll();
    }

    /**
     * {@code GET  /data-forests/:id} : get the "id" dataForest.
     *
     * @param id the id of the dataForest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataForest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-forests/{id}")
    public ResponseEntity<DataForest> getDataForest(@PathVariable Long id) {
        log.debug("REST request to get DataForest : {}", id);
        Optional<DataForest> dataForest = dataForestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dataForest);
    }

    /**
     * {@code DELETE  /data-forests/:id} : delete the "id" dataForest.
     *
     * @param id the id of the dataForest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-forests/{id}")
    public ResponseEntity<Void> deleteDataForest(@PathVariable Long id) {
        log.debug("REST request to delete DataForest : {}", id);
        dataForestRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
