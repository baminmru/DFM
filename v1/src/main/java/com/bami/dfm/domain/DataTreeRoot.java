package com.bami.dfm.domain;

import com.bami.dfm.domain.enumeration.StereoTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
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
    @Schema(description = "stereoType", required = true)
    @NotNull(message = "must not be null")
    @Column("stereo_type")
    private StereoTypeEnum stereoType;

    @NotNull(message = "must not be null")
    @Column("name")
    private String name;

    @NotNull(message = "must not be null")
    @Column("caption")
    private String caption;

    @Column("documentation")
    private String documentation;

    @Transient
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "branchToField", "branchParent", "dataTreeRoots", "branchChildren" },
        allowSetters = true
    )
    private DataTreeBranch dataTreeBranch;

    @Transient
    @JsonIgnoreProperties(value = { "dataField" }, allowSetters = true)
    private DataTreeRootToField rootToField;

    @Column("data_tree_branch_id")
    private Long dataTreeBranchId;

    @Column("root_to_field_id")
    private Long rootToFieldId;

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

    public DataTreeRootToField getRootToField() {
        return this.rootToField;
    }

    public void setRootToField(DataTreeRootToField dataTreeRootToField) {
        this.rootToField = dataTreeRootToField;
        this.rootToFieldId = dataTreeRootToField != null ? dataTreeRootToField.getId() : null;
    }

    public DataTreeRoot rootToField(DataTreeRootToField dataTreeRootToField) {
        this.setRootToField(dataTreeRootToField);
        return this;
    }

    public Long getDataTreeBranchId() {
        return this.dataTreeBranchId;
    }

    public void setDataTreeBranchId(Long dataTreeBranch) {
        this.dataTreeBranchId = dataTreeBranch;
    }

    public Long getRootToFieldId() {
        return this.rootToFieldId;
    }

    public void setRootToFieldId(Long dataTreeRootToField) {
        this.rootToFieldId = dataTreeRootToField;
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
