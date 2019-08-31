package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2019-07-26.
 */
public interface ISQLTypeDefinition extends ISQLExpr {

    @Override
    ISQLTypeDefinition clone();

}
