package com.bami.dfm.domain;

import com.bami.dfm.domain.enumeration.StereoTypeEnum;
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
    @JsonIgnoreProperties(value = { "leafToFields", "dataTreeBranches" }, allowSetters = true)
    private DataTreeLeaf dataTreeLeaf;

    @Transient
    @JsonIgnoreProperties(value = { "dataTreeRoots", "dataTreeBranches", "dataTreeLeaves" }, allowSetters = true)
    private Set<DataField> branchToFields = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "branchToFields", "branchParents", "dataTreeRoots", "branchChildren" },
        allowSetters = true
    )
    private Set<DataTreeBranch> branchParents = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "dataTreeBranch", "rootToFields" }, allowSetters = true)
    private Set<DataTreeRoot> dataTreeRoots = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "branchToFields", "branchParents", "dataTreeRoots", "branchChildren" },
        allowSetters = true
    )
    private Set<DataTreeBranch> branchChildren = new HashSet<>();

    @Column("data_tree_leaf_id")
    private Long dataTreeLeafId;

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

    public Set<DataField> getBranchToFields() {
        return this.branchToFields;
    }

    public void setBranchToFields(Set<DataField> dataFields) {
        this.branchToFields = dataFields;
    }

    public DataTreeBranch branchToFields(Set<DataField> dataFields) {
        this.setBranchToFields(dataFields);
        return this;
    }

    public DataTreeBranch addBranchToField(DataField dataField) {
        this.branchToFields.add(dataField);
        dataField.getDataTreeBranches().add(this);
        return this;
    }

    public DataTreeBranch removeBranchToField(DataField dataField) {
        this.branchToFields.remove(dataField);
        dataField.getDataTreeBranches().remove(this);
        return this;
    }

    public Set<DataTreeBranch> getBranchParents() {
        return this.branchParents;
    }

    public void setBranchParents(Set<DataTreeBranch> dataTreeBranches) {
        this.branchParents = dataTreeBranches;
    }

    public DataTreeBranch branchParents(Set<DataTreeBranch> dataTreeBranches) {
        this.setBranchParents(dataTreeBranches);
        return this;
    }

    public DataTreeBranch addBranchParent(DataTreeBranch dataTreeBranch) {
        this.branchParents.add(dataTreeBranch);
        dataTreeBranch.getBranchChildren().add(this);
        return this;
    }

    public DataTreeBranch removeBranchParent(DataTreeBranch dataTreeBranch) {
        this.branchParents.remove(dataTreeBranch);
        dataTreeBranch.getBranchChildren().remove(this);
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

    public Set<DataTreeBranch> getBranchChildren() {
        return this.branchChildren;
    }

    public void setBranchChildren(Set<DataTreeBranch> dataTreeBranches) {
        if (this.branchChildren != null) {
            this.branchChildren.forEach(i -> i.removeBranchParent(this));
        }
        if (dataTreeBranches != null) {
            dataTreeBranches.forEach(i -> i.addBranchParent(this));
        }
        this.branchChildren = dataTreeBranches;
    }

    public DataTreeBranch branchChildren(Set<DataTreeBranch> dataTreeBranches) {
        this.setBranchChildren(dataTreeBranches);
        return this;
    }

    public DataTreeBranch addBranchChild(DataTreeBranch dataTreeBranch) {
        this.branchChildren.add(dataTreeBranch);
        dataTreeBranch.getBranchParents().add(this);
        return this;
    }

    public DataTreeBranch removeBranchChild(DataTreeBranch dataTreeBranch) {
        this.branchChildren.remove(dataTreeBranch);
        dataTreeBranch.getBranchParents().remove(this);
        return this;
    }

    public Long getDataTreeLeafId() {
        return this.dataTreeLeafId;
    }

    public void setDataTreeLeafId(Long dataTreeLeaf) {
        this.dataTreeLeafId = dataTreeLeaf;
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
