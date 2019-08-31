package com.gumihoy.sql.basic.ast.expr;

import com.gumihoy.sql.basic.ast.AbstractSQLObject;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractSQLExpr extends AbstractSQLObject implements ISQLExpr {

    public AbstractSQLExpr() {
    }

    public AbstractSQLExpr(DBType dbType) {
        this.dbType = dbType;
    }

    @Override
    public AbstractSQLExpr clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }
}
