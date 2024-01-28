package com.bami.dfm.repository.rowmapper;

import com.bami.dfm.domain.DataTreeBranch;
import com.bami.dfm.domain.enumeration.StereoTypeEnum;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link DataTreeBranch}, with proper type conversions.
 */
@Service
public class DataTreeBranchRowMapper implements BiFunction<Row, String, DataTreeBranch> {

    private final ColumnConverter converter;

    public DataTreeBranchRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link DataTreeBranch} stored in the database.
     */
    @Override
    public DataTreeBranch apply(Row row, String prefix) {
        DataTreeBranch entity = new DataTreeBranch();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStereoType(converter.fromRow(row, prefix + "_stereo_type", StereoTypeEnum.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setCaption(converter.fromRow(row, prefix + "_caption", String.class));
        entity.setDocumentation(converter.fromRow(row, prefix + "_documentation", String.class));
        entity.setDataTreeLeafId(converter.fromRow(row, prefix + "_data_tree_leaf_id", Long.class));
        return entity;
    }
}
