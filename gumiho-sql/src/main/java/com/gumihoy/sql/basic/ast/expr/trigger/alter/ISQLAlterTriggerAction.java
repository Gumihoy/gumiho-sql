package com.gumihoy.sql.basic.ast.expr.trigger.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ALTER TRIGGER [ schema. ] trigger_name
 * { trigger_compile_clause
 * | { ENABLE | DISABLE }
 * | RENAME TO new_name
 * | { EDITIONABLE | NONEDITIONABLE }
 * } ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-TRIGGER-statement.html#GUID-BC319647-2D94-46D1-BF69-16CDFB507725
 *
 * @author kent on 2019-07-29.
 */
public interface ISQLAlterTriggerAction extends ISQLExpr {
    @Override
    ISQLAlterTriggerAction clone();

    public static class SQLAlterTriggerEnableAction extends AbstractSQLExpr implements ISQLAlterTriggerAction {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTriggerEnableAction clone() {
            return new SQLAlterTriggerEnableAction();
        }
    }

    public static class SQLAlterTriggerDisableAction extends AbstractSQLExpr implements ISQLAlterTriggerAction {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTriggerDisableAction clone() {
            return new SQLAlterTriggerDisableAction();
        }
    }


    public static class SQLAlterTriggerRenameToAction extends AbstractSQLExpr implements ISQLAlterTriggerAction {

        protected ISQLName name;

        public SQLAlterTriggerRenameToAction(ISQLName name) {
            this.name = name;
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
            }
        }

        @Override
        public SQLAlterTriggerRenameToAction clone() {
            ISQLName nameClone = this.name.clone();
            return new SQLAlterTriggerRenameToAction(nameClone);
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            this.name = name;
        }
    }

    public static class SQLAlterTriggerEditionAbleAction extends AbstractSQLExpr implements ISQLAlterTriggerAction {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTriggerEditionAbleAction clone() {
            return new SQLAlterTriggerEditionAbleAction();
        }
    }

    public static class SQLAlterTriggerNonEditionAbleAction extends AbstractSQLExpr implements ISQLAlterTriggerAction {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterTriggerNonEditionAbleAction clone() {
            return new SQLAlterTriggerNonEditionAbleAction();
        }
    }

}
