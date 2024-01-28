package com.bami.dfm.repository.rowmapper;

import com.bami.dfm.domain.DataForest;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link DataForest}, with proper type conversions.
 */
@Service
public class DataForestRowMapper implements BiFunction<Row, String, DataForest> {

    private final ColumnConverter converter;

    public DataForestRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link DataForest} stored in the database.
     */
    @Override
    public DataForest apply(Row row, String prefix) {
        DataForest entity = new DataForest();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setCaption(converter.fromRow(row, prefix + "_caption", String.class));
        entity.setDocumentation(converter.fromRow(row, prefix + "_documentation", String.class));
        entity.setForestTreesId(converter.fromRow(row, prefix + "_forest_trees_id", Long.class));
        return entity;
    }
}
