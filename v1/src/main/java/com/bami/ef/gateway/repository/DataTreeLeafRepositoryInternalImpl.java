package com.bami.ef.gateway.repository;


import com.bami.ef.gateway.domain.DataField;
import com.bami.ef.gateway.domain.DataTreeLeaf;
import com.bami.ef.gateway.repository.rowmapper.DataTreeLeafRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the DataTreeLeaf entity.
 */
@SuppressWarnings("unused")
class DataTreeLeafRepositoryInternalImpl extends SimpleR2dbcRepository<DataTreeLeaf, Long> implements DataTreeLeafRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final DataTreeLeafRowMapper datatreeleafMapper;

    private static final Table entityTable = Table.aliased("data_tree_leaf", EntityManager.ENTITY_ALIAS);

    private static final EntityManager.LinkTable leafToFieldLink = new EntityManager.LinkTable(
        "rel_data_tree_leaf__leaf_to_field",
        "data_tree_leaf_id",
        "leaf_to_field_id"
    );

    public DataTreeLeafRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        DataTreeLeafRowMapper datatreeleafMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(DataTreeLeaf.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.datatreeleafMapper = datatreeleafMapper;
    }

    @Override
    public Flux<DataTreeLeaf> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<DataTreeLeaf> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = DataTreeLeafSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, DataTreeLeaf.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<DataTreeLeaf> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<DataTreeLeaf> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<DataTreeLeaf> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<DataTreeLeaf> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<DataTreeLeaf> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private DataTreeLeaf process(Row row, RowMetadata metadata) {
        DataTreeLeaf entity = datatreeleafMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends DataTreeLeaf> Mono<S> save(S entity) {
        return super.save(entity).flatMap((S e) -> updateRelations(e));
    }

    protected <S extends DataTreeLeaf> Mono<S> updateRelations(S entity) {
        Mono<Void> result = entityManager
            .updateLinkTable(leafToFieldLink, entity.getId(), entity.getLeafToFields().stream().map(DataField::getId))
            .then();
        return result.thenReturn(entity);
    }

    @Override
    public Mono<Void> deleteById(Long entityId) {
        return deleteRelations(entityId).then(super.deleteById(entityId));
    }

    protected Mono<Void> deleteRelations(Long entityId) {
        return entityManager.deleteFromLinkTable(leafToFieldLink, entityId);
    }
}
