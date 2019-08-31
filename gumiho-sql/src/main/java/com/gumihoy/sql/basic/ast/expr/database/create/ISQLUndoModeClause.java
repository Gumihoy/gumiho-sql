package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * LOCAL UNDO { ON | OFF }
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-DATABASE.html#GUID-ECE717DF-F116-4151-927C-2E51BB9DD39C
 *
 * @author kent on 2019-07-21.
 */
public interface ISQLUndoModeClause extends ISQLExpr {

    @Override
    ISQLUndoModeClause clone();


    class SQLUndoModeOnClause extends AbstractSQLExpr implements ISQLUndoModeClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLUndoModeOnClause clone() {
            return new SQLUndoModeOnClause();
        }
    }

    class SQLUndoModeOffClause extends AbstractSQLExpr implements ISQLUndoModeClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLUndoModeOffClause clone() {
            return new SQLUndoModeOffClause();
        }
    }



}
