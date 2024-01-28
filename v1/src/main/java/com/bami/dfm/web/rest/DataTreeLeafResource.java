package com.bami.dfm.web.rest;

import com.bami.dfm.domain.DataTreeLeaf;
import com.bami.dfm.repository.DataTreeLeafRepository;
import com.bami.dfm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.bami.dfm.domain.DataTreeLeaf}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DataTreeLeafResource {

    private final Logger log = LoggerFactory.getLogger(DataTreeLeafResource.class);

    private static final String ENTITY_NAME = "dataTreeLeaf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataTreeLeafRepository dataTreeLeafRepository;

    public DataTreeLeafResource(DataTreeLeafRepository dataTreeLeafRepository) {
        this.dataTreeLeafRepository = dataTreeLeafRepository;
    }

    /**
     * {@code POST  /data-tree-leaves} : Create a new dataTreeLeaf.
     *
     * @param dataTreeLeaf the dataTreeLeaf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataTreeLeaf, or with status {@code 400 (Bad Request)} if the dataTreeLeaf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-tree-leaves")
    public Mono<ResponseEntity<DataTreeLeaf>> createDataTreeLeaf(@RequestBody DataTreeLeaf dataTreeLeaf) throws URISyntaxException {
        log.debug("REST request to save DataTreeLeaf : {}", dataTreeLeaf);
        if (dataTreeLeaf.getId() != null) {
            throw new BadRequestAlertException("A new dataTreeLeaf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return dataTreeLeafRepository
            .save(dataTreeLeaf)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/data-tree-leaves/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /data-tree-leaves/:id} : Updates an existing dataTreeLeaf.
     *
     * @param id the id of the dataTreeLeaf to save.
     * @param dataTreeLeaf the dataTreeLeaf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataTreeLeaf,
     * or with status {@code 400 (Bad Request)} if the dataTreeLeaf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataTreeLeaf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-tree-leaves/{id}")
    public Mono<ResponseEntity<DataTreeLeaf>> updateDataTreeLeaf(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataTreeLeaf dataTreeLeaf
    ) throws URISyntaxException {
        log.debug("REST request to update DataTreeLeaf : {}, {}", id, dataTreeLeaf);
        if (dataTreeLeaf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataTreeLeaf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dataTreeLeafRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return dataTreeLeafRepository
                    .save(dataTreeLeaf)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /data-tree-leaves/:id} : Partial updates given fields of an existing dataTreeLeaf, field will ignore if it is null
     *
     * @param id the id of the dataTreeLeaf to save.
     * @param dataTreeLeaf the dataTreeLeaf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataTreeLeaf,
     * or with status {@code 400 (Bad Request)} if the dataTreeLeaf is not valid,
     * or with status {@code 404 (Not Found)} if the dataTreeLeaf is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataTreeLeaf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/data-tree-leaves/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<DataTreeLeaf>> partialUpdateDataTreeLeaf(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataTreeLeaf dataTreeLeaf
    ) throws URISyntaxException {
        log.debug("REST request to partial update DataTreeLeaf partially : {}, {}", id, dataTreeLeaf);
        if (dataTreeLeaf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataTreeLeaf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dataTreeLeafRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DataTreeLeaf> result = dataTreeLeafRepository
                    .findById(dataTreeLeaf.getId())
                    .map(existingDataTreeLeaf -> {
                        if (dataTreeLeaf.getStereoType() != null) {
                            existingDataTreeLeaf.setStereoType(dataTreeLeaf.getStereoType());
                        }
                        if (dataTreeLeaf.getName() != null) {
                            existingDataTreeLeaf.setName(dataTreeLeaf.getName());
                        }
                        if (dataTreeLeaf.getCaption() != null) {
                            existingDataTreeLeaf.setCaption(dataTreeLeaf.getCaption());
                        }
                        if (dataTreeLeaf.getDocumentation() != null) {
                            existingDataTreeLeaf.setDocumentation(dataTreeLeaf.getDocumentation());
                        }

                        return existingDataTreeLeaf;
                    })
                    .flatMap(dataTreeLeafRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /data-tree-leaves} : get all the dataTreeLeaves.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataTreeLeaves in body.
     */
    @GetMapping(value = "/data-tree-leaves", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<DataTreeLeaf>> getAllDataTreeLeaves(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DataTreeLeaves");
        if (eagerload) {
            return dataTreeLeafRepository.findAllWithEagerRelationships().collectList();
        } else {
            return dataTreeLeafRepository.findAll().collectList();
        }
    }

    /**
     * {@code GET  /data-tree-leaves} : get all the dataTreeLeaves as a stream.
     * @return the {@link Flux} of dataTreeLeaves.
     */
    @GetMapping(value = "/data-tree-leaves", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DataTreeLeaf> getAllDataTreeLeavesAsStream() {
        log.debug("REST request to get all DataTreeLeaves as a stream");
        return dataTreeLeafRepository.findAll();
    }

    /**
     * {@code GET  /data-tree-leaves/:id} : get the "id" dataTreeLeaf.
     *
     * @param id the id of the dataTreeLeaf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataTreeLeaf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-tree-leaves/{id}")
    public Mono<ResponseEntity<DataTreeLeaf>> getDataTreeLeaf(@PathVariable Long id) {
        log.debug("REST request to get DataTreeLeaf : {}", id);
        Mono<DataTreeLeaf> dataTreeLeaf = dataTreeLeafRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(dataTreeLeaf);
    }

    /**
     * {@code DELETE  /data-tree-leaves/:id} : delete the "id" dataTreeLeaf.
     *
     * @param id the id of the dataTreeLeaf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-tree-leaves/{id}")
    public Mono<ResponseEntity<Void>> deleteDataTreeLeaf(@PathVariable Long id) {
        log.debug("REST request to delete DataTreeLeaf : {}", id);
        return dataTreeLeafRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
