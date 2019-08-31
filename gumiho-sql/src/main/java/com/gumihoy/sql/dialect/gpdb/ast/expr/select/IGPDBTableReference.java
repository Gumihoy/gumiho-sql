package com.gumihoy.sql.dialect.gpdb.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.dialect.mysql.ast.expr.IMySQLExpr;

/**
 * @author kent on 2018/6/7.
 */
public interface IGPDBTableReference extends IMySQLExpr, ISQLTableReference {

    @Override
    IGPDBTableReference clone();
}
