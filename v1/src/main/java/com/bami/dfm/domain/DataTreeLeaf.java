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
 * A DataTreeLeaf.
 */
@Table("data_tree_leaf")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataTreeLeaf implements Serializable {

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
    @JsonIgnoreProperties(value = { "dataField" }, allowSetters = true)
    private DataTreeLeafToField leafToField;

    @Transient
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "branchToField", "branchParent", "dataTreeRoots", "branchChildren" },
        allowSetters = true
    )
    private Set<DataTreeBranch> dataTreeBranches = new HashSet<>();

    @Column("leaf_to_field_id")
    private Long leafToFieldId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DataTreeLeaf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StereoTypeEnum getStereoType() {
        return this.stereoType;
    }

    public DataTreeLeaf stereoType(StereoTypeEnum stereoType) {
        this.setStereoType(stereoType);
        return this;
    }

    public void setStereoType(StereoTypeEnum stereoType) {
        this.stereoType = stereoType;
    }

    public String getName() {
        return this.name;
    }

    public DataTreeLeaf name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return this.caption;
    }

    public DataTreeLeaf caption(String caption) {
        this.setCaption(caption);
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public DataTreeLeaf documentation(String documentation) {
        this.setDocumentation(documentation);
        return this;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public DataTreeLeafToField getLeafToField() {
        return this.leafToField;
    }

    public void setLeafToField(DataTreeLeafToField dataTreeLeafToField) {
        this.leafToField = dataTreeLeafToField;
        this.leafToFieldId = dataTreeLeafToField != null ? dataTreeLeafToField.getId() : null;
    }

    public DataTreeLeaf leafToField(DataTreeLeafToField dataTreeLeafToField) {
        this.setLeafToField(dataTreeLeafToField);
        return this;
    }

    public Set<DataTreeBranch> getDataTreeBranches() {
        return this.dataTreeBranches;
    }

    public void setDataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        if (this.dataTreeBranches != null) {
            this.dataTreeBranches.forEach(i -> i.setDataTreeLeaf(null));
        }
        if (dataTreeBranches != null) {
            dataTreeBranches.forEach(i -> i.setDataTreeLeaf(this));
        }
        this.dataTreeBranches = dataTreeBranches;
    }

    public DataTreeLeaf dataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        this.setDataTreeBranches(dataTreeBranches);
        return this;
    }

    public DataTreeLeaf addDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.add(dataTreeBranch);
        dataTreeBranch.setDataTreeLeaf(this);
        return this;
    }

    public DataTreeLeaf removeDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.remove(dataTreeBranch);
        dataTreeBranch.setDataTreeLeaf(null);
        return this;
    }

    public Long getLeafToFieldId() {
        return this.leafToFieldId;
    }

    public void setLeafToFieldId(Long dataTreeLeafToField) {
        this.leafToFieldId = dataTreeLeafToField;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataTreeLeaf)) {
            return false;
        }
        return id != null && id.equals(((DataTreeLeaf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataTreeLeaf{" +
            "id=" + getId() +
            ", stereoType='" + getStereoType() + "'" +
            ", name='" + getName() + "'" +
            ", caption='" + getCaption() + "'" +
            ", documentation='" + getDocumentation() + "'" +
            "}";
    }
}
