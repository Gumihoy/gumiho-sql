package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * RESULT_CACHE [ RELIES_ON ( [ data_source [, data_source]... ] ) ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/RESULT_CACHE-clause.html#GUID-7B0FFFDF-C953-46E5-9FD6-C41DFBDE1B0B
 *
 * @author kent on 2019-07-25.
 */
public interface ISQLResultCacheClause extends ISQLExpr {
    @Override
    ISQLResultCacheClause clone();

    class SQLResultCacheClause extends AbstractSQLExpr implements ISQLResultCacheClause {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLResultCacheClause clone() {
            return null;
        }
    }

    /**
     * RESULT_CACHE RELIES_ON ( [ data_source [, data_source]... ] )
     */
    class SQLResultCacheReliesOnClause extends AbstractSQLExpr implements ISQLResultCacheClause {

        protected final List<ISQLExpr> items = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLResultCacheClause clone() {
            return null;
        }

        public List<ISQLExpr> getItems() {
            return items;
        }

        public void addItem(ISQLExpr item) {
            if (item == null) {
                return;
            }
            setChildParent(item);
            this.items.add(item);
        }
    }

}
