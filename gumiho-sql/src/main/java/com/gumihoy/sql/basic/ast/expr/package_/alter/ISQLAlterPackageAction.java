package com.gumihoy.sql.basic.ast.expr.package_.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * @author kent on 2019-07-27.
 */
public interface ISQLAlterPackageAction extends ISQLExpr {
    @Override
    ISQLAlterPackageAction clone();


    class SQLAlterPackageEditionAbleAction extends AbstractSQLExpr implements ISQLAlterPackageAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterPackageEditionAbleAction clone() {
            return null;
        }
    }

    class SQLAlterPackageNoneEditionAbleAction extends AbstractSQLExpr implements ISQLAlterPackageAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLAlterPackageNoneEditionAbleAction clone() {
            return null;
        }
    }
}
