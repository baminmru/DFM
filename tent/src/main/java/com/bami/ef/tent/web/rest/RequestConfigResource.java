package com.bami.ef.tent.web.rest;

import com.bami.ef.tent.domain.RequestConfig;
import com.bami.ef.tent.repository.RequestConfigRepository;
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
 * REST controller for managing {@link com.bami.ef.tent.domain.RequestConfig}.
 */
@RestController
@RequestMapping("/api/request-configs")
@Transactional
public class RequestConfigResource {

    private static final Logger log = LoggerFactory.getLogger(RequestConfigResource.class);

    private static final String ENTITY_NAME = "requestConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestConfigRepository requestConfigRepository;

    public RequestConfigResource(RequestConfigRepository requestConfigRepository) {
        this.requestConfigRepository = requestConfigRepository;
    }

    /**
     * {@code POST  /request-configs} : Create a new requestConfig.
     *
     * @param requestConfig the requestConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestConfig, or with status {@code 400 (Bad Request)} if the requestConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestConfig> createRequestConfig(@Valid @RequestBody RequestConfig requestConfig) throws URISyntaxException {
        log.debug("REST request to save RequestConfig : {}", requestConfig);
        if (requestConfig.getId() != null) {
            throw new BadRequestAlertException("A new requestConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        requestConfig = requestConfigRepository.save(requestConfig);
        return ResponseEntity.created(new URI("/api/request-configs/" + requestConfig.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, requestConfig.getId().toString()))
            .body(requestConfig);
    }

    /**
     * {@code PUT  /request-configs/:id} : Updates an existing requestConfig.
     *
     * @param id the id of the requestConfig to save.
     * @param requestConfig the requestConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestConfig,
     * or with status {@code 400 (Bad Request)} if the requestConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestConfig> updateRequestConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestConfig requestConfig
    ) throws URISyntaxException {
        log.debug("REST request to update RequestConfig : {}, {}", id, requestConfig);
        if (requestConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        requestConfig = requestConfigRepository.save(requestConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestConfig.getId().toString()))
            .body(requestConfig);
    }

    /**
     * {@code PATCH  /request-configs/:id} : Partial updates given fields of an existing requestConfig, field will ignore if it is null
     *
     * @param id the id of the requestConfig to save.
     * @param requestConfig the requestConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestConfig,
     * or with status {@code 400 (Bad Request)} if the requestConfig is not valid,
     * or with status {@code 404 (Not Found)} if the requestConfig is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestConfig> partialUpdateRequestConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestConfig requestConfig
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestConfig partially : {}, {}", id, requestConfig);
        if (requestConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestConfig> result = requestConfigRepository
            .findById(requestConfig.getId())
            .map(existingRequestConfig -> {
                if (requestConfig.getRequestType() != null) {
                    existingRequestConfig.setRequestType(requestConfig.getRequestType());
                }

                return existingRequestConfig;
            })
            .map(requestConfigRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestConfig.getId().toString())
        );
    }

    /**
     * {@code GET  /request-configs} : get all the requestConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestConfigs in body.
     */
    @GetMapping("")
    public List<RequestConfig> getAllRequestConfigs() {
        log.debug("REST request to get all RequestConfigs");
        return requestConfigRepository.findAll();
    }

    /**
     * {@code GET  /request-configs/:id} : get the "id" requestConfig.
     *
     * @param id the id of the requestConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestConfig> getRequestConfig(@PathVariable("id") Long id) {
        log.debug("REST request to get RequestConfig : {}", id);
        Optional<RequestConfig> requestConfig = requestConfigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestConfig);
    }

    /**
     * {@code DELETE  /request-configs/:id} : delete the "id" requestConfig.
     *
     * @param id the id of the requestConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestConfig(@PathVariable("id") Long id) {
        log.debug("REST request to delete RequestConfig : {}", id);
        requestConfigRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
