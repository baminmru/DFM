package com.bami.ef.gateway.repository;

import com.bami.ef.gateway.domain.DataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the DataField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataFieldRepository extends ReactiveCrudRepository<DataField, Long>, DataFieldRepositoryInternal {
    @Override
    <S extends DataField> Mono<S> save(S entity);

    @Override
    Flux<DataField> findAll();

    @Override
    Mono<DataField> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DataFieldRepositoryInternal {
    <S extends DataField> Mono<S> save(S entity);

    Flux<DataField> findAllBy(Pageable pageable);

    Flux<DataField> findAll();

    Mono<DataField> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<DataField> findAllBy(Pageable pageable, Criteria criteria);
}
