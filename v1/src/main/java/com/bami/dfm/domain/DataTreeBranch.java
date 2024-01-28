package com.bami.dfm.domain;

import com.bami.dfm.domain.enumeration.StereoTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Thy DataTreeBranch entity.
 */
@Schema(description = "Thy DataTreeBranch entity.")
@Table("data_tree_branch")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataTreeBranch implements Serializable {

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
    @JsonIgnoreProperties(value = { "leafToField", "dataTreeBranches" }, allowSetters = true)
    private DataTreeLeaf dataTreeLeaf;

    @Transient
    @JsonIgnoreProperties(value = { "dataField" }, allowSetters = true)
    private DataTreeBranchToField branchToField;

    @Transient
    @JsonIgnoreProperties(value = { "dataTreeBranch" }, allowSetters = true)
    private DataTreeBranchLink branchParent;

    @Transient
    @JsonIgnoreProperties(value = { "dataTreeBranch", "rootToField" }, allowSetters = true)
    private Set<DataTreeRoot> dataTreeRoots = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "dataTreeBranch" }, allowSetters = true)
    private Set<DataTreeBranchLink> branchChildren = new HashSet<>();

    @Column("data_tree_leaf_id")
    private Long dataTreeLeafId;

    @Column("branch_to_field_id")
    private Long branchToFieldId;

    @Column("branch_parent_id")
    private Long branchParentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DataTreeBranch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StereoTypeEnum getStereoType() {
        return this.stereoType;
    }

    public DataTreeBranch stereoType(StereoTypeEnum stereoType) {
        this.setStereoType(stereoType);
        return this;
    }

    public void setStereoType(StereoTypeEnum stereoType) {
        this.stereoType = stereoType;
    }

    public String getName() {
        return this.name;
    }

    public DataTreeBranch name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return this.caption;
    }

    public DataTreeBranch caption(String caption) {
        this.setCaption(caption);
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public DataTreeBranch documentation(String documentation) {
        this.setDocumentation(documentation);
        return this;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public DataTreeLeaf getDataTreeLeaf() {
        return this.dataTreeLeaf;
    }

    public void setDataTreeLeaf(DataTreeLeaf dataTreeLeaf) {
        this.dataTreeLeaf = dataTreeLeaf;
        this.dataTreeLeafId = dataTreeLeaf != null ? dataTreeLeaf.getId() : null;
    }

    public DataTreeBranch dataTreeLeaf(DataTreeLeaf dataTreeLeaf) {
        this.setDataTreeLeaf(dataTreeLeaf);
        return this;
    }

    public DataTreeBranchToField getBranchToField() {
        return this.branchToField;
    }

    public void setBranchToField(DataTreeBranchToField dataTreeBranchToField) {
        this.branchToField = dataTreeBranchToField;
        this.branchToFieldId = dataTreeBranchToField != null ? dataTreeBranchToField.getId() : null;
    }

    public DataTreeBranch branchToField(DataTreeBranchToField dataTreeBranchToField) {
        this.setBranchToField(dataTreeBranchToField);
        return this;
    }

    public DataTreeBranchLink getBranchParent() {
        return this.branchParent;
    }

    public void setBranchParent(DataTreeBranchLink dataTreeBranchLink) {
        this.branchParent = dataTreeBranchLink;
        this.branchParentId = dataTreeBranchLink != null ? dataTreeBranchLink.getId() : null;
    }

    public DataTreeBranch branchParent(DataTreeBranchLink dataTreeBranchLink) {
        this.setBranchParent(dataTreeBranchLink);
        return this;
    }

    public Set<DataTreeRoot> getDataTreeRoots() {
        return this.dataTreeRoots;
    }

    public void setDataTreeRoots(Set<DataTreeRoot> dataTreeRoots) {
        if (this.dataTreeRoots != null) {
            this.dataTreeRoots.forEach(i -> i.setDataTreeBranch(null));
        }
        if (dataTreeRoots != null) {
            dataTreeRoots.forEach(i -> i.setDataTreeBranch(this));
        }
        this.dataTreeRoots = dataTreeRoots;
    }

    public DataTreeBranch dataTreeRoots(Set<DataTreeRoot> dataTreeRoots) {
        this.setDataTreeRoots(dataTreeRoots);
        return this;
    }

    public DataTreeBranch addDataTreeRoot(DataTreeRoot dataTreeRoot) {
        this.dataTreeRoots.add(dataTreeRoot);
        dataTreeRoot.setDataTreeBranch(this);
        return this;
    }

    public DataTreeBranch removeDataTreeRoot(DataTreeRoot dataTreeRoot) {
        this.dataTreeRoots.remove(dataTreeRoot);
        dataTreeRoot.setDataTreeBranch(null);
        return this;
    }

    public Set<DataTreeBranchLink> getBranchChildren() {
        return this.branchChildren;
    }

    public void setBranchChildren(Set<DataTreeBranchLink> dataTreeBranchLinks) {
        if (this.branchChildren != null) {
            this.branchChildren.forEach(i -> i.setDataTreeBranch(null));
        }
        if (dataTreeBranchLinks != null) {
            dataTreeBranchLinks.forEach(i -> i.setDataTreeBranch(this));
        }
        this.branchChildren = dataTreeBranchLinks;
    }

    public DataTreeBranch branchChildren(Set<DataTreeBranchLink> dataTreeBranchLinks) {
        this.setBranchChildren(dataTreeBranchLinks);
        return this;
    }

    public DataTreeBranch addBranchChild(DataTreeBranchLink dataTreeBranchLink) {
        this.branchChildren.add(dataTreeBranchLink);
        dataTreeBranchLink.setDataTreeBranch(this);
        return this;
    }

    public DataTreeBranch removeBranchChild(DataTreeBranchLink dataTreeBranchLink) {
        this.branchChildren.remove(dataTreeBranchLink);
        dataTreeBranchLink.setDataTreeBranch(null);
        return this;
    }

    public Long getDataTreeLeafId() {
        return this.dataTreeLeafId;
    }

    public void setDataTreeLeafId(Long dataTreeLeaf) {
        this.dataTreeLeafId = dataTreeLeaf;
    }

    public Long getBranchToFieldId() {
        return this.branchToFieldId;
    }

    public void setBranchToFieldId(Long dataTreeBranchToField) {
        this.branchToFieldId = dataTreeBranchToField;
    }

    public Long getBranchParentId() {
        return this.branchParentId;
    }

    public void setBranchParentId(Long dataTreeBranchLink) {
        this.branchParentId = dataTreeBranchLink;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataTreeBranch)) {
            return false;
        }
        return id != null && id.equals(((DataTreeBranch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataTreeBranch{" +
            "id=" + getId() +
            ", stereoType='" + getStereoType() + "'" +
            ", name='" + getName() + "'" +
            ", caption='" + getCaption() + "'" +
            ", documentation='" + getDocumentation() + "'" +
            "}";
    }
}
