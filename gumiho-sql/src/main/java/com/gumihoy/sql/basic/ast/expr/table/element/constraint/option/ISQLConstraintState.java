package com.gumihoy.sql.basic.ast.expr.table.element.constraint.option;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * https://docs.oracle.com/cd/B28359_01/server.111/b28286/clauses002.htm#CJAFFBAA
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 *
 * @author kent on 2018/6/26.
 */
public interface ISQLConstraintState extends ISQLExpr {
    @Override
    ISQLConstraintState clone();
}
