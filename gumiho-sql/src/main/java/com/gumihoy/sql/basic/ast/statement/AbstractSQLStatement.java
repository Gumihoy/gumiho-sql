package com.gumihoy.sql.basic.ast.statement;

import com.gumihoy.sql.basic.ast.AbstractSQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractSQLStatement extends AbstractSQLObject implements ISQLStatement {

    public AbstractSQLStatement(DBType dbType) {
        this.dbType = dbType;
    }

    @Override
    public ISQLStatement clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public SQLObjectType getObjectType() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }
}
