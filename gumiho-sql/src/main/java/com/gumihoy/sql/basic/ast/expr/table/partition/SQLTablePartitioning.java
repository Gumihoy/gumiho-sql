package com.gumihoy.sql.basic.ast.expr.table.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * table_partitioning_clauses
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/4/9.
 */
public interface SQLTablePartitioning extends ISQLExpr {
    @Override
    SQLTablePartitioning clone();
}
