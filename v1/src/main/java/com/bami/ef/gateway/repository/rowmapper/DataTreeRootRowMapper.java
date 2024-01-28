package com.bami.ef.gateway.repository.rowmapper;

import com.bami.ef.gateway.domain.DataTreeRoot;
import com.bami.ef.gateway.domain.enumeration.StereoTypeEnum;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link DataTreeRoot}, with proper type conversions.
 */
@Service
public class DataTreeRootRowMapper implements BiFunction<Row, String, DataTreeRoot> {

    private final ColumnConverter converter;

    public DataTreeRootRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link DataTreeRoot} stored in the database.
     */
    @Override
    public DataTreeRoot apply(Row row, String prefix) {
        DataTreeRoot entity = new DataTreeRoot();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStereoType(converter.fromRow(row, prefix + "_stereo_type", StereoTypeEnum.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setCaption(converter.fromRow(row, prefix + "_caption", String.class));
        entity.setDocumentation(converter.fromRow(row, prefix + "_documentation", String.class));
        entity.setDataTreeBranchId(converter.fromRow(row, prefix + "_data_tree_branch_id", Long.class));
        return entity;
    }
}
