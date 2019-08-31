package com.gumihoy.sql.basic.ast.expr.sequence.alter;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2019-07-31.
 */
public interface ISQLAlterSequenceAction extends ISQLExpr {
    @Override
    ISQLAlterSequenceAction clone();
}
