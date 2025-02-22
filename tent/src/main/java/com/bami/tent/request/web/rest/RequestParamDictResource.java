package com.bami.tent.request.web.rest;

import com.bami.tent.request.domain.RequestParamDict;
import com.bami.tent.request.repository.RequestParamDictRepository;
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
 * REST controller for managing {@link com.bami.tent.request.domain.RequestParamDict}.
 */
@RestController
@RequestMapping("/api/request-param-dicts")
@Transactional
public class RequestParamDictResource {

    private static final Logger log = LoggerFactory.getLogger(RequestParamDictResource.class);

    private static final String ENTITY_NAME = "requestParamDict";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestParamDictRepository requestParamDictRepository;

    public RequestParamDictResource(RequestParamDictRepository requestParamDictRepository) {
        this.requestParamDictRepository = requestParamDictRepository;
    }

    /**
     * {@code POST  /request-param-dicts} : Create a new requestParamDict.
     *
     * @param requestParamDict the requestParamDict to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestParamDict, or with status {@code 400 (Bad Request)} if the requestParamDict has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestParamDict> createRequestParamDict(@Valid @RequestBody RequestParamDict requestParamDict)
        throws URISyntaxException {
        log.debug("REST request to save RequestParamDict : {}", requestParamDict);
        if (requestParamDict.getId() != null) {
            throw new BadRequestAlertException("A new requestParamDict cannot already have an ID", ENTITY_NAME, "idexists");
        }
        requestParamDict = requestParamDictRepository.save(requestParamDict);
        return ResponseEntity.created(new URI("/api/request-param-dicts/" + requestParamDict.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, requestParamDict.getId().toString()))
            .body(requestParamDict);
    }

    /**
     * {@code PUT  /request-param-dicts/:id} : Updates an existing requestParamDict.
     *
     * @param id the id of the requestParamDict to save.
     * @param requestParamDict the requestParamDict to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestParamDict,
     * or with status {@code 400 (Bad Request)} if the requestParamDict is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestParamDict couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestParamDict> updateRequestParamDict(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestParamDict requestParamDict
    ) throws URISyntaxException {
        log.debug("REST request to update RequestParamDict : {}, {}", id, requestParamDict);
        if (requestParamDict.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestParamDict.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestParamDictRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        requestParamDict = requestParamDictRepository.save(requestParamDict);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestParamDict.getId().toString()))
            .body(requestParamDict);
    }

    /**
     * {@code PATCH  /request-param-dicts/:id} : Partial updates given fields of an existing requestParamDict, field will ignore if it is null
     *
     * @param id the id of the requestParamDict to save.
     * @param requestParamDict the requestParamDict to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestParamDict,
     * or with status {@code 400 (Bad Request)} if the requestParamDict is not valid,
     * or with status {@code 404 (Not Found)} if the requestParamDict is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestParamDict couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestParamDict> partialUpdateRequestParamDict(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestParamDict requestParamDict
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestParamDict partially : {}, {}", id, requestParamDict);
        if (requestParamDict.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestParamDict.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestParamDictRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestParamDict> result = requestParamDictRepository
            .findById(requestParamDict.getId())
            .map(existingRequestParamDict -> {
                if (requestParamDict.getCode() != null) {
                    existingRequestParamDict.setCode(requestParamDict.getCode());
                }
                if (requestParamDict.getName() != null) {
                    existingRequestParamDict.setName(requestParamDict.getName());
                }
                if (requestParamDict.getValueArray() != null) {
                    existingRequestParamDict.setValueArray(requestParamDict.getValueArray());
                }
                if (requestParamDict.getReferenceTo() != null) {
                    existingRequestParamDict.setReferenceTo(requestParamDict.getReferenceTo());
                }

                return existingRequestParamDict;
            })
            .map(requestParamDictRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestParamDict.getId().toString())
        );
    }

    /**
     * {@code GET  /request-param-dicts} : get all the requestParamDicts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestParamDicts in body.
     */
    @GetMapping("")
    public List<RequestParamDict> getAllRequestParamDicts() {
        log.debug("REST request to get all RequestParamDicts");
        return requestParamDictRepository.findAll();
    }

    /**
     * {@code GET  /request-param-dicts/:id} : get the "id" requestParamDict.
     *
     * @param id the id of the requestParamDict to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestParamDict, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestParamDict> getRequestParamDict(@PathVariable("id") Long id) {
        log.debug("REST request to get RequestParamDict : {}", id);
        Optional<RequestParamDict> requestParamDict = requestParamDictRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestParamDict);
    }

    /**
     * {@code DELETE  /request-param-dicts/:id} : delete the "id" requestParamDict.
     *
     * @param id the id of the requestParamDict to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestParamDict(@PathVariable("id") Long id) {
        log.debug("REST request to delete RequestParamDict : {}", id);
        requestParamDictRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
