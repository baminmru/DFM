package com.bami.ef.gateway.repository;

import com.bami.ef.gateway.domain.DataForest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the DataForest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataForestRepository extends ReactiveCrudRepository<DataForest, Long>, DataForestRepositoryInternal {
    @Query("SELECT * FROM data_forest entity WHERE entity.forest_trees_id = :id")
    Flux<DataForest> findByForestTrees(Long id);

    @Query("SELECT * FROM data_forest entity WHERE entity.forest_trees_id IS NULL")
    Flux<DataForest> findAllWhereForestTreesIsNull();

    @Override
    <S extends DataForest> Mono<S> save(S entity);

    @Override
    Flux<DataForest> findAll();

    @Override
    Mono<DataForest> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DataForestRepositoryInternal {
    <S extends DataForest> Mono<S> save(S entity);

    Flux<DataForest> findAllBy(Pageable pageable);

    Flux<DataForest> findAll();

    Mono<DataForest> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<DataForest> findAllBy(Pageable pageable, Criteria criteria);
}
