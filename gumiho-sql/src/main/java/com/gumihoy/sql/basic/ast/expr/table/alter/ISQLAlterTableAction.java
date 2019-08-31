package com.gumihoy.sql.basic.ast.expr.table.alter;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#alter%20table%20statement
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public interface ISQLAlterTableAction extends ISQLExpr {
    @Override
    ISQLAlterTableAction clone();
}
