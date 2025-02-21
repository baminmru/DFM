package com.bami.ef.tent.web.rest;

import com.bami.ef.tent.domain.RequestContent;
import com.bami.ef.tent.repository.RequestContentRepository;
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
 * REST controller for managing {@link com.bami.ef.tent.domain.RequestContent}.
 */
@RestController
@RequestMapping("/api/request-contents")
@Transactional
public class RequestContentResource {

    private static final Logger log = LoggerFactory.getLogger(RequestContentResource.class);

    private static final String ENTITY_NAME = "requestContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestContentRepository requestContentRepository;

    public RequestContentResource(RequestContentRepository requestContentRepository) {
        this.requestContentRepository = requestContentRepository;
    }

    /**
     * {@code POST  /request-contents} : Create a new requestContent.
     *
     * @param requestContent the requestContent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestContent, or with status {@code 400 (Bad Request)} if the requestContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestContent> createRequestContent(@Valid @RequestBody RequestContent requestContent)
        throws URISyntaxException {
        log.debug("REST request to save RequestContent : {}", requestContent);
        if (requestContent.getId() != null) {
            throw new BadRequestAlertException("A new requestContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        requestContent = requestContentRepository.save(requestContent);
        return ResponseEntity.created(new URI("/api/request-contents/" + requestContent.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, requestContent.getId().toString()))
            .body(requestContent);
    }

    /**
     * {@code PUT  /request-contents/:id} : Updates an existing requestContent.
     *
     * @param id the id of the requestContent to save.
     * @param requestContent the requestContent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestContent,
     * or with status {@code 400 (Bad Request)} if the requestContent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestContent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestContent> updateRequestContent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestContent requestContent
    ) throws URISyntaxException {
        log.debug("REST request to update RequestContent : {}, {}", id, requestContent);
        if (requestContent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestContent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        requestContent = requestContentRepository.save(requestContent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestContent.getId().toString()))
            .body(requestContent);
    }

    /**
     * {@code PATCH  /request-contents/:id} : Partial updates given fields of an existing requestContent, field will ignore if it is null
     *
     * @param id the id of the requestContent to save.
     * @param requestContent the requestContent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestContent,
     * or with status {@code 400 (Bad Request)} if the requestContent is not valid,
     * or with status {@code 404 (Not Found)} if the requestContent is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestContent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestContent> partialUpdateRequestContent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestContent requestContent
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestContent partially : {}, {}", id, requestContent);
        if (requestContent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestContent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestContent> result = requestContentRepository
            .findById(requestContent.getId())
            .map(existingRequestContent -> {
                if (requestContent.getRequestInfoId() != null) {
                    existingRequestContent.setRequestInfoId(requestContent.getRequestInfoId());
                }
                if (requestContent.getParamCode() != null) {
                    existingRequestContent.setParamCode(requestContent.getParamCode());
                }
                if (requestContent.getParamValue() != null) {
                    existingRequestContent.setParamValue(requestContent.getParamValue());
                }

                return existingRequestContent;
            })
            .map(requestContentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestContent.getId().toString())
        );
    }

    /**
     * {@code GET  /request-contents} : get all the requestContents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestContents in body.
     */
    @GetMapping("")
    public List<RequestContent> getAllRequestContents() {
        log.debug("REST request to get all RequestContents");
        return requestContentRepository.findAll();
    }

    /**
     * {@code GET  /request-contents/:id} : get the "id" requestContent.
     *
     * @param id the id of the requestContent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestContent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestContent> getRequestContent(@PathVariable("id") Long id) {
        log.debug("REST request to get RequestContent : {}", id);
        Optional<RequestContent> requestContent = requestContentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestContent);
    }

    /**
     * {@code DELETE  /request-contents/:id} : delete the "id" requestContent.
     *
     * @param id the id of the requestContent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestContent(@PathVariable("id") Long id) {
        log.debug("REST request to delete RequestContent : {}", id);
        requestContentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
