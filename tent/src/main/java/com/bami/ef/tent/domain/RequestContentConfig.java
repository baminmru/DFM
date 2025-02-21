package com.bami.ef.tent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @NotNull
    @Column(name = "request_config_id", nullable = false)
    private Integer requestConfigId;

    @Column(name = "parameter")
    private Integer parameter;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestContentConfig")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestTypes", "requestContentConfig" }, allowSetters = true)
    private Set<RequestConfig> requestConfigIds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestContentConfig")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestContentConfig" }, allowSetters = true)
    private Set<RequestParamDict> parameters = new HashSet<>();

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

    public Integer getRequestConfigId() {
        return this.requestConfigId;
    }

    public RequestContentConfig requestConfigId(Integer requestConfigId) {
        this.setRequestConfigId(requestConfigId);
        return this;
    }

    public void setRequestConfigId(Integer requestConfigId) {
        this.requestConfigId = requestConfigId;
    }

    public Integer getParameter() {
        return this.parameter;
    }

    public RequestContentConfig parameter(Integer parameter) {
        this.setParameter(parameter);
        return this;
    }

    public void setParameter(Integer parameter) {
        this.parameter = parameter;
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

    public Set<RequestConfig> getRequestConfigIds() {
        return this.requestConfigIds;
    }

    public void setRequestConfigIds(Set<RequestConfig> requestConfigs) {
        if (this.requestConfigIds != null) {
            this.requestConfigIds.forEach(i -> i.setRequestContentConfig(null));
        }
        if (requestConfigs != null) {
            requestConfigs.forEach(i -> i.setRequestContentConfig(this));
        }
        this.requestConfigIds = requestConfigs;
    }

    public RequestContentConfig requestConfigIds(Set<RequestConfig> requestConfigs) {
        this.setRequestConfigIds(requestConfigs);
        return this;
    }

    public RequestContentConfig addRequestConfigId(RequestConfig requestConfig) {
        this.requestConfigIds.add(requestConfig);
        requestConfig.setRequestContentConfig(this);
        return this;
    }

    public RequestContentConfig removeRequestConfigId(RequestConfig requestConfig) {
        this.requestConfigIds.remove(requestConfig);
        requestConfig.setRequestContentConfig(null);
        return this;
    }

    public Set<RequestParamDict> getParameters() {
        return this.parameters;
    }

    public void setParameters(Set<RequestParamDict> requestParamDicts) {
        if (this.parameters != null) {
            this.parameters.forEach(i -> i.setRequestContentConfig(null));
        }
        if (requestParamDicts != null) {
            requestParamDicts.forEach(i -> i.setRequestContentConfig(this));
        }
        this.parameters = requestParamDicts;
    }

    public RequestContentConfig parameters(Set<RequestParamDict> requestParamDicts) {
        this.setParameters(requestParamDicts);
        return this;
    }

    public RequestContentConfig addParameter(RequestParamDict requestParamDict) {
        this.parameters.add(requestParamDict);
        requestParamDict.setRequestContentConfig(this);
        return this;
    }

    public RequestContentConfig removeParameter(RequestParamDict requestParamDict) {
        this.parameters.remove(requestParamDict);
        requestParamDict.setRequestContentConfig(null);
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
            ", requestConfigId=" + getRequestConfigId() +
            ", parameter=" + getParameter() +
            ", isMandatory='" + getIsMandatory() + "'" +
            "}";
    }
}
