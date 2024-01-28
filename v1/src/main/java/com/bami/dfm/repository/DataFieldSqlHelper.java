package com.bami.dfm.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class DataFieldSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("input_type", table, columnPrefix + "_input_type"));
        columns.add(Column.aliased("field_type", table, columnPrefix + "_field_type"));
        columns.add(Column.aliased("sequence", table, columnPrefix + "_sequence"));
        columns.add(Column.aliased("is_brief", table, columnPrefix + "_is_brief"));
        columns.add(Column.aliased("brief_sequence", table, columnPrefix + "_brief_sequence"));
        columns.add(Column.aliased("allow_null", table, columnPrefix + "_allow_null"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("caption", table, columnPrefix + "_caption"));
        columns.add(Column.aliased("documentation", table, columnPrefix + "_documentation"));
        columns.add(Column.aliased("tab_name", table, columnPrefix + "_tab_name"));
        columns.add(Column.aliased("group_name", table, columnPrefix + "_group_name"));
        columns.add(Column.aliased("generation_style", table, columnPrefix + "_generation_style"));

        columns.add(Column.aliased("ref_to_root_id", table, columnPrefix + "_ref_to_root_id"));
        return columns;
    }
}
