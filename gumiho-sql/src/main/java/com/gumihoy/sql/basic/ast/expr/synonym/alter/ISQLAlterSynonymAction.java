package com.gumihoy.sql.basic.ast.expr.synonym.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * @author kent on 2019-07-31.
 */
public interface ISQLAlterSynonymAction extends ISQLExpr {
    @Override
    ISQLAlterSynonymAction clone();


    public static class SQLAlterSynonymEditionAbleAction extends AbstractSQLExpr implements ISQLAlterSynonymAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterSynonymEditionAbleAction clone() {
            return new SQLAlterSynonymEditionAbleAction();
        }
    }

    public static class SQLAlterSynonymNonEditionAbleAction extends AbstractSQLExpr implements ISQLAlterSynonymAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterSynonymNonEditionAbleAction clone() {
            return new SQLAlterSynonymNonEditionAbleAction();
        }
    }

    public static class SQLAlterSynonymCompileAction extends AbstractSQLExpr implements ISQLAlterSynonymAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterSynonymCompileAction clone() {
            return new SQLAlterSynonymCompileAction();
        }
    }
}
