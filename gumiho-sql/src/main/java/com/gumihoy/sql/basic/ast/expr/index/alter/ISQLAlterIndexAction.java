package com.gumihoy.sql.basic.ast.expr.index.alter;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2019-08-02.
 */
public interface ISQLAlterIndexAction extends ISQLExpr {
    @Override
    ISQLAlterIndexAction clone();
}
