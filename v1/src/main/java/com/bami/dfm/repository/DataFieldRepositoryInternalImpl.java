package com.bami.dfm.repository;


import com.bami.dfm.domain.DataField;
import com.bami.dfm.repository.rowmapper.DataFieldRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the DataField entity.
 */
@SuppressWarnings("unused")
class DataFieldRepositoryInternalImpl extends SimpleR2dbcRepository<DataField, Long> implements DataFieldRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final DataTreeRootRowMapper datatreerootMapper;
    private final DataFieldRowMapper datafieldMapper;

    private static final Table entityTable = Table.aliased("data_field", EntityManager.ENTITY_ALIAS);
    private static final Table refToRootTable = Table.aliased("data_tree_root", "refToRoot");

    public DataFieldRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        DataTreeRootRowMapper datatreerootMapper,
        DataFieldRowMapper datafieldMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(DataField.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.datatreerootMapper = datatreerootMapper;
        this.datafieldMapper = datafieldMapper;
    }

    @Override
    public Flux<DataField> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<DataField> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = DataFieldSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(DataTreeRootSqlHelper.getColumns(refToRootTable, "refToRoot"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(refToRootTable)
            .on(Column.create("ref_to_root_id", entityTable))
            .equals(Column.create("id", refToRootTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, DataField.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<DataField> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<DataField> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private DataField process(Row row, RowMetadata metadata) {
        DataField entity = datafieldMapper.apply(row, "e");
        entity.setRefToRoot(datatreerootMapper.apply(row, "refToRoot"));
        return entity;
    }

    @Override
    public <S extends DataField> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
