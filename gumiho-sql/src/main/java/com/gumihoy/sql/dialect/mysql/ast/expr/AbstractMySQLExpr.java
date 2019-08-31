package com.gumihoy.sql.dialect.mysql.ast.expr;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.mysql.visitor.IMySQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * @author kent on 2019-06-14.
 */
public abstract class AbstractMySQLExpr extends AbstractSQLExpr implements IMySQLExpr {

    public AbstractMySQLExpr() {
        this.dbType = DBType.MySQL;
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IMySQLASTVisitor) {
            accept0((IMySQLASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public abstract void accept0(IMySQLASTVisitor visitor);

    @Override
    public AbstractMySQLExpr clone() {
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
