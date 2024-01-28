package com.bami.ef.gateway.web.rest;

import com.bami.ef.gateway.domain.DataTreeRoot;
import com.bami.ef.gateway.repository.DataTreeRootRepository;
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
 * REST controller for managing {@link com.bami.ef.gateway.domain.DataTreeRoot}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DataTreeRootResource {

    private final Logger log = LoggerFactory.getLogger(DataTreeRootResource.class);

    private static final String ENTITY_NAME = "dataTreeRoot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataTreeRootRepository dataTreeRootRepository;

    public DataTreeRootResource(DataTreeRootRepository dataTreeRootRepository) {
        this.dataTreeRootRepository = dataTreeRootRepository;
    }

    /**
     * {@code POST  /data-tree-roots} : Create a new dataTreeRoot.
     *
     * @param dataTreeRoot the dataTreeRoot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataTreeRoot, or with status {@code 400 (Bad Request)} if the dataTreeRoot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-tree-roots")
    public Mono<ResponseEntity<DataTreeRoot>> createDataTreeRoot(@RequestBody DataTreeRoot dataTreeRoot) throws URISyntaxException {
        log.debug("REST request to save DataTreeRoot : {}", dataTreeRoot);
        if (dataTreeRoot.getId() != null) {
            throw new BadRequestAlertException("A new dataTreeRoot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return dataTreeRootRepository
            .save(dataTreeRoot)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/data-tree-roots/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /data-tree-roots/:id} : Updates an existing dataTreeRoot.
     *
     * @param id the id of the dataTreeRoot to save.
     * @param dataTreeRoot the dataTreeRoot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataTreeRoot,
     * or with status {@code 400 (Bad Request)} if the dataTreeRoot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataTreeRoot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-tree-roots/{id}")
    public Mono<ResponseEntity<DataTreeRoot>> updateDataTreeRoot(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataTreeRoot dataTreeRoot
    ) throws URISyntaxException {
        log.debug("REST request to update DataTreeRoot : {}, {}", id, dataTreeRoot);
        if (dataTreeRoot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataTreeRoot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dataTreeRootRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return dataTreeRootRepository
                    .save(dataTreeRoot)
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
     * {@code PATCH  /data-tree-roots/:id} : Partial updates given fields of an existing dataTreeRoot, field will ignore if it is null
     *
     * @param id the id of the dataTreeRoot to save.
     * @param dataTreeRoot the dataTreeRoot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataTreeRoot,
     * or with status {@code 400 (Bad Request)} if the dataTreeRoot is not valid,
     * or with status {@code 404 (Not Found)} if the dataTreeRoot is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataTreeRoot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/data-tree-roots/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<DataTreeRoot>> partialUpdateDataTreeRoot(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataTreeRoot dataTreeRoot
    ) throws URISyntaxException {
        log.debug("REST request to partial update DataTreeRoot partially : {}, {}", id, dataTreeRoot);
        if (dataTreeRoot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataTreeRoot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dataTreeRootRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DataTreeRoot> result = dataTreeRootRepository
                    .findById(dataTreeRoot.getId())
                    .map(existingDataTreeRoot -> {
                        if (dataTreeRoot.getStereoType() != null) {
                            existingDataTreeRoot.setStereoType(dataTreeRoot.getStereoType());
                        }
                        if (dataTreeRoot.getName() != null) {
                            existingDataTreeRoot.setName(dataTreeRoot.getName());
                        }
                        if (dataTreeRoot.getCaption() != null) {
                            existingDataTreeRoot.setCaption(dataTreeRoot.getCaption());
                        }
                        if (dataTreeRoot.getDocumentation() != null) {
                            existingDataTreeRoot.setDocumentation(dataTreeRoot.getDocumentation());
                        }

                        return existingDataTreeRoot;
                    })
                    .flatMap(dataTreeRootRepository::save);

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
     * {@code GET  /data-tree-roots} : get all the dataTreeRoots.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataTreeRoots in body.
     */
    @GetMapping(value = "/data-tree-roots", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<DataTreeRoot>> getAllDataTreeRoots(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DataTreeRoots");
        if (eagerload) {
            return dataTreeRootRepository.findAllWithEagerRelationships().collectList();
        } else {
            return dataTreeRootRepository.findAll().collectList();
        }
    }

    /**
     * {@code GET  /data-tree-roots} : get all the dataTreeRoots as a stream.
     * @return the {@link Flux} of dataTreeRoots.
     */
    @GetMapping(value = "/data-tree-roots", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DataTreeRoot> getAllDataTreeRootsAsStream() {
        log.debug("REST request to get all DataTreeRoots as a stream");
        return dataTreeRootRepository.findAll();
    }

    /**
     * {@code GET  /data-tree-roots/:id} : get the "id" dataTreeRoot.
     *
     * @param id the id of the dataTreeRoot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataTreeRoot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-tree-roots/{id}")
    public Mono<ResponseEntity<DataTreeRoot>> getDataTreeRoot(@PathVariable Long id) {
        log.debug("REST request to get DataTreeRoot : {}", id);
        Mono<DataTreeRoot> dataTreeRoot = dataTreeRootRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(dataTreeRoot);
    }

    /**
     * {@code DELETE  /data-tree-roots/:id} : delete the "id" dataTreeRoot.
     *
     * @param id the id of the dataTreeRoot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-tree-roots/{id}")
    public Mono<ResponseEntity<Void>> deleteDataTreeRoot(@PathVariable Long id) {
        log.debug("REST request to delete DataTreeRoot : {}", id);
        return dataTreeRootRepository
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
