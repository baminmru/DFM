package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataTreeBranch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DataTreeBranch entity.
 *
 * When extending this class, extend DataTreeBranchRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DataTreeBranchRepository extends DataTreeBranchRepositoryWithBagRelationships, JpaRepository<DataTreeBranch, Long> {
    default Optional<DataTreeBranch> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<DataTreeBranch> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<DataTreeBranch> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
