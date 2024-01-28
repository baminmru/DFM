package com.bami.dfm.repository;

import com.bami.dfm.domain.DataTreeBranch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the DataTreeBranch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataTreeBranchRepository extends ReactiveCrudRepository<DataTreeBranch, Long>, DataTreeBranchRepositoryInternal {
    @Override
    Mono<DataTreeBranch> findOneWithEagerRelationships(Long id);

    @Override
    Flux<DataTreeBranch> findAllWithEagerRelationships();

    @Override
    Flux<DataTreeBranch> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM data_tree_branch entity WHERE entity.data_tree_leaf_id = :id")
    Flux<DataTreeBranch> findByDataTreeLeaf(Long id);

    @Query("SELECT * FROM data_tree_branch entity WHERE entity.data_tree_leaf_id IS NULL")
    Flux<DataTreeBranch> findAllWhereDataTreeLeafIsNull();

    @Query(
        "SELECT entity.* FROM data_tree_branch entity JOIN rel_data_tree_branch__branch_to_field joinTable ON entity.id = joinTable.branch_to_field_id WHERE joinTable.branch_to_field_id = :id"
    )
    Flux<DataTreeBranch> findByBranchToField(Long id);

    @Query(
        "SELECT entity.* FROM data_tree_branch entity JOIN rel_data_tree_branch__branch_parent joinTable ON entity.id = joinTable.branch_parent_id WHERE joinTable.branch_parent_id = :id"
    )
    Flux<DataTreeBranch> findByBranchParent(Long id);

    @Override
    <S extends DataTreeBranch> Mono<S> save(S entity);

    @Override
    Flux<DataTreeBranch> findAll();

    @Override
    Mono<DataTreeBranch> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DataTreeBranchRepositoryInternal {
    <S extends DataTreeBranch> Mono<S> save(S entity);

    Flux<DataTreeBranch> findAllBy(Pageable pageable);

    Flux<DataTreeBranch> findAll();

    Mono<DataTreeBranch> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<DataTreeBranch> findAllBy(Pageable pageable, Criteria criteria);

    Mono<DataTreeBranch> findOneWithEagerRelationships(Long id);

    Flux<DataTreeBranch> findAllWithEagerRelationships();

    Flux<DataTreeBranch> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
