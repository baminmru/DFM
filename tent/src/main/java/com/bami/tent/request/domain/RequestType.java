package com.bami.tent.request.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RequestType.
 */
@Entity
@Table(name = "request_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 64)
    @Column(name = "code", length = 64, nullable = false)
    private String code;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestContents", "requestType", "requestSource" }, allowSetters = true)
    private Set<RequestInfo> requestInfos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestContentConfigs", "requestType" }, allowSetters = true)
    private Set<RequestConfig> requestConfigs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public RequestType code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public RequestType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public RequestType createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public RequestType createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public RequestType updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public RequestType updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<RequestInfo> getRequestInfos() {
        return this.requestInfos;
    }

    public void setRequestInfos(Set<RequestInfo> requestInfos) {
        if (this.requestInfos != null) {
            this.requestInfos.forEach(i -> i.setRequestType(null));
        }
        if (requestInfos != null) {
            requestInfos.forEach(i -> i.setRequestType(this));
        }
        this.requestInfos = requestInfos;
    }

    public RequestType requestInfos(Set<RequestInfo> requestInfos) {
        this.setRequestInfos(requestInfos);
        return this;
    }

    public RequestType addRequestInfo(RequestInfo requestInfo) {
        this.requestInfos.add(requestInfo);
        requestInfo.setRequestType(this);
        return this;
    }

    public RequestType removeRequestInfo(RequestInfo requestInfo) {
        this.requestInfos.remove(requestInfo);
        requestInfo.setRequestType(null);
        return this;
    }

    public Set<RequestConfig> getRequestConfigs() {
        return this.requestConfigs;
    }

    public void setRequestConfigs(Set<RequestConfig> requestConfigs) {
        if (this.requestConfigs != null) {
            this.requestConfigs.forEach(i -> i.setRequestType(null));
        }
        if (requestConfigs != null) {
            requestConfigs.forEach(i -> i.setRequestType(this));
        }
        this.requestConfigs = requestConfigs;
    }

    public RequestType requestConfigs(Set<RequestConfig> requestConfigs) {
        this.setRequestConfigs(requestConfigs);
        return this;
    }

    public RequestType addRequestConfig(RequestConfig requestConfig) {
        this.requestConfigs.add(requestConfig);
        requestConfig.setRequestType(this);
        return this;
    }

    public RequestType removeRequestConfig(RequestConfig requestConfig) {
        this.requestConfigs.remove(requestConfig);
        requestConfig.setRequestType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestType)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
