package com.bami.dfm.repository;

import com.bami.dfm.domain.DataTreeLeaf;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the DataTreeLeaf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataTreeLeafRepository extends ReactiveCrudRepository<DataTreeLeaf, Long>, DataTreeLeafRepositoryInternal {
    @Query("SELECT * FROM data_tree_leaf entity WHERE entity.leaf_to_field_id = :id")
    Flux<DataTreeLeaf> findByLeafToField(Long id);

    @Query("SELECT * FROM data_tree_leaf entity WHERE entity.leaf_to_field_id IS NULL")
    Flux<DataTreeLeaf> findAllWhereLeafToFieldIsNull();

    @Override
    <S extends DataTreeLeaf> Mono<S> save(S entity);

    @Override
    Flux<DataTreeLeaf> findAll();

    @Override
    Mono<DataTreeLeaf> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DataTreeLeafRepositoryInternal {
    <S extends DataTreeLeaf> Mono<S> save(S entity);

    Flux<DataTreeLeaf> findAllBy(Pageable pageable);

    Flux<DataTreeLeaf> findAll();

    Mono<DataTreeLeaf> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<DataTreeLeaf> findAllBy(Pageable pageable, Criteria criteria);
}
