package com.gumihoy.sql.basic.ast.expr.index.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * ENABLE
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/alter_index.html
 *
 * @author kent on 2019-08-02.
 */
public class SQLAlterIndexEnableAction extends AbstractSQLExpr implements ISQLAlterIndexAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    @Override
    public SQLAlterIndexEnableAction clone() {
        return null;
    }
}
