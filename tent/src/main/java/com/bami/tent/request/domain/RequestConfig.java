package com.bami.tent.request.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestConfigId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestConfigId", "parameter" }, allowSetters = true)
    private Set<RequestContentConfig> requestContentConfigs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestInfos", "requestConfigs" }, allowSetters = true)
    private RequestType requestType;

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

    public Set<RequestContentConfig> getRequestContentConfigs() {
        return this.requestContentConfigs;
    }

    public void setRequestContentConfigs(Set<RequestContentConfig> requestContentConfigs) {
        if (this.requestContentConfigs != null) {
            this.requestContentConfigs.forEach(i -> i.setRequestConfigId(null));
        }
        if (requestContentConfigs != null) {
            requestContentConfigs.forEach(i -> i.setRequestConfigId(this));
        }
        this.requestContentConfigs = requestContentConfigs;
    }

    public RequestConfig requestContentConfigs(Set<RequestContentConfig> requestContentConfigs) {
        this.setRequestContentConfigs(requestContentConfigs);
        return this;
    }

    public RequestConfig addRequestContentConfig(RequestContentConfig requestContentConfig) {
        this.requestContentConfigs.add(requestContentConfig);
        requestContentConfig.setRequestConfigId(this);
        return this;
    }

    public RequestConfig removeRequestContentConfig(RequestContentConfig requestContentConfig) {
        this.requestContentConfigs.remove(requestContentConfig);
        requestContentConfig.setRequestConfigId(null);
        return this;
    }

    public RequestType getRequestType() {
        return this.requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestConfig requestType(RequestType requestType) {
        this.setRequestType(requestType);
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
            "}";
    }
}
