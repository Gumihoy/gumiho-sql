package com.gumihoy.sql.dialect.postgresql.ast.element;

import com.gumihoy.sql.basic.ast.AbstractSQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractPostgreSQLExpr extends AbstractSQLObject implements ISQLExpr {

    public AbstractPostgreSQLExpr(DBType dbType) {
        this.dbType = dbType;
    }

    @Override
    public AbstractPostgreSQLExpr clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

}
