package com.bami.dfm.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class DataTreeBranchSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("stereo_type", table, columnPrefix + "_stereo_type"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("caption", table, columnPrefix + "_caption"));
        columns.add(Column.aliased("documentation", table, columnPrefix + "_documentation"));

        columns.add(Column.aliased("data_tree_leaf_id", table, columnPrefix + "_data_tree_leaf_id"));
        return columns;
    }
}
