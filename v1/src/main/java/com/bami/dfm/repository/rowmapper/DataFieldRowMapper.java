package com.bami.dfm.repository.rowmapper;

import com.bami.dfm.domain.DataField;
import com.bami.dfm.domain.enumeration.FieldTypeEnum;
import com.bami.dfm.domain.enumeration.InputTypeEnum;
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
        entity.setSequence(converter.fromRow(row, prefix + "_sequence", Integer.class));
        entity.setIsBrief(converter.fromRow(row, prefix + "_is_brief", Boolean.class));
        entity.setBriefSequence(converter.fromRow(row, prefix + "_brief_sequence", Integer.class));
        entity.setAllowNull(converter.fromRow(row, prefix + "_allow_null", Boolean.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setCaption(converter.fromRow(row, prefix + "_caption", String.class));
        entity.setDocumentation(converter.fromRow(row, prefix + "_documentation", String.class));
        entity.setTabName(converter.fromRow(row, prefix + "_tab_name", String.class));
        entity.setGroupName(converter.fromRow(row, prefix + "_group_name", String.class));
        entity.setGenerationStyle(converter.fromRow(row, prefix + "_generation_style", String.class));
        entity.setRefToRootId(converter.fromRow(row, prefix + "_ref_to_root_id", Long.class));
        return entity;
    }
}
