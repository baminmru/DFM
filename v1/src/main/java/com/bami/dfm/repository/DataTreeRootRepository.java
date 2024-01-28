package com.bami.dfm.repository;

import com.bami.dfm.domain.DataTreeRoot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the DataTreeRoot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataTreeRootRepository extends ReactiveCrudRepository<DataTreeRoot, Long>, DataTreeRootRepositoryInternal {
    @Override
    Mono<DataTreeRoot> findOneWithEagerRelationships(Long id);

    @Override
    Flux<DataTreeRoot> findAllWithEagerRelationships();

    @Override
    Flux<DataTreeRoot> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM data_tree_root entity WHERE entity.data_tree_branch_id = :id")
    Flux<DataTreeRoot> findByDataTreeBranch(Long id);

    @Query("SELECT * FROM data_tree_root entity WHERE entity.data_tree_branch_id IS NULL")
    Flux<DataTreeRoot> findAllWhereDataTreeBranchIsNull();

    @Query(
        "SELECT entity.* FROM data_tree_root entity JOIN rel_data_tree_root__root_to_field joinTable ON entity.id = joinTable.root_to_field_id WHERE joinTable.root_to_field_id = :id"
    )
    Flux<DataTreeRoot> findByRootToField(Long id);

    @Override
    <S extends DataTreeRoot> Mono<S> save(S entity);

    @Override
    Flux<DataTreeRoot> findAll();

    @Override
    Mono<DataTreeRoot> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DataTreeRootRepositoryInternal {
    <S extends DataTreeRoot> Mono<S> save(S entity);

    Flux<DataTreeRoot> findAllBy(Pageable pageable);

    Flux<DataTreeRoot> findAll();

    Mono<DataTreeRoot> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<DataTreeRoot> findAllBy(Pageable pageable, Criteria criteria);

    Mono<DataTreeRoot> findOneWithEagerRelationships(Long id);

    Flux<DataTreeRoot> findAllWithEagerRelationships();

    Flux<DataTreeRoot> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
