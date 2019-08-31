package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * RAISE [ exception ] ;
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/RAISE-statement.html#GUID-5F58843F-84C8-4768-A7B3-0E318948A88B
 *
 * @author kent on 2018/6/14.
 */
public class SQLRaiseStatement extends AbstractSQLStatement {

    protected ISQLExpr exception;

    public SQLRaiseStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, exception);
        }
    }

    @Override
    public SQLRaiseStatement clone() {
        SQLRaiseStatement x =new SQLRaiseStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLRaiseStatement x) {
        super.cloneTo(x);

        if (this.exception != null) {
            ISQLExpr exceptionClone = this.exception.clone();
            x.setException(exceptionClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == exception) {
            setException(target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.RAISE;
    }

    public ISQLExpr getException() {
        return exception;
    }

    public void setException(ISQLExpr exception) {
        setChildParent(exception);
        this.exception = exception;
    }
}
