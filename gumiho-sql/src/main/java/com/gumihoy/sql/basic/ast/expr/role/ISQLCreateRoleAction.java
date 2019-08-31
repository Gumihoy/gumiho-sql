package com.gumihoy.sql.basic.ast.expr.role;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2019-08-01.
 */
public interface ISQLCreateRoleAction extends ISQLExpr {
    @Override
    ISQLCreateRoleAction clone();
}
