package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * INDEXING { ON | OFF }
 *
 * indexing_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/indexing_clause.html
 *
 * @author kent on 2019-07-16.
 */
public interface ISQLIndexingClause extends ISQLPhysicalProperty {

    @Override
    ISQLIndexingClause clone();


    class SQLIndexingOn extends AbstractSQLExpr implements ISQLIndexingClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLIndexingOn clone() {
            return new SQLIndexingOn();
        }
    }

    class SQLIndexingOff extends AbstractSQLExpr implements ISQLIndexingClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLIndexingOff clone() {
            return new SQLIndexingOff();
        }
    }

}
