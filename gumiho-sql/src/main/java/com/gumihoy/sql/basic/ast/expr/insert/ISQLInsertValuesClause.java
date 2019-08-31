package com.gumihoy.sql.basic.ast.expr.insert;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2019-07-07.
 */
public interface ISQLInsertValuesClause extends ISQLExpr {

    @Override
    ISQLInsertValuesClause clone();
}
