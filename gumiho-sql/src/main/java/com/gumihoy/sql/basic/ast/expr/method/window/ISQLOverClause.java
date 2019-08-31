package com.gumihoy.sql.basic.ast.expr.method.window;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * OVER <window name or specification>
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#window%20function
 *
 * @author kent on 2018/5/22.
 */
public interface ISQLOverClause extends ISQLExpr {

    @Override
    ISQLOverClause clone();
}
