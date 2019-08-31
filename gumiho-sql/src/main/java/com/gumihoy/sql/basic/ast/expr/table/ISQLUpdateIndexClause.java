package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * update_index_clauses
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/12.
 */
public interface ISQLUpdateIndexClause extends ISQLExpr {
    @Override
    ISQLUpdateIndexClause clone();
}
