package com.gumihoy.sql.basic.ast.expr.index.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexRenameToAction extends AbstractSQLExpr implements ISQLAlterIndexAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    @Override
    public SQLAlterIndexRenameToAction clone() {
        return null;
    }
}
