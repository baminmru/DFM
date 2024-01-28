package com.bami.dfm.repository.rowmapper;

import com.bami.dfm.domain.DataTreeLeaf;
import com.bami.dfm.domain.enumeration.StereoTypeEnum;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link DataTreeLeaf}, with proper type conversions.
 */
@Service
public class DataTreeLeafRowMapper implements BiFunction<Row, String, DataTreeLeaf> {

    private final ColumnConverter converter;

    public DataTreeLeafRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link DataTreeLeaf} stored in the database.
     */
    @Override
    public DataTreeLeaf apply(Row row, String prefix) {
        DataTreeLeaf entity = new DataTreeLeaf();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStereoType(converter.fromRow(row, prefix + "_stereo_type", StereoTypeEnum.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setCaption(converter.fromRow(row, prefix + "_caption", String.class));
        entity.setDocumentation(converter.fromRow(row, prefix + "_documentation", String.class));
        entity.setLeafToFieldId(converter.fromRow(row, prefix + "_leaf_to_field_id", Long.class));
        return entity;
    }
}
