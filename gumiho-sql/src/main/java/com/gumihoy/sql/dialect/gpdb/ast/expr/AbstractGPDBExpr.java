package com.gumihoy.sql.dialect.gpdb.ast.expr;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.gpdb.visitor.IGPDBASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractGPDBExpr extends AbstractSQLExpr implements IGPDBExpr {

    public AbstractGPDBExpr() {
        this.dbType = DBType.GPDB;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IGPDBASTVisitor) {
            accept0((IGPDBASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public abstract void accept0(IGPDBASTVisitor visitor);

    @Override
    public AbstractGPDBExpr clone() {
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
