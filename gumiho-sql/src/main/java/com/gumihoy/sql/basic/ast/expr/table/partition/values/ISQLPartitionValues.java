package com.gumihoy.sql.basic.ast.expr.table.partition.values;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

import java.util.List;

/**
 * partition_definition 中的 VALUES
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * range_values_clause、list_values_clause
 * <p>
 * VALUES LESS THAN ( expr (COMMA expr)* )
 * VALUES ( expr (COMMA expr)* )
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/9.
 */
public interface ISQLPartitionValues extends ISQLExpr {

    List<ISQLExpr> getValues();

    void addValue(ISQLExpr value);

    @Override
    ISQLPartitionValues clone();
}
