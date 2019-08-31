package com.gumihoy.sql.basic.ast.expr.index.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * DISABLE
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/alter_index.html
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexDisableAction extends AbstractSQLExpr implements ISQLAlterIndexAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    @Override
    public SQLAlterIndexDisableAction clone() {
        return null;
    }
}
