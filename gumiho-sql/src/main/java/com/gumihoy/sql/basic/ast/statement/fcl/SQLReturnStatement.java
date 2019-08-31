package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * RETURN [ expression ] ;
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/RETURN-statement.html#GUID-2DCDD1F2-041A-479C-A2F8-B3B68F50FE5D
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLReturnStatement extends AbstractSQLStatement {

    protected ISQLExpr expr;


    public SQLReturnStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
        }
    }

    @Override
    public SQLReturnStatement clone() {
        SQLReturnStatement x = new SQLReturnStatement(this.dbType);

        if (expr != null) {
            ISQLExpr exprClone = this.expr.clone();
            x.setExpr(exprClone);
        }
        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == expr) {
            this.setExpr(target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.RETURN;
    }



    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }
}
