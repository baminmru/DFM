package com.bami.ef.gateway.repository.rowmapper;

import com.bami.ef.gateway.domain.DataField;
import com.bami.ef.gateway.domain.enumeration.FieldTypeEnum;
import com.bami.ef.gateway.domain.enumeration.InputTypeEnum;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link DataField}, with proper type conversions.
 */
@Service
public class DataFieldRowMapper implements BiFunction<Row, String, DataField> {

    private final ColumnConverter converter;

    public DataFieldRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link DataField} stored in the database.
     */
    @Override
    public DataField apply(Row row, String prefix) {
        DataField entity = new DataField();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setInputType(converter.fromRow(row, prefix + "_input_type", InputTypeEnum.class));
        entity.setFieldType(converter.fromRow(row, prefix + "_field_type", FieldTypeEnum.class));
        entity.setReferenceRoot(converter.fromRow(row, prefix + "_reference_root", String.class));
        entity.setAllowNull(converter.fromRow(row, prefix + "_allow_null", Boolean.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setCaption(converter.fromRow(row, prefix + "_caption", String.class));
        entity.setDocumentation(converter.fromRow(row, prefix + "_documentation", String.class));
        return entity;
    }
}
