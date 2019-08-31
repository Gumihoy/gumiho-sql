package com.gumihoy.sql.basic.ast.expr.view.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DROP CONSTRAINT constraint
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-VIEW.html#GUID-0DEDE960-B481-4B55-8027-EA9E4C863625
 * @author kent on 2018/7/13.
 */
public class SQLAlterViewDropConstraintAction extends AbstractSQLExpr implements ISQLAlterViewAction {

    protected ISQLName constraint;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, constraint);
//        }
    }

    @Override
    public SQLAlterViewDropConstraintAction clone() {
        SQLAlterViewDropConstraintAction x = new SQLAlterViewDropConstraintAction();
        ISQLName constraintClone = this.constraint.clone();
        x.setConstraint(constraintClone);
        return x;
    }

    public ISQLName getConstraint() {
        return constraint;
    }

    public void setConstraint(ISQLName constraint) {
        setChildParent(constraint);
        this.constraint = constraint;
    }
}
