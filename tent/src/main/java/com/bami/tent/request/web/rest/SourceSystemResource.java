package com.bami.tent.request.web.rest;

import com.bami.tent.request.domain.SourceSystem;
import com.bami.tent.request.repository.SourceSystemRepository;
import com.bami.tent.request.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.bami.tent.request.domain.SourceSystem}.
 */
@RestController
@RequestMapping("/api/source-systems")
@Transactional
public class SourceSystemResource {

    private static final Logger log = LoggerFactory.getLogger(SourceSystemResource.class);

    private static final String ENTITY_NAME = "sourceSystem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SourceSystemRepository sourceSystemRepository;

    public SourceSystemResource(SourceSystemRepository sourceSystemRepository) {
        this.sourceSystemRepository = sourceSystemRepository;
    }

    /**
     * {@code POST  /source-systems} : Create a new sourceSystem.
     *
     * @param sourceSystem the sourceSystem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sourceSystem, or with status {@code 400 (Bad Request)} if the sourceSystem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SourceSystem> createSourceSystem(@Valid @RequestBody SourceSystem sourceSystem) throws URISyntaxException {
        log.debug("REST request to save SourceSystem : {}", sourceSystem);
        if (sourceSystem.getId() != null) {
            throw new BadRequestAlertException("A new sourceSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sourceSystem = sourceSystemRepository.save(sourceSystem);
        return ResponseEntity.created(new URI("/api/source-systems/" + sourceSystem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sourceSystem.getId().toString()))
            .body(sourceSystem);
    }

    /**
     * {@code PUT  /source-systems/:id} : Updates an existing sourceSystem.
     *
     * @param id the id of the sourceSystem to save.
     * @param sourceSystem the sourceSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sourceSystem,
     * or with status {@code 400 (Bad Request)} if the sourceSystem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sourceSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SourceSystem> updateSourceSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SourceSystem sourceSystem
    ) throws URISyntaxException {
        log.debug("REST request to update SourceSystem : {}, {}", id, sourceSystem);
        if (sourceSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sourceSystem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sourceSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sourceSystem = sourceSystemRepository.save(sourceSystem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sourceSystem.getId().toString()))
            .body(sourceSystem);
    }

    /**
     * {@code PATCH  /source-systems/:id} : Partial updates given fields of an existing sourceSystem, field will ignore if it is null
     *
     * @param id the id of the sourceSystem to save.
     * @param sourceSystem the sourceSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sourceSystem,
     * or with status {@code 400 (Bad Request)} if the sourceSystem is not valid,
     * or with status {@code 404 (Not Found)} if the sourceSystem is not found,
     * or with status {@code 500 (Internal Server Error)} if the sourceSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SourceSystem> partialUpdateSourceSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SourceSystem sourceSystem
    ) throws URISyntaxException {
        log.debug("REST request to partial update SourceSystem partially : {}, {}", id, sourceSystem);
        if (sourceSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sourceSystem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sourceSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SourceSystem> result = sourceSystemRepository
            .findById(sourceSystem.getId())
            .map(existingSourceSystem -> {
                if (sourceSystem.getCode() != null) {
                    existingSourceSystem.setCode(sourceSystem.getCode());
                }
                if (sourceSystem.getName() != null) {
                    existingSourceSystem.setName(sourceSystem.getName());
                }
                if (sourceSystem.getCreatedAt() != null) {
                    existingSourceSystem.setCreatedAt(sourceSystem.getCreatedAt());
                }
                if (sourceSystem.getCreatedBy() != null) {
                    existingSourceSystem.setCreatedBy(sourceSystem.getCreatedBy());
                }
                if (sourceSystem.getUpdatedAt() != null) {
                    existingSourceSystem.setUpdatedAt(sourceSystem.getUpdatedAt());
                }
                if (sourceSystem.getUpdatedBy() != null) {
                    existingSourceSystem.setUpdatedBy(sourceSystem.getUpdatedBy());
                }

                return existingSourceSystem;
            })
            .map(sourceSystemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sourceSystem.getId().toString())
        );
    }

    /**
     * {@code GET  /source-systems} : get all the sourceSystems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sourceSystems in body.
     */
    @GetMapping("")
    public List<SourceSystem> getAllSourceSystems() {
        log.debug("REST request to get all SourceSystems");
        return sourceSystemRepository.findAll();
    }

    /**
     * {@code GET  /source-systems/:id} : get the "id" sourceSystem.
     *
     * @param id the id of the sourceSystem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sourceSystem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SourceSystem> getSourceSystem(@PathVariable("id") Long id) {
        log.debug("REST request to get SourceSystem : {}", id);
        Optional<SourceSystem> sourceSystem = sourceSystemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sourceSystem);
    }

    /**
     * {@code DELETE  /source-systems/:id} : delete the "id" sourceSystem.
     *
     * @param id the id of the sourceSystem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSourceSystem(@PathVariable("id") Long id) {
        log.debug("REST request to delete SourceSystem : {}", id);
        sourceSystemRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
