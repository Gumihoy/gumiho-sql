package com.gumihoy.sql.basic.ast.expr.database.alter;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2019-07-19.
 */
public interface ISQLAlterDatabaseAction extends ISQLExpr {

    @Override
    ISQLAlterDatabaseAction clone();
}
