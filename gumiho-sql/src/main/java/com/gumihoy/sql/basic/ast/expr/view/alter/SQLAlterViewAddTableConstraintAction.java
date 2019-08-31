package com.gumihoy.sql.basic.ast.expr.view.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.ISQLTableConstraint;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ADD out_of_line_constraint
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-VIEW.html#GUID-0DEDE960-B481-4B55-8027-EA9E4C863625
 * @author kent on 2018/7/13.
 */
public class SQLAlterViewAddTableConstraintAction extends AbstractSQLExpr implements ISQLAlterViewAction {

    protected ISQLTableConstraint constraint;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, constraint);
//        }
    }

    @Override
    public SQLAlterViewAddTableConstraintAction clone() {
        SQLAlterViewAddTableConstraintAction x = new SQLAlterViewAddTableConstraintAction();
        ISQLTableConstraint constraintClone = this.constraint.clone();
        x.setConstraint(constraintClone);
        return x;
    }

    public ISQLTableConstraint getConstraint() {
        return constraint;
    }

    public void setConstraint(ISQLTableConstraint constraint) {
        setChildParent(constraint);
        this.constraint = constraint;
    }
}
