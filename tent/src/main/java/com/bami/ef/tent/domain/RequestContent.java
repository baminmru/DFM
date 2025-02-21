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
 * A RequestContent.
 */
@Entity
@Table(name = "request_content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "request_info_id", nullable = false)
    private Integer requestInfoId;

    @NotNull
    @Size(max = 64)
    @Column(name = "param_code", length = 64, nullable = false)
    private String paramCode;

    @Size(max = 255)
    @Column(name = "param_value", length = 255)
    private String paramValue;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestContent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestTypes", "requestContent" }, allowSetters = true)
    private Set<RequestInfo> requestInfoIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestContent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRequestInfoId() {
        return this.requestInfoId;
    }

    public RequestContent requestInfoId(Integer requestInfoId) {
        this.setRequestInfoId(requestInfoId);
        return this;
    }

    public void setRequestInfoId(Integer requestInfoId) {
        this.requestInfoId = requestInfoId;
    }

    public String getParamCode() {
        return this.paramCode;
    }

    public RequestContent paramCode(String paramCode) {
        this.setParamCode(paramCode);
        return this;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamValue() {
        return this.paramValue;
    }

    public RequestContent paramValue(String paramValue) {
        this.setParamValue(paramValue);
        return this;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Set<RequestInfo> getRequestInfoIds() {
        return this.requestInfoIds;
    }

    public void setRequestInfoIds(Set<RequestInfo> requestInfos) {
        if (this.requestInfoIds != null) {
            this.requestInfoIds.forEach(i -> i.setRequestContent(null));
        }
        if (requestInfos != null) {
            requestInfos.forEach(i -> i.setRequestContent(this));
        }
        this.requestInfoIds = requestInfos;
    }

    public RequestContent requestInfoIds(Set<RequestInfo> requestInfos) {
        this.setRequestInfoIds(requestInfos);
        return this;
    }

    public RequestContent addRequestInfoId(RequestInfo requestInfo) {
        this.requestInfoIds.add(requestInfo);
        requestInfo.setRequestContent(this);
        return this;
    }

    public RequestContent removeRequestInfoId(RequestInfo requestInfo) {
        this.requestInfoIds.remove(requestInfo);
        requestInfo.setRequestContent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestContent)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestContent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestContent{" +
            "id=" + getId() +
            ", requestInfoId=" + getRequestInfoId() +
            ", paramCode='" + getParamCode() + "'" +
            ", paramValue='" + getParamValue() + "'" +
            "}";
    }
}
