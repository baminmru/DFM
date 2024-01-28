package com.bami.dfm.web.rest;

import com.bami.dfm.domain.DataTreeBranch;
import com.bami.dfm.repository.DataTreeBranchRepository;
import com.bami.dfm.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.bami.dfm.domain.DataTreeBranch}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DataTreeBranchResource {

    private final Logger log = LoggerFactory.getLogger(DataTreeBranchResource.class);

    private static final String ENTITY_NAME = "dataTreeBranch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataTreeBranchRepository dataTreeBranchRepository;

    public DataTreeBranchResource(DataTreeBranchRepository dataTreeBranchRepository) {
        this.dataTreeBranchRepository = dataTreeBranchRepository;
    }

    /**
     * {@code POST  /data-tree-branches} : Create a new dataTreeBranch.
     *
     * @param dataTreeBranch the dataTreeBranch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataTreeBranch, or with status {@code 400 (Bad Request)} if the dataTreeBranch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-tree-branches")
    public Mono<ResponseEntity<DataTreeBranch>> createDataTreeBranch(@Valid @RequestBody DataTreeBranch dataTreeBranch)
        throws URISyntaxException {
        log.debug("REST request to save DataTreeBranch : {}", dataTreeBranch);
        if (dataTreeBranch.getId() != null) {
            throw new BadRequestAlertException("A new dataTreeBranch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return dataTreeBranchRepository
            .save(dataTreeBranch)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/data-tree-branches/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /data-tree-branches/:id} : Updates an existing dataTreeBranch.
     *
     * @param id the id of the dataTreeBranch to save.
     * @param dataTreeBranch the dataTreeBranch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataTreeBranch,
     * or with status {@code 400 (Bad Request)} if the dataTreeBranch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataTreeBranch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-tree-branches/{id}")
    public Mono<ResponseEntity<DataTreeBranch>> updateDataTreeBranch(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DataTreeBranch dataTreeBranch
    ) throws URISyntaxException {
        log.debug("REST request to update DataTreeBranch : {}, {}", id, dataTreeBranch);
        if (dataTreeBranch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataTreeBranch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dataTreeBranchRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return dataTreeBranchRepository
                    .save(dataTreeBranch)
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
     * {@code PATCH  /data-tree-branches/:id} : Partial updates given fields of an existing dataTreeBranch, field will ignore if it is null
     *
     * @param id the id of the dataTreeBranch to save.
     * @param dataTreeBranch the dataTreeBranch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataTreeBranch,
     * or with status {@code 400 (Bad Request)} if the dataTreeBranch is not valid,
     * or with status {@code 404 (Not Found)} if the dataTreeBranch is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataTreeBranch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/data-tree-branches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<DataTreeBranch>> partialUpdateDataTreeBranch(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DataTreeBranch dataTreeBranch
    ) throws URISyntaxException {
        log.debug("REST request to partial update DataTreeBranch partially : {}, {}", id, dataTreeBranch);
        if (dataTreeBranch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataTreeBranch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dataTreeBranchRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DataTreeBranch> result = dataTreeBranchRepository
                    .findById(dataTreeBranch.getId())
                    .map(existingDataTreeBranch -> {
                        if (dataTreeBranch.getStereoType() != null) {
                            existingDataTreeBranch.setStereoType(dataTreeBranch.getStereoType());
                        }
                        if (dataTreeBranch.getName() != null) {
                            existingDataTreeBranch.setName(dataTreeBranch.getName());
                        }
                        if (dataTreeBranch.getCaption() != null) {
                            existingDataTreeBranch.setCaption(dataTreeBranch.getCaption());
                        }
                        if (dataTreeBranch.getDocumentation() != null) {
                            existingDataTreeBranch.setDocumentation(dataTreeBranch.getDocumentation());
                        }

                        return existingDataTreeBranch;
                    })
                    .flatMap(dataTreeBranchRepository::save);

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
     * {@code GET  /data-tree-branches} : get all the dataTreeBranches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataTreeBranches in body.
     */
    @GetMapping(value = "/data-tree-branches", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<DataTreeBranch>> getAllDataTreeBranches() {
        log.debug("REST request to get all DataTreeBranches");
        return dataTreeBranchRepository.findAll().collectList();
    }

    /**
     * {@code GET  /data-tree-branches} : get all the dataTreeBranches as a stream.
     * @return the {@link Flux} of dataTreeBranches.
     */
    @GetMapping(value = "/data-tree-branches", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DataTreeBranch> getAllDataTreeBranchesAsStream() {
        log.debug("REST request to get all DataTreeBranches as a stream");
        return dataTreeBranchRepository.findAll();
    }

    /**
     * {@code GET  /data-tree-branches/:id} : get the "id" dataTreeBranch.
     *
     * @param id the id of the dataTreeBranch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataTreeBranch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-tree-branches/{id}")
    public Mono<ResponseEntity<DataTreeBranch>> getDataTreeBranch(@PathVariable Long id) {
        log.debug("REST request to get DataTreeBranch : {}", id);
        Mono<DataTreeBranch> dataTreeBranch = dataTreeBranchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dataTreeBranch);
    }

    /**
     * {@code DELETE  /data-tree-branches/:id} : delete the "id" dataTreeBranch.
     *
     * @param id the id of the dataTreeBranch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-tree-branches/{id}")
    public Mono<ResponseEntity<Void>> deleteDataTreeBranch(@PathVariable Long id) {
        log.debug("REST request to delete DataTreeBranch : {}", id);
        return dataTreeBranchRepository
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
