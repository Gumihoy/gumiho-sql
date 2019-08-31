package com.gumihoy.sql.basic.ast.expr;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.ISQLReplaceable;

/**
 * @author kent on 2019-06-14.
 */
public interface ISQLExpr extends ISQLObject, ISQLReplaceable {

    @Override
    ISQLExpr clone();

}
