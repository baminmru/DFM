package com.bami.dfm.repository;


import com.bami.dfm.domain.DataTreeBranch;
import com.bami.dfm.repository.rowmapper.DataTreeBranchLinkRowMapper;
import com.bami.dfm.repository.rowmapper.DataTreeBranchRowMapper;
import com.bami.dfm.repository.rowmapper.DataTreeBranchToFieldRowMapper;
import com.bami.dfm.repository.rowmapper.DataTreeLeafRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the DataTreeBranch entity.
 */
@SuppressWarnings("unused")
class DataTreeBranchRepositoryInternalImpl extends SimpleR2dbcRepository<DataTreeBranch, Long> implements DataTreeBranchRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final DataTreeLeafRowMapper datatreeleafMapper;
    private final DataTreeBranchToFieldRowMapper datatreebranchtofieldMapper;
    private final DataTreeBranchLinkRowMapper datatreebranchlinkMapper;
    private final DataTreeBranchRowMapper datatreebranchMapper;

    private static final Table entityTable = Table.aliased("data_tree_branch", EntityManager.ENTITY_ALIAS);
    private static final Table dataTreeLeafTable = Table.aliased("data_tree_leaf", "dataTreeLeaf");
    private static final Table branchToFieldTable = Table.aliased("data_tree_branch_to_field", "branchToField");
    private static final Table branchParentTable = Table.aliased("data_tree_branch_link", "branchParent");

    public DataTreeBranchRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        DataTreeLeafRowMapper datatreeleafMapper,
        DataTreeBranchToFieldRowMapper datatreebranchtofieldMapper,
        DataTreeBranchLinkRowMapper datatreebranchlinkMapper,
        DataTreeBranchRowMapper datatreebranchMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(DataTreeBranch.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.datatreeleafMapper = datatreeleafMapper;
        this.datatreebranchtofieldMapper = datatreebranchtofieldMapper;
        this.datatreebranchlinkMapper = datatreebranchlinkMapper;
        this.datatreebranchMapper = datatreebranchMapper;
    }

    @Override
    public Flux<DataTreeBranch> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<DataTreeBranch> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = DataTreeBranchSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(DataTreeLeafSqlHelper.getColumns(dataTreeLeafTable, "dataTreeLeaf"));
        columns.addAll(DataTreeBranchToFieldSqlHelper.getColumns(branchToFieldTable, "branchToField"));
        columns.addAll(DataTreeBranchLinkSqlHelper.getColumns(branchParentTable, "branchParent"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(dataTreeLeafTable)
            .on(Column.create("data_tree_leaf_id", entityTable))
            .equals(Column.create("id", dataTreeLeafTable))
            .leftOuterJoin(branchToFieldTable)
            .on(Column.create("branch_to_field_id", entityTable))
            .equals(Column.create("id", branchToFieldTable))
            .leftOuterJoin(branchParentTable)
            .on(Column.create("branch_parent_id", entityTable))
            .equals(Column.create("id", branchParentTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, DataTreeBranch.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<DataTreeBranch> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<DataTreeBranch> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private DataTreeBranch process(Row row, RowMetadata metadata) {
        DataTreeBranch entity = datatreebranchMapper.apply(row, "e");
        entity.setDataTreeLeaf(datatreeleafMapper.apply(row, "dataTreeLeaf"));
        entity.setBranchToField(datatreebranchtofieldMapper.apply(row, "branchToField"));
        entity.setBranchParent(datatreebranchlinkMapper.apply(row, "branchParent"));
        return entity;
    }

    @Override
    public <S extends DataTreeBranch> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
