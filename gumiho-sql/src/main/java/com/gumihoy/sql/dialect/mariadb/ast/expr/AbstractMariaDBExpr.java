package com.gumihoy.sql.dialect.mariadb.ast.expr;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.mariadb.visitor.IMariaDBASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractMariaDBExpr extends AbstractSQLExpr implements IMariaDBExpr {

    public AbstractMariaDBExpr() {
        this.dbType = DBType.MySQL;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IMariaDBASTVisitor) {
            accept0((IMariaDBASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public abstract void accept0(IMariaDBASTVisitor visitor);

    @Override
    public AbstractMariaDBExpr clone() {
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
