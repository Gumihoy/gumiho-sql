package com.gumihoy.sql.basic.ast.expr.literal;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#literal
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Literals.html
 * https://dev.mysql.com/doc/refman/8.0/en/literals.html
 *
 * @author kent on 2019-06-14.
 */
public interface ISQLLiteral extends ISQLExpr {

    Object getValue();

}
