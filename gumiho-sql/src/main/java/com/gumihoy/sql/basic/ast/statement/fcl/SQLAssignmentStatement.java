package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * assignment_statement_target := expression ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/assignment-statement.html#GUID-4C3BEFDF-3FFA-4E9D-96D0-4C5E13E08643
 *
 * @author kent on 2018/6/9.
 */
public class SQLAssignmentStatement extends AbstractSQLStatement {

    protected ISQLExpr target;
    protected ISQLExpr expr;

    public SQLAssignmentStatement(DBType dbType) {
        super(dbType);
    }

    public SQLAssignmentStatement(ISQLExpr target, ISQLExpr expr, DBType dbType) {
        super(dbType);
        setTarget(target);
        setExpr(expr);
        setAfterSemi(true);
    }

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, target);
            this.acceptChild(visitor, expr);
        }
    }

    @Override
    public SQLAssignmentStatement clone() {
        SQLAssignmentStatement x = new SQLAssignmentStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAssignmentStatement x) {
        super.cloneTo(x);

        ISQLExpr targetClone = this.target.clone();
        x.setTarget(targetClone);

        ISQLExpr exprClone = this.expr.clone();
        x.setExpr(exprClone);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == this.target) {
            this.setTarget(target);
            return true;
        }

        if (source == this.expr) {
            this.setExpr(target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;
    }

    public ISQLExpr getTarget() {
        return target;
    }

    public void setTarget(ISQLExpr target) {
        setChildParent(target);
        this.target = target;
    }

    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }


    
}
