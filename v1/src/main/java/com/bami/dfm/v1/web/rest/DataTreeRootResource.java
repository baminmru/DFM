package com.bami.dfm.v1.web.rest;

import com.bami.dfm.v1.domain.DataTreeRoot;
import com.bami.dfm.v1.repository.DataTreeRootRepository;
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
 * REST controller for managing {@link com.bami.dfm.v1.domain.DataTreeRoot}.
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
    public ResponseEntity<DataTreeRoot> createDataTreeRoot(@RequestBody DataTreeRoot dataTreeRoot) throws URISyntaxException {
        log.debug("REST request to save DataTreeRoot : {}", dataTreeRoot);
        if (dataTreeRoot.getId() != null) {
            throw new BadRequestAlertException("A new dataTreeRoot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataTreeRoot result = dataTreeRootRepository.save(dataTreeRoot);
        return ResponseEntity
            .created(new URI("/api/data-tree-roots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<DataTreeRoot> updateDataTreeRoot(
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

        if (!dataTreeRootRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DataTreeRoot result = dataTreeRootRepository.save(dataTreeRoot);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataTreeRoot.getId().toString()))
            .body(result);
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
    public ResponseEntity<DataTreeRoot> partialUpdateDataTreeRoot(
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

        if (!dataTreeRootRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DataTreeRoot> result = dataTreeRootRepository
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
            .map(dataTreeRootRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataTreeRoot.getId().toString())
        );
    }

    /**
     * {@code GET  /data-tree-roots} : get all the dataTreeRoots.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataTreeRoots in body.
     */
    @GetMapping("/data-tree-roots")
    public List<DataTreeRoot> getAllDataTreeRoots(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DataTreeRoots");
        if (eagerload) {
            return dataTreeRootRepository.findAllWithEagerRelationships();
        } else {
            return dataTreeRootRepository.findAll();
        }
    }

    /**
     * {@code GET  /data-tree-roots/:id} : get the "id" dataTreeRoot.
     *
     * @param id the id of the dataTreeRoot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataTreeRoot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-tree-roots/{id}")
    public ResponseEntity<DataTreeRoot> getDataTreeRoot(@PathVariable Long id) {
        log.debug("REST request to get DataTreeRoot : {}", id);
        Optional<DataTreeRoot> dataTreeRoot = dataTreeRootRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(dataTreeRoot);
    }

    /**
     * {@code DELETE  /data-tree-roots/:id} : delete the "id" dataTreeRoot.
     *
     * @param id the id of the dataTreeRoot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-tree-roots/{id}")
    public ResponseEntity<Void> deleteDataTreeRoot(@PathVariable Long id) {
        log.debug("REST request to delete DataTreeRoot : {}", id);
        dataTreeRootRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
