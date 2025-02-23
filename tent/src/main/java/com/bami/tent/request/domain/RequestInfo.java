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
 * A RequestInfo.
 */
@Entity
@Table(name = "request_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "contract", nullable = false)
    private Integer contract;

    @NotNull
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @NotNull
    @Size(max = 40)
    @Column(name = "code_at_source", length = 40, nullable = false)
    private String codeAtSource;

    @Column(name = "effective_date_start")
    private LocalDate effectiveDateStart;

    @Column(name = "effective_date_end")
    private LocalDate effectiveDateEnd;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestInfoId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestInfoId" }, allowSetters = true)
    private Set<RequestContent> requestContents = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestInfos", "requestConfigs" }, allowSetters = true)
    private RequestType requestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestInfos" }, allowSetters = true)
    private SourceSystem requestSource;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContract() {
        return this.contract;
    }

    public RequestInfo contract(Integer contract) {
        this.setContract(contract);
        return this;
    }

    public void setContract(Integer contract) {
        this.contract = contract;
    }

    public LocalDate getRequestDate() {
        return this.requestDate;
    }

    public RequestInfo requestDate(LocalDate requestDate) {
        this.setRequestDate(requestDate);
        return this;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getCodeAtSource() {
        return this.codeAtSource;
    }

    public RequestInfo codeAtSource(String codeAtSource) {
        this.setCodeAtSource(codeAtSource);
        return this;
    }

    public void setCodeAtSource(String codeAtSource) {
        this.codeAtSource = codeAtSource;
    }

    public LocalDate getEffectiveDateStart() {
        return this.effectiveDateStart;
    }

    public RequestInfo effectiveDateStart(LocalDate effectiveDateStart) {
        this.setEffectiveDateStart(effectiveDateStart);
        return this;
    }

    public void setEffectiveDateStart(LocalDate effectiveDateStart) {
        this.effectiveDateStart = effectiveDateStart;
    }

    public LocalDate getEffectiveDateEnd() {
        return this.effectiveDateEnd;
    }

    public RequestInfo effectiveDateEnd(LocalDate effectiveDateEnd) {
        this.setEffectiveDateEnd(effectiveDateEnd);
        return this;
    }

    public void setEffectiveDateEnd(LocalDate effectiveDateEnd) {
        this.effectiveDateEnd = effectiveDateEnd;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public RequestInfo createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public RequestInfo createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public RequestInfo updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public RequestInfo updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<RequestContent> getRequestContents() {
        return this.requestContents;
    }

    public void setRequestContents(Set<RequestContent> requestContents) {
        if (this.requestContents != null) {
            this.requestContents.forEach(i -> i.setRequestInfoId(null));
        }
        if (requestContents != null) {
            requestContents.forEach(i -> i.setRequestInfoId(this));
        }
        this.requestContents = requestContents;
    }

    public RequestInfo requestContents(Set<RequestContent> requestContents) {
        this.setRequestContents(requestContents);
        return this;
    }

    public RequestInfo addRequestContent(RequestContent requestContent) {
        this.requestContents.add(requestContent);
        requestContent.setRequestInfoId(this);
        return this;
    }

    public RequestInfo removeRequestContent(RequestContent requestContent) {
        this.requestContents.remove(requestContent);
        requestContent.setRequestInfoId(null);
        return this;
    }

    public RequestType getRequestType() {
        return this.requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestInfo requestType(RequestType requestType) {
        this.setRequestType(requestType);
        return this;
    }

    public SourceSystem getRequestSource() {
        return this.requestSource;
    }

    public void setRequestSource(SourceSystem sourceSystem) {
        this.requestSource = sourceSystem;
    }

    public RequestInfo requestSource(SourceSystem sourceSystem) {
        this.setRequestSource(sourceSystem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestInfo{" +
            "id=" + getId() +
            ", contract=" + getContract() +
            ", requestDate='" + getRequestDate() + "'" +
            ", codeAtSource='" + getCodeAtSource() + "'" +
            ", effectiveDateStart='" + getEffectiveDateStart() + "'" +
            ", effectiveDateEnd='" + getEffectiveDateEnd() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
