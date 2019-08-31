package com.gumihoy.sql.basic.ast.expr.procedure.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * ALTER PROCEDURE [ schema. ] procedure_name
 *   { procedure_compile_clause | { EDITIONABLE | NONEDITIONABLE } } ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-PROCEDURE-statement.html#GUID-17EAA51A-308B-4DA3-856C-DA5C4480AAA9
 *
 * @author kent on 2019-07-30.
 */
public interface ISQLAlterProcedureAction extends ISQLExpr {
    @Override
    ISQLAlterProcedureAction clone();


    public static class SQLAlterProcedureEditionAbleAction extends AbstractSQLExpr implements ISQLAlterProcedureAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLAlterProcedureEditionAbleAction clone() {
            return null;
        }
    }

    public static class SQLAlterProcedureNonEditionAbleAction extends AbstractSQLExpr implements ISQLAlterProcedureAction {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLAlterProcedureNonEditionAbleAction clone() {
            return null;
        }
    }
}
