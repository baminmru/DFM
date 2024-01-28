package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataTreeLeaf;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DataTreeLeaf entity.
 *
 * When extending this class, extend DataTreeLeafRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DataTreeLeafRepository extends DataTreeLeafRepositoryWithBagRelationships, JpaRepository<DataTreeLeaf, Long> {
    default Optional<DataTreeLeaf> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<DataTreeLeaf> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<DataTreeLeaf> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
