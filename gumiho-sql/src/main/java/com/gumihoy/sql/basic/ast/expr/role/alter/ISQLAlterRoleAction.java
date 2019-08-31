package com.gumihoy.sql.basic.ast.expr.role.alter;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2019-08-01.
 */
public interface ISQLAlterRoleAction extends ISQLExpr {
    @Override
    ISQLAlterRoleAction clone();
}
