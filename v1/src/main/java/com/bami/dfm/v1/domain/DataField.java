package com.bami.dfm.v1.domain;

import com.bami.dfm.v1.domain.enumeration.FieldTypeEnum;
import com.bami.dfm.v1.domain.enumeration.InputTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The DataField entity.
 */
@Schema(description = "The DataField entity.")
@Entity
@Table(name = "data_field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_type")
    private InputTypeEnum inputType;

    @Enumerated(EnumType.STRING)
    @Column(name = "field_type")
    private FieldTypeEnum fieldType;

    @Column(name = "reference_root")
    private String referenceRoot;

    @Column(name = "allow_null")
    private Boolean allowNull;

    @Column(name = "name")
    private String name;

    @Column(name = "caption")
    private String caption;

    @Column(name = "documentation")
    private String documentation;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dataFields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dataTreeBranch", "dataFields", "dataForests" }, allowSetters = true)
    private Set<DataTreeRoot> dataTreeRoots = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dataFields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "dataFields", "dataTreeBranches", "dataTreeRoots", "dataTreeBranches" },
        allowSetters = true
    )
    private Set<DataTreeBranch> dataTreeBranches = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dataFields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dataFields", "dataTreeBranches" }, allowSetters = true)
    private Set<DataTreeLeaf> dataTreeLeaves = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DataField id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InputTypeEnum getInputType() {
        return this.inputType;
    }

    public DataField inputType(InputTypeEnum inputType) {
        this.setInputType(inputType);
        return this;
    }

    public void setInputType(InputTypeEnum inputType) {
        this.inputType = inputType;
    }

    public FieldTypeEnum getFieldType() {
        return this.fieldType;
    }

    public DataField fieldType(FieldTypeEnum fieldType) {
        this.setFieldType(fieldType);
        return this;
    }

    public void setFieldType(FieldTypeEnum fieldType) {
        this.fieldType = fieldType;
    }

    public String getReferenceRoot() {
        return this.referenceRoot;
    }

    public DataField referenceRoot(String referenceRoot) {
        this.setReferenceRoot(referenceRoot);
        return this;
    }

    public void setReferenceRoot(String referenceRoot) {
        this.referenceRoot = referenceRoot;
    }

    public Boolean getAllowNull() {
        return this.allowNull;
    }

    public DataField allowNull(Boolean allowNull) {
        this.setAllowNull(allowNull);
        return this;
    }

    public void setAllowNull(Boolean allowNull) {
        this.allowNull = allowNull;
    }

    public String getName() {
        return this.name;
    }

    public DataField name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return this.caption;
    }

    public DataField caption(String caption) {
        this.setCaption(caption);
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public DataField documentation(String documentation) {
        this.setDocumentation(documentation);
        return this;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public Set<DataTreeRoot> getDataTreeRoots() {
        return this.dataTreeRoots;
    }

    public void setDataTreeRoots(Set<DataTreeRoot> dataTreeRoots) {
        if (this.dataTreeRoots != null) {
            this.dataTreeRoots.forEach(i -> i.removeDataField(this));
        }
        if (dataTreeRoots != null) {
            dataTreeRoots.forEach(i -> i.addDataField(this));
        }
        this.dataTreeRoots = dataTreeRoots;
    }

    public DataField dataTreeRoots(Set<DataTreeRoot> dataTreeRoots) {
        this.setDataTreeRoots(dataTreeRoots);
        return this;
    }

    public DataField addDataTreeRoot(DataTreeRoot dataTreeRoot) {
        this.dataTreeRoots.add(dataTreeRoot);
        dataTreeRoot.getDataFields().add(this);
        return this;
    }

    public DataField removeDataTreeRoot(DataTreeRoot dataTreeRoot) {
        this.dataTreeRoots.remove(dataTreeRoot);
        dataTreeRoot.getDataFields().remove(this);
        return this;
    }

    public Set<DataTreeBranch> getDataTreeBranches() {
        return this.dataTreeBranches;
    }

    public void setDataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        if (this.dataTreeBranches != null) {
            this.dataTreeBranches.forEach(i -> i.removeDataField(this));
        }
        if (dataTreeBranches != null) {
            dataTreeBranches.forEach(i -> i.addDataField(this));
        }
        this.dataTreeBranches = dataTreeBranches;
    }

    public DataField dataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        this.setDataTreeBranches(dataTreeBranches);
        return this;
    }

    public DataField addDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.add(dataTreeBranch);
        dataTreeBranch.getDataFields().add(this);
        return this;
    }

    public DataField removeDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.remove(dataTreeBranch);
        dataTreeBranch.getDataFields().remove(this);
        return this;
    }

    public Set<DataTreeLeaf> getDataTreeLeaves() {
        return this.dataTreeLeaves;
    }

    public void setDataTreeLeaves(Set<DataTreeLeaf> dataTreeLeaves) {
        if (this.dataTreeLeaves != null) {
            this.dataTreeLeaves.forEach(i -> i.removeDataField(this));
        }
        if (dataTreeLeaves != null) {
            dataTreeLeaves.forEach(i -> i.addDataField(this));
        }
        this.dataTreeLeaves = dataTreeLeaves;
    }

    public DataField dataTreeLeaves(Set<DataTreeLeaf> dataTreeLeaves) {
        this.setDataTreeLeaves(dataTreeLeaves);
        return this;
    }

    public DataField addDataTreeLeaf(DataTreeLeaf dataTreeLeaf) {
        this.dataTreeLeaves.add(dataTreeLeaf);
        dataTreeLeaf.getDataFields().add(this);
        return this;
    }

    public DataField removeDataTreeLeaf(DataTreeLeaf dataTreeLeaf) {
        this.dataTreeLeaves.remove(dataTreeLeaf);
        dataTreeLeaf.getDataFields().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataField)) {
            return false;
        }
        return id != null && id.equals(((DataField) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataField{" +
            "id=" + getId() +
            ", inputType='" + getInputType() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", referenceRoot='" + getReferenceRoot() + "'" +
            ", allowNull='" + getAllowNull() + "'" +
            ", name='" + getName() + "'" +
            ", caption='" + getCaption() + "'" +
            ", documentation='" + getDocumentation() + "'" +
            "}";
    }
}
