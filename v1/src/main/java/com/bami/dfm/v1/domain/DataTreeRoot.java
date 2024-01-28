package com.bami.dfm.v1.domain;

import com.bami.dfm.v1.domain.enumeration.StereoTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DataTreeRoot.
 */
@Entity
@Table(name = "data_tree_root")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataTreeRoot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * stereoType
     */
    @Schema(description = "stereoType")
    @Enumerated(EnumType.STRING)
    @Column(name = "stereo_type")
    private StereoTypeEnum stereoType;

    @Column(name = "name")
    private String name;

    @Column(name = "caption")
    private String caption;

    @Column(name = "documentation")
    private String documentation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "dataFields", "dataTreeBranches", "dataTreeRoots", "dataTreeBranches" },
        allowSetters = true
    )
    private DataTreeBranch dataTreeBranch;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_data_tree_root__data_field",
        joinColumns = @JoinColumn(name = "data_tree_root_id"),
        inverseJoinColumns = @JoinColumn(name = "data_field_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dataTreeRoots", "dataTreeBranches", "dataTreeLeaves" }, allowSetters = true)
    private Set<DataField> dataFields = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dataTreeRoot")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dataTreeRoot" }, allowSetters = true)
    private Set<DataForest> dataForests = new HashSet<>();

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
    }

    public DataTreeRoot dataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.setDataTreeBranch(dataTreeBranch);
        return this;
    }

    public Set<DataField> getDataFields() {
        return this.dataFields;
    }

    public void setDataFields(Set<DataField> dataFields) {
        this.dataFields = dataFields;
    }

    public DataTreeRoot dataFields(Set<DataField> dataFields) {
        this.setDataFields(dataFields);
        return this;
    }

    public DataTreeRoot addDataField(DataField dataField) {
        this.dataFields.add(dataField);
        dataField.getDataTreeRoots().add(this);
        return this;
    }

    public DataTreeRoot removeDataField(DataField dataField) {
        this.dataFields.remove(dataField);
        dataField.getDataTreeRoots().remove(this);
        return this;
    }

    public Set<DataForest> getDataForests() {
        return this.dataForests;
    }

    public void setDataForests(Set<DataForest> dataForests) {
        if (this.dataForests != null) {
            this.dataForests.forEach(i -> i.setDataTreeRoot(null));
        }
        if (dataForests != null) {
            dataForests.forEach(i -> i.setDataTreeRoot(this));
        }
        this.dataForests = dataForests;
    }

    public DataTreeRoot dataForests(Set<DataForest> dataForests) {
        this.setDataForests(dataForests);
        return this;
    }

    public DataTreeRoot addDataForest(DataForest dataForest) {
        this.dataForests.add(dataForest);
        dataForest.setDataTreeRoot(this);
        return this;
    }

    public DataTreeRoot removeDataForest(DataForest dataForest) {
        this.dataForests.remove(dataForest);
        dataForest.setDataTreeRoot(null);
        return this;
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
