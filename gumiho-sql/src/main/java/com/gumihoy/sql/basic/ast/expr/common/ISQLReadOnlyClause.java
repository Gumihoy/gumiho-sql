package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * { READ ONLY } | { READ WRITE }
 * <p>
 * read_only_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/read_only_clause.html
 *
 * @author kent on 2019-07-16.
 */
public interface ISQLReadOnlyClause extends ISQLPhysicalProperty {

    @Override
    ISQLReadOnlyClause clone();


     class SQLReadOnly extends AbstractSQLExpr implements ISQLReadOnlyClause {
         @Override
         protected void accept0(ISQLASTVisitor visitor) {

         }

         @Override
         public SQLReadOnly clone() {
             return new SQLReadOnly();
         }
     }

    class SQLReadWrite extends AbstractSQLExpr implements ISQLReadOnlyClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLReadWrite clone() {
            return new SQLReadWrite();
        }
    }
}
