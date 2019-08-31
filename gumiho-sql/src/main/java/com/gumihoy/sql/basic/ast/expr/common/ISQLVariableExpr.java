package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2019-07-23.
 */
public interface ISQLVariableExpr extends ISQLExpr {
    @Override
    ISQLVariableExpr clone();
}
