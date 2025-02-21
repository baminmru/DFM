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
 * A RequestConfig.
 */
@Entity
@Table(name = "request_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "request_type", nullable = false)
    private Integer requestType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestConfig")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestInfo", "requestConfig" }, allowSetters = true)
    private Set<RequestType> requestTypes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestConfigIds", "parameters" }, allowSetters = true)
    private RequestContentConfig requestContentConfig;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRequestType() {
        return this.requestType;
    }

    public RequestConfig requestType(Integer requestType) {
        this.setRequestType(requestType);
        return this;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Set<RequestType> getRequestTypes() {
        return this.requestTypes;
    }

    public void setRequestTypes(Set<RequestType> requestTypes) {
        if (this.requestTypes != null) {
            this.requestTypes.forEach(i -> i.setRequestConfig(null));
        }
        if (requestTypes != null) {
            requestTypes.forEach(i -> i.setRequestConfig(this));
        }
        this.requestTypes = requestTypes;
    }

    public RequestConfig requestTypes(Set<RequestType> requestTypes) {
        this.setRequestTypes(requestTypes);
        return this;
    }

    public RequestConfig addRequestType(RequestType requestType) {
        this.requestTypes.add(requestType);
        requestType.setRequestConfig(this);
        return this;
    }

    public RequestConfig removeRequestType(RequestType requestType) {
        this.requestTypes.remove(requestType);
        requestType.setRequestConfig(null);
        return this;
    }

    public RequestContentConfig getRequestContentConfig() {
        return this.requestContentConfig;
    }

    public void setRequestContentConfig(RequestContentConfig requestContentConfig) {
        this.requestContentConfig = requestContentConfig;
    }

    public RequestConfig requestContentConfig(RequestContentConfig requestContentConfig) {
        this.setRequestContentConfig(requestContentConfig);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestConfig)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestConfig) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestConfig{" +
            "id=" + getId() +
            ", requestType=" + getRequestType() +
            "}";
    }
}
