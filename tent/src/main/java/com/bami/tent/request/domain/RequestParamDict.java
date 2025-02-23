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
 * A RequestParamDict.
 */
@Entity
@Table(name = "request_param_dict")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestParamDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "code", length = 40, nullable = false)
    private String code;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Size(max = 64)
    @Column(name = "paramtype", length = 64)
    private String paramtype;

    @Column(name = "value_array")
    private Boolean valueArray;

    @Size(max = 64)
    @Column(name = "reference_to", length = 64)
    private String referenceTo;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parameter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestConfigId", "parameter" }, allowSetters = true)
    private Set<RequestContentConfig> requestContentConfigs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestParamDict id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public RequestParamDict code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public RequestParamDict name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParamtype() {
        return this.paramtype;
    }

    public RequestParamDict paramtype(String paramtype) {
        this.setParamtype(paramtype);
        return this;
    }

    public void setParamtype(String paramtype) {
        this.paramtype = paramtype;
    }

    public Boolean getValueArray() {
        return this.valueArray;
    }

    public RequestParamDict valueArray(Boolean valueArray) {
        this.setValueArray(valueArray);
        return this;
    }

    public void setValueArray(Boolean valueArray) {
        this.valueArray = valueArray;
    }

    public String getReferenceTo() {
        return this.referenceTo;
    }

    public RequestParamDict referenceTo(String referenceTo) {
        this.setReferenceTo(referenceTo);
        return this;
    }

    public void setReferenceTo(String referenceTo) {
        this.referenceTo = referenceTo;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public RequestParamDict createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public RequestParamDict createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public RequestParamDict updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public RequestParamDict updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<RequestContentConfig> getRequestContentConfigs() {
        return this.requestContentConfigs;
    }

    public void setRequestContentConfigs(Set<RequestContentConfig> requestContentConfigs) {
        if (this.requestContentConfigs != null) {
            this.requestContentConfigs.forEach(i -> i.setParameter(null));
        }
        if (requestContentConfigs != null) {
            requestContentConfigs.forEach(i -> i.setParameter(this));
        }
        this.requestContentConfigs = requestContentConfigs;
    }

    public RequestParamDict requestContentConfigs(Set<RequestContentConfig> requestContentConfigs) {
        this.setRequestContentConfigs(requestContentConfigs);
        return this;
    }

    public RequestParamDict addRequestContentConfig(RequestContentConfig requestContentConfig) {
        this.requestContentConfigs.add(requestContentConfig);
        requestContentConfig.setParameter(this);
        return this;
    }

    public RequestParamDict removeRequestContentConfig(RequestContentConfig requestContentConfig) {
        this.requestContentConfigs.remove(requestContentConfig);
        requestContentConfig.setParameter(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestParamDict)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestParamDict) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestParamDict{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", paramtype='" + getParamtype() + "'" +
            ", valueArray='" + getValueArray() + "'" +
            ", referenceTo='" + getReferenceTo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
