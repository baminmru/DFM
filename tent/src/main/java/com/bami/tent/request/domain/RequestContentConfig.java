package com.bami.tent.request.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RequestContentConfig.
 */
@Entity
@Table(name = "request_content_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestContentConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Size(max = 64)
    @Column(name = "created_by", length = 64)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Size(max = 64)
    @Column(name = "updated_by", length = 64)
    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestContentConfigs", "requestType" }, allowSetters = true)
    private RequestConfig requestConfigId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestContentConfigs" }, allowSetters = true)
    private RequestParamDict parameter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestContentConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsMandatory() {
        return this.isMandatory;
    }

    public RequestContentConfig isMandatory(Boolean isMandatory) {
        this.setIsMandatory(isMandatory);
        return this;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public RequestContentConfig createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public RequestContentConfig createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public RequestContentConfig updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public RequestContentConfig updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public RequestConfig getRequestConfigId() {
        return this.requestConfigId;
    }

    public void setRequestConfigId(RequestConfig requestConfig) {
        this.requestConfigId = requestConfig;
    }

    public RequestContentConfig requestConfigId(RequestConfig requestConfig) {
        this.setRequestConfigId(requestConfig);
        return this;
    }

    public RequestParamDict getParameter() {
        return this.parameter;
    }

    public void setParameter(RequestParamDict requestParamDict) {
        this.parameter = requestParamDict;
    }

    public RequestContentConfig parameter(RequestParamDict requestParamDict) {
        this.setParameter(requestParamDict);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestContentConfig)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestContentConfig) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestContentConfig{" +
            "id=" + getId() +
            ", isMandatory='" + getIsMandatory() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
