package com.gumihoy.sql.basic.ast.expr.view.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *  MODIFY CONSTRAINT constraint  { RELY | NORELY }
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-VIEW.html#GUID-0DEDE960-B481-4B55-8027-EA9E4C863625
 * @author kent on 2018/7/13.
 */
public class SQLAlterViewModifyConstraintAction extends AbstractSQLExpr implements ISQLAlterViewAction {

    protected ISQLName constraint;
//    protected SQLOption option;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, constraint);
//        }
    }

    @Override
    public SQLAlterViewModifyConstraintAction clone() {
        SQLAlterViewModifyConstraintAction x = new SQLAlterViewModifyConstraintAction();
        ISQLName constraintClone = this.constraint.clone();
        x.setConstraint(constraintClone);

//        x.option = this.option;

        return x;
    }

    public ISQLName getConstraint() {
        return constraint;
    }

    public void setConstraint(ISQLName constraint) {
        setChildParent(constraint);
        this.constraint = constraint;
    }

//    public SQLOption getOption() {
//        return option;
//    }
//
//    public void setOption(SQLOption option) {
//        this.option = option;
//    }

//    public enum  SQLOption implements ISQLEnum {
//
//        RELY(SQLReserved.RELY),
//        NORELY(SQLReserved.NORELY);
//
//        public final SQLReserved name;
//
//        SQLOption(SQLReserved name) {
//            this.name = name;
//        }
//
//        @Override
//        public SQLReserved getName() {
//            return name;
//        }
//    }
}
