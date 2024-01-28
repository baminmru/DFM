package com.bami.dfm.repository;


import com.bami.dfm.domain.DataField;
import com.bami.dfm.domain.DataTreeRoot;
import com.bami.dfm.repository.rowmapper.DataTreeBranchRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the DataTreeRoot entity.
 */
@SuppressWarnings("unused")
class DataTreeRootRepositoryInternalImpl extends SimpleR2dbcRepository<DataTreeRoot, Long> implements DataTreeRootRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final DataTreeBranchRowMapper datatreebranchMapper;
    private final DataTreeRootRowMapper datatreerootMapper;

    private static final Table entityTable = Table.aliased("data_tree_root", EntityManager.ENTITY_ALIAS);
    private static final Table dataTreeBranchTable = Table.aliased("data_tree_branch", "dataTreeBranch");

    private static final EntityManager.LinkTable rootToFieldLink = new EntityManager.LinkTable(
        "rel_data_tree_root__root_to_field",
        "data_tree_root_id",
        "root_to_field_id"
    );

    public DataTreeRootRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        DataTreeBranchRowMapper datatreebranchMapper,
        DataTreeRootRowMapper datatreerootMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(DataTreeRoot.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.datatreebranchMapper = datatreebranchMapper;
        this.datatreerootMapper = datatreerootMapper;
    }

    @Override
    public Flux<DataTreeRoot> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<DataTreeRoot> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = DataTreeRootSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(DataTreeBranchSqlHelper.getColumns(dataTreeBranchTable, "dataTreeBranch"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(dataTreeBranchTable)
            .on(Column.create("data_tree_branch_id", entityTable))
            .equals(Column.create("id", dataTreeBranchTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, DataTreeRoot.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<DataTreeRoot> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<DataTreeRoot> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<DataTreeRoot> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<DataTreeRoot> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<DataTreeRoot> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private DataTreeRoot process(Row row, RowMetadata metadata) {
        DataTreeRoot entity = datatreerootMapper.apply(row, "e");
        entity.setDataTreeBranch(datatreebranchMapper.apply(row, "dataTreeBranch"));
        return entity;
    }

    @Override
    public <S extends DataTreeRoot> Mono<S> save(S entity) {
        return super.save(entity).flatMap((S e) -> updateRelations(e));
    }

    protected <S extends DataTreeRoot> Mono<S> updateRelations(S entity) {
        Mono<Void> result = entityManager
            .updateLinkTable(rootToFieldLink, entity.getId(), entity.getRootToFields().stream().map(DataField::getId))
            .then();
        return result.thenReturn(entity);
    }

    @Override
    public Mono<Void> deleteById(Long entityId) {
        return deleteRelations(entityId).then(super.deleteById(entityId));
    }

    protected Mono<Void> deleteRelations(Long entityId) {
        return entityManager.deleteFromLinkTable(rootToFieldLink, entityId);
    }
}
