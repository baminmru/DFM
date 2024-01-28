package com.bami.dfm.repository;


import com.bami.dfm.domain.DataForest;
import com.bami.dfm.repository.rowmapper.DataForestRowMapper;
import com.bami.dfm.repository.rowmapper.DataTreeRootRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the DataForest entity.
 */
@SuppressWarnings("unused")
class DataForestRepositoryInternalImpl extends SimpleR2dbcRepository<DataForest, Long> implements DataForestRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final DataTreeRootRowMapper datatreerootMapper;
    private final DataForestRowMapper dataforestMapper;

    private static final Table entityTable = Table.aliased("data_forest", EntityManager.ENTITY_ALIAS);
    private static final Table forestTreesTable = Table.aliased("data_tree_root", "forestTrees");

    public DataForestRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        DataTreeRootRowMapper datatreerootMapper,
        DataForestRowMapper dataforestMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(DataForest.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.datatreerootMapper = datatreerootMapper;
        this.dataforestMapper = dataforestMapper;
    }

    @Override
    public Flux<DataForest> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<DataForest> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = DataForestSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(DataTreeRootSqlHelper.getColumns(forestTreesTable, "forestTrees"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(forestTreesTable)
            .on(Column.create("forest_trees_id", entityTable))
            .equals(Column.create("id", forestTreesTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, DataForest.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<DataForest> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<DataForest> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private DataForest process(Row row, RowMetadata metadata) {
        DataForest entity = dataforestMapper.apply(row, "e");
        entity.setForestTrees(datatreerootMapper.apply(row, "forestTrees"));
        return entity;
    }

    @Override
    public <S extends DataForest> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
