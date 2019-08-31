package com.gumihoy.sql.dialect.mariadb.ast.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.dialect.mariadb.ast.AbstractMariaDBObject;
import com.gumihoy.sql.dialect.mysql.ast.statement.IMySQLStatement;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractMariaDBStatement extends AbstractMariaDBObject implements IMariaDBStatement {

    public AbstractMariaDBStatement() {
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
