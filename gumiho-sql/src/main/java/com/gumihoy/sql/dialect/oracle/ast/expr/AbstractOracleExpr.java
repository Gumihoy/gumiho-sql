package com.gumihoy.sql.dialect.oracle.ast.expr;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;
import com.gumihoy.sql.enums.DBType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractOracleExpr extends AbstractSQLExpr implements IOracleExpr {

    public AbstractOracleExpr() {
        this.dbType = DBType.Oracle;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IOracleASTVisitor) {
            accept0((IOracleASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public abstract void accept0(IOracleASTVisitor visitor);

    @Override
    public AbstractOracleExpr clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

}
