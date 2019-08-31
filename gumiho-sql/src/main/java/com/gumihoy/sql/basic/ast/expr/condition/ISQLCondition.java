package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2018/5/11.
 */
public interface ISQLCondition extends ISQLExpr {

    @Override
    ISQLCondition clone();
}
