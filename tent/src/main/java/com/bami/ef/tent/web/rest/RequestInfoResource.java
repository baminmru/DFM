package com.bami.ef.tent.web.rest;

import com.bami.ef.tent.domain.RequestInfo;
import com.bami.ef.tent.repository.RequestInfoRepository;
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
 * REST controller for managing {@link com.bami.ef.tent.domain.RequestInfo}.
 */
@RestController
@RequestMapping("/api/request-infos")
@Transactional
public class RequestInfoResource {

    private static final Logger log = LoggerFactory.getLogger(RequestInfoResource.class);

    private static final String ENTITY_NAME = "requestInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestInfoRepository requestInfoRepository;

    public RequestInfoResource(RequestInfoRepository requestInfoRepository) {
        this.requestInfoRepository = requestInfoRepository;
    }

    /**
     * {@code POST  /request-infos} : Create a new requestInfo.
     *
     * @param requestInfo the requestInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestInfo, or with status {@code 400 (Bad Request)} if the requestInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestInfo> createRequestInfo(@Valid @RequestBody RequestInfo requestInfo) throws URISyntaxException {
        log.debug("REST request to save RequestInfo : {}", requestInfo);
        if (requestInfo.getId() != null) {
            throw new BadRequestAlertException("A new requestInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        requestInfo = requestInfoRepository.save(requestInfo);
        return ResponseEntity.created(new URI("/api/request-infos/" + requestInfo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, requestInfo.getId().toString()))
            .body(requestInfo);
    }

    /**
     * {@code PUT  /request-infos/:id} : Updates an existing requestInfo.
     *
     * @param id the id of the requestInfo to save.
     * @param requestInfo the requestInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestInfo,
     * or with status {@code 400 (Bad Request)} if the requestInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestInfo> updateRequestInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestInfo requestInfo
    ) throws URISyntaxException {
        log.debug("REST request to update RequestInfo : {}, {}", id, requestInfo);
        if (requestInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        requestInfo = requestInfoRepository.save(requestInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestInfo.getId().toString()))
            .body(requestInfo);
    }

    /**
     * {@code PATCH  /request-infos/:id} : Partial updates given fields of an existing requestInfo, field will ignore if it is null
     *
     * @param id the id of the requestInfo to save.
     * @param requestInfo the requestInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestInfo,
     * or with status {@code 400 (Bad Request)} if the requestInfo is not valid,
     * or with status {@code 404 (Not Found)} if the requestInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestInfo> partialUpdateRequestInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestInfo requestInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestInfo partially : {}, {}", id, requestInfo);
        if (requestInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestInfo> result = requestInfoRepository
            .findById(requestInfo.getId())
            .map(existingRequestInfo -> {
                if (requestInfo.getRequestType() != null) {
                    existingRequestInfo.setRequestType(requestInfo.getRequestType());
                }
                if (requestInfo.getContract() != null) {
                    existingRequestInfo.setContract(requestInfo.getContract());
                }
                if (requestInfo.getRequestDate() != null) {
                    existingRequestInfo.setRequestDate(requestInfo.getRequestDate());
                }
                if (requestInfo.getEffectiveDateStart() != null) {
                    existingRequestInfo.setEffectiveDateStart(requestInfo.getEffectiveDateStart());
                }
                if (requestInfo.getEffectiveDateEnd() != null) {
                    existingRequestInfo.setEffectiveDateEnd(requestInfo.getEffectiveDateEnd());
                }
                if (requestInfo.getCreatedAt() != null) {
                    existingRequestInfo.setCreatedAt(requestInfo.getCreatedAt());
                }
                if (requestInfo.getCreatedBy() != null) {
                    existingRequestInfo.setCreatedBy(requestInfo.getCreatedBy());
                }
                if (requestInfo.getUpdatedAt() != null) {
                    existingRequestInfo.setUpdatedAt(requestInfo.getUpdatedAt());
                }
                if (requestInfo.getUpdatedBy() != null) {
                    existingRequestInfo.setUpdatedBy(requestInfo.getUpdatedBy());
                }

                return existingRequestInfo;
            })
            .map(requestInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /request-infos} : get all the requestInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestInfos in body.
     */
    @GetMapping("")
    public List<RequestInfo> getAllRequestInfos() {
        log.debug("REST request to get all RequestInfos");
        return requestInfoRepository.findAll();
    }

    /**
     * {@code GET  /request-infos/:id} : get the "id" requestInfo.
     *
     * @param id the id of the requestInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestInfo> getRequestInfo(@PathVariable("id") Long id) {
        log.debug("REST request to get RequestInfo : {}", id);
        Optional<RequestInfo> requestInfo = requestInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestInfo);
    }

    /**
     * {@code DELETE  /request-infos/:id} : delete the "id" requestInfo.
     *
     * @param id the id of the requestInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestInfo(@PathVariable("id") Long id) {
        log.debug("REST request to delete RequestInfo : {}", id);
        requestInfoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
