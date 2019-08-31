package com.gumihoy.sql.basic.ast.expr.view.alter;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2018/7/13.
 */
public interface ISQLAlterViewAction extends ISQLExpr {
    @Override
    ISQLAlterViewAction clone();
}
