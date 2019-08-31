package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * INVALIDATE GLOBAL INDEXES
 * <p>
 * update_global_index_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/12.
 */
public class SQLInvalidateGlobalIndexClause extends AbstractSQLExpr implements ISQLUpdateIndexClause {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
    }

    @Override
    public SQLInvalidateGlobalIndexClause clone() {
        return new SQLInvalidateGlobalIndexClause();
    }
}
