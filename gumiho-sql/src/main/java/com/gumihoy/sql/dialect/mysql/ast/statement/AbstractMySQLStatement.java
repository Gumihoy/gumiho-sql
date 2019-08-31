package com.gumihoy.sql.dialect.mysql.ast.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.dialect.mysql.ast.AbstractMySQLObject;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractMySQLStatement extends AbstractMySQLObject implements IMySQLStatement {

    public AbstractMySQLStatement() {
    }

    @Override
    public IMySQLStatement clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        throw new UnsupportedOperationException(this.getClass().getName());
    }
}
