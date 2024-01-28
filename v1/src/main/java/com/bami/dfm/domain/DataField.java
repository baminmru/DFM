package com.bami.dfm.domain;

import com.bami.dfm.domain.enumeration.FieldTypeEnum;
import com.bami.dfm.domain.enumeration.InputTypeEnum;
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

    @NotNull(message = "must not be null")
    @Column("input_type")
    private InputTypeEnum inputType;

    @NotNull(message = "must not be null")
    @Column("field_type")
    private FieldTypeEnum fieldType;

    @NotNull(message = "must not be null")
    @Column("sequence")
    private Integer sequence;

    @NotNull(message = "must not be null")
    @Column("is_brief")
    private Boolean isBrief;

    @Column("brief_sequence")
    private Integer briefSequence;

    @NotNull(message = "must not be null")
    @Column("allow_null")
    private Boolean allowNull;

    @NotNull(message = "must not be null")
    @Column("name")
    private String name;

    @NotNull(message = "must not be null")
    @Column("caption")
    private String caption;

    @Column("documentation")
    private String documentation;

    @Column("tab_name")
    private String tabName;

    @Column("group_name")
    private String groupName;

    @Column("generation_style")
    private String generationStyle;

    @Transient
    @JsonIgnoreProperties(value = { "dataTreeBranch", "rootToField" }, allowSetters = true)
    private DataTreeRoot refToRoot;

    @Transient
    @JsonIgnoreProperties(value = { "dataField" }, allowSetters = true)
    private Set<DataTreeBranchToField> dataTreeBranchToFields = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "dataField" }, allowSetters = true)
    private Set<DataTreeLeafToField> dataTreeLeafToFields = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "dataField" }, allowSetters = true)
    private Set<DataTreeRootToField> dataTreeRootToFields = new HashSet<>();

    @Column("ref_to_root_id")
    private Long refToRootId;

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

    public Integer getSequence() {
        return this.sequence;
    }

    public DataField sequence(Integer sequence) {
        this.setSequence(sequence);
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean getIsBrief() {
        return this.isBrief;
    }

    public DataField isBrief(Boolean isBrief) {
        this.setIsBrief(isBrief);
        return this;
    }

    public void setIsBrief(Boolean isBrief) {
        this.isBrief = isBrief;
    }

    public Integer getBriefSequence() {
        return this.briefSequence;
    }

    public DataField briefSequence(Integer briefSequence) {
        this.setBriefSequence(briefSequence);
        return this;
    }

    public void setBriefSequence(Integer briefSequence) {
        this.briefSequence = briefSequence;
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

    public String getTabName() {
        return this.tabName;
    }

    public DataField tabName(String tabName) {
        this.setTabName(tabName);
        return this;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public DataField groupName(String groupName) {
        this.setGroupName(groupName);
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGenerationStyle() {
        return this.generationStyle;
    }

    public DataField generationStyle(String generationStyle) {
        this.setGenerationStyle(generationStyle);
        return this;
    }

    public void setGenerationStyle(String generationStyle) {
        this.generationStyle = generationStyle;
    }

    public DataTreeRoot getRefToRoot() {
        return this.refToRoot;
    }

    public void setRefToRoot(DataTreeRoot dataTreeRoot) {
        this.refToRoot = dataTreeRoot;
        this.refToRootId = dataTreeRoot != null ? dataTreeRoot.getId() : null;
    }

    public DataField refToRoot(DataTreeRoot dataTreeRoot) {
        this.setRefToRoot(dataTreeRoot);
        return this;
    }

    public Set<DataTreeBranchToField> getDataTreeBranchToFields() {
        return this.dataTreeBranchToFields;
    }

    public void setDataTreeBranchToFields(Set<DataTreeBranchToField> dataTreeBranchToFields) {
        if (this.dataTreeBranchToFields != null) {
            this.dataTreeBranchToFields.forEach(i -> i.setDataField(null));
        }
        if (dataTreeBranchToFields != null) {
            dataTreeBranchToFields.forEach(i -> i.setDataField(this));
        }
        this.dataTreeBranchToFields = dataTreeBranchToFields;
    }

    public DataField dataTreeBranchToFields(Set<DataTreeBranchToField> dataTreeBranchToFields) {
        this.setDataTreeBranchToFields(dataTreeBranchToFields);
        return this;
    }

    public DataField addDataTreeBranchToField(DataTreeBranchToField dataTreeBranchToField) {
        this.dataTreeBranchToFields.add(dataTreeBranchToField);
        dataTreeBranchToField.setDataField(this);
        return this;
    }

    public DataField removeDataTreeBranchToField(DataTreeBranchToField dataTreeBranchToField) {
        this.dataTreeBranchToFields.remove(dataTreeBranchToField);
        dataTreeBranchToField.setDataField(null);
        return this;
    }

    public Set<DataTreeLeafToField> getDataTreeLeafToFields() {
        return this.dataTreeLeafToFields;
    }

    public void setDataTreeLeafToFields(Set<DataTreeLeafToField> dataTreeLeafToFields) {
        if (this.dataTreeLeafToFields != null) {
            this.dataTreeLeafToFields.forEach(i -> i.setDataField(null));
        }
        if (dataTreeLeafToFields != null) {
            dataTreeLeafToFields.forEach(i -> i.setDataField(this));
        }
        this.dataTreeLeafToFields = dataTreeLeafToFields;
    }

    public DataField dataTreeLeafToFields(Set<DataTreeLeafToField> dataTreeLeafToFields) {
        this.setDataTreeLeafToFields(dataTreeLeafToFields);
        return this;
    }

    public DataField addDataTreeLeafToField(DataTreeLeafToField dataTreeLeafToField) {
        this.dataTreeLeafToFields.add(dataTreeLeafToField);
        dataTreeLeafToField.setDataField(this);
        return this;
    }

    public DataField removeDataTreeLeafToField(DataTreeLeafToField dataTreeLeafToField) {
        this.dataTreeLeafToFields.remove(dataTreeLeafToField);
        dataTreeLeafToField.setDataField(null);
        return this;
    }

    public Set<DataTreeRootToField> getDataTreeRootToFields() {
        return this.dataTreeRootToFields;
    }

    public void setDataTreeRootToFields(Set<DataTreeRootToField> dataTreeRootToFields) {
        if (this.dataTreeRootToFields != null) {
            this.dataTreeRootToFields.forEach(i -> i.setDataField(null));
        }
        if (dataTreeRootToFields != null) {
            dataTreeRootToFields.forEach(i -> i.setDataField(this));
        }
        this.dataTreeRootToFields = dataTreeRootToFields;
    }

    public DataField dataTreeRootToFields(Set<DataTreeRootToField> dataTreeRootToFields) {
        this.setDataTreeRootToFields(dataTreeRootToFields);
        return this;
    }

    public DataField addDataTreeRootToField(DataTreeRootToField dataTreeRootToField) {
        this.dataTreeRootToFields.add(dataTreeRootToField);
        dataTreeRootToField.setDataField(this);
        return this;
    }

    public DataField removeDataTreeRootToField(DataTreeRootToField dataTreeRootToField) {
        this.dataTreeRootToFields.remove(dataTreeRootToField);
        dataTreeRootToField.setDataField(null);
        return this;
    }

    public Long getRefToRootId() {
        return this.refToRootId;
    }

    public void setRefToRootId(Long dataTreeRoot) {
        this.refToRootId = dataTreeRoot;
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
            ", sequence=" + getSequence() +
            ", isBrief='" + getIsBrief() + "'" +
            ", briefSequence=" + getBriefSequence() +
            ", allowNull='" + getAllowNull() + "'" +
            ", name='" + getName() + "'" +
            ", caption='" + getCaption() + "'" +
            ", documentation='" + getDocumentation() + "'" +
            ", tabName='" + getTabName() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", generationStyle='" + getGenerationStyle() + "'" +
            "}";
    }
}
