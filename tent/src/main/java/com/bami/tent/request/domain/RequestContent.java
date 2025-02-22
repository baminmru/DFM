package com.bami.tent.request.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
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
    @Size(max = 64)
    @Column(name = "param_code", length = 64, nullable = false)
    private String paramCode;

    @Size(max = 255)
    @Column(name = "param_value", length = 255)
    private String paramValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestContents", "requestType" }, allowSetters = true)
    private RequestInfo requestInfoId;

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

    public RequestInfo getRequestInfoId() {
        return this.requestInfoId;
    }

    public void setRequestInfoId(RequestInfo requestInfo) {
        this.requestInfoId = requestInfo;
    }

    public RequestContent requestInfoId(RequestInfo requestInfo) {
        this.setRequestInfoId(requestInfo);
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
            ", paramCode='" + getParamCode() + "'" +
            ", paramValue='" + getParamValue() + "'" +
            "}";
    }
}
