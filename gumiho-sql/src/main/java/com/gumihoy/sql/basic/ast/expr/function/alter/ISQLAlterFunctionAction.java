package com.gumihoy.sql.basic.ast.expr.function.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.procedure.alter.ISQLAlterProcedureAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * ALTER PROCEDURE [ schema. ] procedure_name
 *   { procedure_compile_clause | { EDITIONABLE | NONEDITIONABLE } } ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-PROCEDURE-statement.html#GUID-17EAA51A-308B-4DA3-856C-DA5C4480AAA9
 *
 * @author kent on 2019-07-30.
 */
public interface ISQLAlterFunctionAction extends ISQLExpr {
    @Override
    ISQLAlterFunctionAction clone();


    public static class SQLAlterFunctionEditionAbleAction extends AbstractSQLExpr implements ISQLAlterFunctionAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLAlterFunctionEditionAbleAction clone() {
            return new SQLAlterFunctionEditionAbleAction();
        }
    }

    public static class SQLAlterFunctionNonEditionAbleAction extends AbstractSQLExpr implements ISQLAlterFunctionAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLAlterFunctionNonEditionAbleAction clone() {
            return new SQLAlterFunctionNonEditionAbleAction();
        }
    }
}
