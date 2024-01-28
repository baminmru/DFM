package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataTreeRoot;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DataTreeRoot entity.
 *
 * When extending this class, extend DataTreeRootRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DataTreeRootRepository extends DataTreeRootRepositoryWithBagRelationships, JpaRepository<DataTreeRoot, Long> {
    default Optional<DataTreeRoot> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<DataTreeRoot> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<DataTreeRoot> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
