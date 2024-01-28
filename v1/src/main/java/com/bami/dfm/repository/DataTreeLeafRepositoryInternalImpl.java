package com.bami.dfm.repository;


import com.bami.dfm.domain.DataTreeLeaf;
import com.bami.dfm.repository.rowmapper.DataTreeLeafRowMapper;
import com.bami.dfm.repository.rowmapper.DataTreeLeafToFieldRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
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

    private final DataTreeLeafToFieldRowMapper datatreeleaftofieldMapper;
    private final DataTreeLeafRowMapper datatreeleafMapper;

    private static final Table entityTable = Table.aliased("data_tree_leaf", EntityManager.ENTITY_ALIAS);
    private static final Table leafToFieldTable = Table.aliased("data_tree_leaf_to_field", "leafToField");

    public DataTreeLeafRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        DataTreeLeafToFieldRowMapper datatreeleaftofieldMapper,
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
        this.datatreeleaftofieldMapper = datatreeleaftofieldMapper;
        this.datatreeleafMapper = datatreeleafMapper;
    }

    @Override
    public Flux<DataTreeLeaf> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<DataTreeLeaf> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = DataTreeLeafSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(DataTreeLeafToFieldSqlHelper.getColumns(leafToFieldTable, "leafToField"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(leafToFieldTable)
            .on(Column.create("leaf_to_field_id", entityTable))
            .equals(Column.create("id", leafToFieldTable));
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

    private DataTreeLeaf process(Row row, RowMetadata metadata) {
        DataTreeLeaf entity = datatreeleafMapper.apply(row, "e");
        entity.setLeafToField(datatreeleaftofieldMapper.apply(row, "leafToField"));
        return entity;
    }

    @Override
    public <S extends DataTreeLeaf> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
