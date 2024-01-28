package com.bami.ef.gateway.domain;

import com.bami.ef.gateway.domain.enumeration.StereoTypeEnum;
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
 * A DataTreeRoot.
 */
@Table("data_tree_root")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataTreeRoot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    /**
     * stereoType
     */
    @Schema(description = "stereoType")
    @Column("stereo_type")
    private StereoTypeEnum stereoType;

    @Column("name")
    private String name;

    @Column("caption")
    private String caption;

    @Column("documentation")
    private String documentation;

    @Transient
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "branchToFields", "branchParents", "dataTreeRoots", "branchChildren" },
        allowSetters = true
    )
    private DataTreeBranch dataTreeBranch;

    @Transient
    @JsonIgnoreProperties(value = { "dataTreeRoots", "dataTreeBranches", "dataTreeLeaves" }, allowSetters = true)
    private Set<DataField> rootToFields = new HashSet<>();

    @Column("data_tree_branch_id")
    private Long dataTreeBranchId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DataTreeRoot id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StereoTypeEnum getStereoType() {
        return this.stereoType;
    }

    public DataTreeRoot stereoType(StereoTypeEnum stereoType) {
        this.setStereoType(stereoType);
        return this;
    }

    public void setStereoType(StereoTypeEnum stereoType) {
        this.stereoType = stereoType;
    }

    public String getName() {
        return this.name;
    }

    public DataTreeRoot name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return this.caption;
    }

    public DataTreeRoot caption(String caption) {
        this.setCaption(caption);
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public DataTreeRoot documentation(String documentation) {
        this.setDocumentation(documentation);
        return this;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public DataTreeBranch getDataTreeBranch() {
        return this.dataTreeBranch;
    }

    public void setDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranch = dataTreeBranch;
        this.dataTreeBranchId = dataTreeBranch != null ? dataTreeBranch.getId() : null;
    }

    public DataTreeRoot dataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.setDataTreeBranch(dataTreeBranch);
        return this;
    }

    public Set<DataField> getRootToFields() {
        return this.rootToFields;
    }

    public void setRootToFields(Set<DataField> dataFields) {
        this.rootToFields = dataFields;
    }

    public DataTreeRoot rootToFields(Set<DataField> dataFields) {
        this.setRootToFields(dataFields);
        return this;
    }

    public DataTreeRoot addRootToField(DataField dataField) {
        this.rootToFields.add(dataField);
        dataField.getDataTreeRoots().add(this);
        return this;
    }

    public DataTreeRoot removeRootToField(DataField dataField) {
        this.rootToFields.remove(dataField);
        dataField.getDataTreeRoots().remove(this);
        return this;
    }

    public Long getDataTreeBranchId() {
        return this.dataTreeBranchId;
    }

    public void setDataTreeBranchId(Long dataTreeBranch) {
        this.dataTreeBranchId = dataTreeBranch;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataTreeRoot)) {
            return false;
        }
        return id != null && id.equals(((DataTreeRoot) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataTreeRoot{" +
            "id=" + getId() +
            ", stereoType='" + getStereoType() + "'" +
            ", name='" + getName() + "'" +
            ", caption='" + getCaption() + "'" +
            ", documentation='" + getDocumentation() + "'" +
            "}";
    }
}
