package com.bami.ef.tent.web.rest;

import com.bami.ef.tent.domain.RequestContentConfig;
import com.bami.ef.tent.repository.RequestContentConfigRepository;
import com.bami.ef.tent.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.bami.ef.tent.domain.RequestContentConfig}.
 */
@RestController
@RequestMapping("/api/request-content-configs")
@Transactional
public class RequestContentConfigResource {

    private static final Logger log = LoggerFactory.getLogger(RequestContentConfigResource.class);

    private static final String ENTITY_NAME = "requestContentConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestContentConfigRepository requestContentConfigRepository;

    public RequestContentConfigResource(RequestContentConfigRepository requestContentConfigRepository) {
        this.requestContentConfigRepository = requestContentConfigRepository;
    }

    /**
     * {@code POST  /request-content-configs} : Create a new requestContentConfig.
     *
     * @param requestContentConfig the requestContentConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestContentConfig, or with status {@code 400 (Bad Request)} if the requestContentConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestContentConfig> createRequestContentConfig(@Valid @RequestBody RequestContentConfig requestContentConfig)
        throws URISyntaxException {
        log.debug("REST request to save RequestContentConfig : {}", requestContentConfig);
        if (requestContentConfig.getId() != null) {
            throw new BadRequestAlertException("A new requestContentConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        requestContentConfig = requestContentConfigRepository.save(requestContentConfig);
        return ResponseEntity.created(new URI("/api/request-content-configs/" + requestContentConfig.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, requestContentConfig.getId().toString()))
            .body(requestContentConfig);
    }

    /**
     * {@code PUT  /request-content-configs/:id} : Updates an existing requestContentConfig.
     *
     * @param id the id of the requestContentConfig to save.
     * @param requestContentConfig the requestContentConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestContentConfig,
     * or with status {@code 400 (Bad Request)} if the requestContentConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestContentConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestContentConfig> updateRequestContentConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestContentConfig requestContentConfig
    ) throws URISyntaxException {
        log.debug("REST request to update RequestContentConfig : {}, {}", id, requestContentConfig);
        if (requestContentConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestContentConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestContentConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        requestContentConfig = requestContentConfigRepository.save(requestContentConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestContentConfig.getId().toString()))
            .body(requestContentConfig);
    }

    /**
     * {@code PATCH  /request-content-configs/:id} : Partial updates given fields of an existing requestContentConfig, field will ignore if it is null
     *
     * @param id the id of the requestContentConfig to save.
     * @param requestContentConfig the requestContentConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestContentConfig,
     * or with status {@code 400 (Bad Request)} if the requestContentConfig is not valid,
     * or with status {@code 404 (Not Found)} if the requestContentConfig is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestContentConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestContentConfig> partialUpdateRequestContentConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestContentConfig requestContentConfig
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestContentConfig partially : {}, {}", id, requestContentConfig);
        if (requestContentConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestContentConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestContentConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestContentConfig> result = requestContentConfigRepository
            .findById(requestContentConfig.getId())
            .map(existingRequestContentConfig -> {
                if (requestContentConfig.getRequestConfigId() != null) {
                    existingRequestContentConfig.setRequestConfigId(requestContentConfig.getRequestConfigId());
                }
                if (requestContentConfig.getParameter() != null) {
                    existingRequestContentConfig.setParameter(requestContentConfig.getParameter());
                }
                if (requestContentConfig.getIsMandatory() != null) {
                    existingRequestContentConfig.setIsMandatory(requestContentConfig.getIsMandatory());
                }

                return existingRequestContentConfig;
            })
            .map(requestContentConfigRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestContentConfig.getId().toString())
        );
    }

    /**
     * {@code GET  /request-content-configs} : get all the requestContentConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestContentConfigs in body.
     */
    @GetMapping("")
    public List<RequestContentConfig> getAllRequestContentConfigs() {
        log.debug("REST request to get all RequestContentConfigs");
        return requestContentConfigRepository.findAll();
    }

    /**
     * {@code GET  /request-content-configs/:id} : get the "id" requestContentConfig.
     *
     * @param id the id of the requestContentConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestContentConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestContentConfig> getRequestContentConfig(@PathVariable("id") Long id) {
        log.debug("REST request to get RequestContentConfig : {}", id);
        Optional<RequestContentConfig> requestContentConfig = requestContentConfigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestContentConfig);
    }

    /**
     * {@code DELETE  /request-content-configs/:id} : delete the "id" requestContentConfig.
     *
     * @param id the id of the requestContentConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestContentConfig(@PathVariable("id") Long id) {
        log.debug("REST request to delete RequestContentConfig : {}", id);
        requestContentConfigRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
