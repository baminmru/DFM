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
 * Thy DataTreeBranch entity.
 */
@Schema(description = "Thy DataTreeBranch entity.")
@Entity
@Table(name = "data_tree_branch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataTreeBranch implements Serializable {

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
    @JsonIgnoreProperties(value = { "dataFields", "dataTreeBranches" }, allowSetters = true)
    private DataTreeLeaf dataTreeLeaf;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_data_tree_branch__data_field",
        joinColumns = @JoinColumn(name = "data_tree_branch_id"),
        inverseJoinColumns = @JoinColumn(name = "data_field_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dataTreeRoots", "dataTreeBranches", "dataTreeLeaves" }, allowSetters = true)
    private Set<DataField> dataFields = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_data_tree_branch__data_tree_branch",
        joinColumns = @JoinColumn(name = "data_tree_branch_id"),
        inverseJoinColumns = @JoinColumn(name = "data_tree_branch_id")
    )
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dataTreeBranches")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "dataFields", "dataTreeBranches", "dataTreeRoots", "dataTreeBranches" },
        allowSetters = true
    )
    private Set<DataTreeBranch> dataTreeBranches = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dataTreeBranch")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dataTreeBranch", "dataFields", "dataForests" }, allowSetters = true)
    private Set<DataTreeRoot> dataTreeRoots = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_data_tree_branch__data_tree_branch",
        joinColumns = @JoinColumn(name = "data_tree_branch_id"),
        inverseJoinColumns = @JoinColumn(name = "data_tree_branch_id")
    )
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dataTreeBranches")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "dataTreeLeaf", "dataFields", "dataTreeBranches", "dataTreeRoots", "dataTreeBranches" },
        allowSetters = true
    )
    private Set<DataTreeBranch> dataTreeBranches = new HashSet<>();

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
    }

    public DataTreeBranch dataTreeLeaf(DataTreeLeaf dataTreeLeaf) {
        this.setDataTreeLeaf(dataTreeLeaf);
        return this;
    }

    public Set<DataField> getDataFields() {
        return this.dataFields;
    }

    public void setDataFields(Set<DataField> dataFields) {
        this.dataFields = dataFields;
    }

    public DataTreeBranch dataFields(Set<DataField> dataFields) {
        this.setDataFields(dataFields);
        return this;
    }

    public DataTreeBranch addDataField(DataField dataField) {
        this.dataFields.add(dataField);
        dataField.getDataTreeBranches().add(this);
        return this;
    }

    public DataTreeBranch removeDataField(DataField dataField) {
        this.dataFields.remove(dataField);
        dataField.getDataTreeBranches().remove(this);
        return this;
    }

    public Set<DataTreeBranch> getDataTreeBranches() {
        return this.dataTreeBranches;
    }

    public void setDataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        this.dataTreeBranches = dataTreeBranches;
    }

    public DataTreeBranch dataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        this.setDataTreeBranches(dataTreeBranches);
        return this;
    }

    public DataTreeBranch addDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.add(dataTreeBranch);
        dataTreeBranch.getDataTreeBranches().add(this);
        return this;
    }

    public DataTreeBranch removeDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.remove(dataTreeBranch);
        dataTreeBranch.getDataTreeBranches().remove(this);
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

    public Set<DataTreeBranch> getDataTreeBranches() {
        return this.dataTreeBranches;
    }

    public void setDataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        if (this.dataTreeBranches != null) {
            this.dataTreeBranches.forEach(i -> i.removeDataTreeBranch(this));
        }
        if (dataTreeBranches != null) {
            dataTreeBranches.forEach(i -> i.addDataTreeBranch(this));
        }
        this.dataTreeBranches = dataTreeBranches;
    }

    public DataTreeBranch dataTreeBranches(Set<DataTreeBranch> dataTreeBranches) {
        this.setDataTreeBranches(dataTreeBranches);
        return this;
    }

    public DataTreeBranch addDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.add(dataTreeBranch);
        dataTreeBranch.getDataTreeBranches().add(this);
        return this;
    }

    public DataTreeBranch removeDataTreeBranch(DataTreeBranch dataTreeBranch) {
        this.dataTreeBranches.remove(dataTreeBranch);
        dataTreeBranch.getDataTreeBranches().remove(this);
        return this;
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
