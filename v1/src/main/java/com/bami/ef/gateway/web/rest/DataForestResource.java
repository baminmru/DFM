package com.bami.ef.gateway.web.rest;

import com.bami.ef.gateway.domain.DataForest;
import com.bami.ef.gateway.repository.DataForestRepository;
import com.bami.ef.gateway.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.bami.ef.gateway.domain.DataForest}.
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
    public Mono<ResponseEntity<DataForest>> createDataForest(@RequestBody DataForest dataForest) throws URISyntaxException {
        log.debug("REST request to save DataForest : {}", dataForest);
        if (dataForest.getId() != null) {
            throw new BadRequestAlertException("A new dataForest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return dataForestRepository
            .save(dataForest)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/data-forests/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
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
    public Mono<ResponseEntity<DataForest>> updateDataForest(
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

        return dataForestRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return dataForestRepository
                    .save(dataForest)
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
    public Mono<ResponseEntity<DataForest>> partialUpdateDataForest(
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

        return dataForestRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DataForest> result = dataForestRepository
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
                    .flatMap(dataForestRepository::save);

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
     * {@code GET  /data-forests} : get all the dataForests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataForests in body.
     */
    @GetMapping(value = "/data-forests", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<DataForest>> getAllDataForests() {
        log.debug("REST request to get all DataForests");
        return dataForestRepository.findAll().collectList();
    }

    /**
     * {@code GET  /data-forests} : get all the dataForests as a stream.
     * @return the {@link Flux} of dataForests.
     */
    @GetMapping(value = "/data-forests", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DataForest> getAllDataForestsAsStream() {
        log.debug("REST request to get all DataForests as a stream");
        return dataForestRepository.findAll();
    }

    /**
     * {@code GET  /data-forests/:id} : get the "id" dataForest.
     *
     * @param id the id of the dataForest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataForest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-forests/{id}")
    public Mono<ResponseEntity<DataForest>> getDataForest(@PathVariable Long id) {
        log.debug("REST request to get DataForest : {}", id);
        Mono<DataForest> dataForest = dataForestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dataForest);
    }

    /**
     * {@code DELETE  /data-forests/:id} : delete the "id" dataForest.
     *
     * @param id the id of the dataForest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-forests/{id}")
    public Mono<ResponseEntity<Void>> deleteDataForest(@PathVariable Long id) {
        log.debug("REST request to delete DataForest : {}", id);
        return dataForestRepository
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
