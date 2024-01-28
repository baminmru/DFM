package com.bami.dfm.domain;

import com.bami.dfm.domain.enumeration.FieldTypeEnum;
import com.bami.dfm.domain.enumeration.InputTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The DataField entity.
 */
@Schema(description = "The DataField entity.")
@Table("data_field")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("input_type")
    private InputTypeEnum inputType;

    @Column("field_type")
    private FieldTypeEnum fieldType;

    @Column("reference_root")
    private String referenceRoot;

    @Column("allow_null")
    private Boolean allowNull;

    @Column("name")
    private String name;

    @Column("caption")
    private String caption;

    @Column("documentation")
    private String documentation;

    @Transient
    @JsonIgnoreProperties(value = { "dataTreeBranch", "rootToFields" }, allowSetters = true)
    private Set<DataTreeRoot> dataTreeRoots = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "branchToFields", "branchParents", "dataTreeRoots", "branchChildren" },
        allowSetters = true
    )
    private Set<DataTreeBranch> dataTreeBranches = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "leafToFields", "dataTreeBranches" }, allowSetters = true)
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
            this.dataTreeRoots.forEach(i -> i.removeRootToField(this));
        }
        if (dataTreeRoots != null) {
            dataTreeRoots.forEach(i -> i.addRootToField(this));
        }
        this.dataTreeRoots = dataTreeRoots;
    }

    public DataField dataTreeRoots(Set<DataTreeRoot> dataTreeRoots) {
        this.setDataTreeRoots(dataTreeRoots);
        return this;
    }

    public DataField addDataTreeRoot(DataTreeRoot dataTreeRoot) {
        this.dataTreeRoots.add(dataTreeRoot);
        dataTreeRoot.getRootToFields().add(this);
        return this;
    }

    public DataField removeDataTreeRoot(DataTreeRoot dataTreeRoot) {
        this.dataTreeRoots.remove(dataTreeRoot);
        dataTreeRoot.getRootToFields().remove(this);
        return this;
    }

    public Set<DataTreeBranch> getDataTreeBranches() {
        return this.dataTreeBranches;
    }

    public void setDataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        if (this.dataTreeBranches != null) {
            this.dataTreeBranches.forEach(i -> i.removeBranchToField(this));
        }
        if (dataTreeBranches != null) {
            dataTreeBranches.forEach(i -> i.addBranchToField(this));
        }
        this.dataTreeBranches = dataTreeBranches;
    }

    public DataField dataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        this.setDataTreeBranches(dataTreeBranches);
        return this;
    }

    public DataField addDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.add(dataTreeBranch);
        dataTreeBranch.getBranchToFields().add(this);
        return this;
    }

    public DataField removeDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.remove(dataTreeBranch);
        dataTreeBranch.getBranchToFields().remove(this);
        return this;
    }

    public Set<DataTreeLeaf> getDataTreeLeaves() {
        return this.dataTreeLeaves;
    }

    public void setDataTreeLeaves(Set<DataTreeLeaf> dataTreeLeaves) {
        if (this.dataTreeLeaves != null) {
            this.dataTreeLeaves.forEach(i -> i.removeLeafToField(this));
        }
        if (dataTreeLeaves != null) {
            dataTreeLeaves.forEach(i -> i.addLeafToField(this));
        }
        this.dataTreeLeaves = dataTreeLeaves;
    }

    public DataField dataTreeLeaves(Set<DataTreeLeaf> dataTreeLeaves) {
        this.setDataTreeLeaves(dataTreeLeaves);
        return this;
    }

    public DataField addDataTreeLeaf(DataTreeLeaf dataTreeLeaf) {
        this.dataTreeLeaves.add(dataTreeLeaf);
        dataTreeLeaf.getLeafToFields().add(this);
        return this;
    }

    public DataField removeDataTreeLeaf(DataTreeLeaf dataTreeLeaf) {
        this.dataTreeLeaves.remove(dataTreeLeaf);
        dataTreeLeaf.getLeafToFields().remove(this);
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
