package com.bami.ef.gateway.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A DataForest.
 */
@Table("data_forest")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataForest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    /**
     * name
     */
    @Schema(description = "name")
    @Column("name")
    private String name;

    @Column("caption")
    private String caption;

    @Column("documentation")
    private String documentation;

    @Transient
    @JsonIgnoreProperties(value = { "dataTreeBranch", "rootToFields" }, allowSetters = true)
    private DataTreeRoot forestTrees;

    @Column("forest_trees_id")
    private Long forestTreesId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DataForest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DataForest name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return this.caption;
    }

    public DataForest caption(String caption) {
        this.setCaption(caption);
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public DataForest documentation(String documentation) {
        this.setDocumentation(documentation);
        return this;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public DataTreeRoot getForestTrees() {
        return this.forestTrees;
    }

    public void setForestTrees(DataTreeRoot dataTreeRoot) {
        this.forestTrees = dataTreeRoot;
        this.forestTreesId = dataTreeRoot != null ? dataTreeRoot.getId() : null;
    }

    public DataForest forestTrees(DataTreeRoot dataTreeRoot) {
        this.setForestTrees(dataTreeRoot);
        return this;
    }

    public Long getForestTreesId() {
        return this.forestTreesId;
    }

    public void setForestTreesId(Long dataTreeRoot) {
        this.forestTreesId = dataTreeRoot;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataForest)) {
            return false;
        }
        return id != null && id.equals(((DataForest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataForest{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", caption='" + getCaption() + "'" +
            ", documentation='" + getDocumentation() + "'" +
            "}";
    }
}
