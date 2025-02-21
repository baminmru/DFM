package com.bami.ef.tent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
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

    @Size(max = 40)
    @Column(name = "code", length = 40)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestConfigIds", "parameters" }, allowSetters = true)
    private RequestContentConfig requestContentConfig;

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

    public RequestContentConfig getRequestContentConfig() {
        return this.requestContentConfig;
    }

    public void setRequestContentConfig(RequestContentConfig requestContentConfig) {
        this.requestContentConfig = requestContentConfig;
    }

    public RequestParamDict requestContentConfig(RequestContentConfig requestContentConfig) {
        this.setRequestContentConfig(requestContentConfig);
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
            "}";
    }
}
