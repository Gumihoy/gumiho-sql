package com.gumihoy.sql.basic.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2018/7/24.
 */
public interface ISQLLockClause extends ISQLExpr {
    @Override
    ISQLLockClause clone();
}
