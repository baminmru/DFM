package com.bami.tent.request.web.rest;

import com.bami.tent.request.domain.RequestType;
import com.bami.tent.request.repository.RequestTypeRepository;
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
 * REST controller for managing {@link com.bami.tent.request.domain.RequestType}.
 */
@RestController
@RequestMapping("/api/request-types")
@Transactional
public class RequestTypeResource {

    private static final Logger log = LoggerFactory.getLogger(RequestTypeResource.class);

    private static final String ENTITY_NAME = "requestType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestTypeRepository requestTypeRepository;

    public RequestTypeResource(RequestTypeRepository requestTypeRepository) {
        this.requestTypeRepository = requestTypeRepository;
    }

    /**
     * {@code POST  /request-types} : Create a new requestType.
     *
     * @param requestType the requestType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestType, or with status {@code 400 (Bad Request)} if the requestType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestType> createRequestType(@Valid @RequestBody RequestType requestType) throws URISyntaxException {
        log.debug("REST request to save RequestType : {}", requestType);
        if (requestType.getId() != null) {
            throw new BadRequestAlertException("A new requestType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        requestType = requestTypeRepository.save(requestType);
        return ResponseEntity.created(new URI("/api/request-types/" + requestType.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, requestType.getId().toString()))
            .body(requestType);
    }

    /**
     * {@code PUT  /request-types/:id} : Updates an existing requestType.
     *
     * @param id the id of the requestType to save.
     * @param requestType the requestType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestType,
     * or with status {@code 400 (Bad Request)} if the requestType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestType> updateRequestType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestType requestType
    ) throws URISyntaxException {
        log.debug("REST request to update RequestType : {}, {}", id, requestType);
        if (requestType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        requestType = requestTypeRepository.save(requestType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestType.getId().toString()))
            .body(requestType);
    }

    /**
     * {@code PATCH  /request-types/:id} : Partial updates given fields of an existing requestType, field will ignore if it is null
     *
     * @param id the id of the requestType to save.
     * @param requestType the requestType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestType,
     * or with status {@code 400 (Bad Request)} if the requestType is not valid,
     * or with status {@code 404 (Not Found)} if the requestType is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestType> partialUpdateRequestType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestType requestType
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestType partially : {}, {}", id, requestType);
        if (requestType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestType> result = requestTypeRepository
            .findById(requestType.getId())
            .map(existingRequestType -> {
                if (requestType.getCode() != null) {
                    existingRequestType.setCode(requestType.getCode());
                }
                if (requestType.getName() != null) {
                    existingRequestType.setName(requestType.getName());
                }
                if (requestType.getCreatedAt() != null) {
                    existingRequestType.setCreatedAt(requestType.getCreatedAt());
                }
                if (requestType.getCreatedBy() != null) {
                    existingRequestType.setCreatedBy(requestType.getCreatedBy());
                }
                if (requestType.getUpdatedAt() != null) {
                    existingRequestType.setUpdatedAt(requestType.getUpdatedAt());
                }
                if (requestType.getUpdatedBy() != null) {
                    existingRequestType.setUpdatedBy(requestType.getUpdatedBy());
                }

                return existingRequestType;
            })
            .map(requestTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestType.getId().toString())
        );
    }

    /**
     * {@code GET  /request-types} : get all the requestTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestTypes in body.
     */
    @GetMapping("")
    public List<RequestType> getAllRequestTypes() {
        log.debug("REST request to get all RequestTypes");
        return requestTypeRepository.findAll();
    }

    /**
     * {@code GET  /request-types/:id} : get the "id" requestType.
     *
     * @param id the id of the requestType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestType> getRequestType(@PathVariable("id") Long id) {
        log.debug("REST request to get RequestType : {}", id);
        Optional<RequestType> requestType = requestTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestType);
    }

    /**
     * {@code DELETE  /request-types/:id} : delete the "id" requestType.
     *
     * @param id the id of the requestType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestType(@PathVariable("id") Long id) {
        log.debug("REST request to delete RequestType : {}", id);
        requestTypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
