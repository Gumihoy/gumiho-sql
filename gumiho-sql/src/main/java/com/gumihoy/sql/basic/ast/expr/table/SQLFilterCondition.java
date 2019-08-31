package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLWhereClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * INCLUDING ROWS where_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/filter_condition.html
 *
 * @author kent on 2019-08-02.
 */
public class SQLFilterCondition extends AbstractSQLExpr  {

    protected SQLWhereClause whereClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    public SQLWhereClause getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(SQLWhereClause whereClause) {
        setChildParent(whereClause);
        this.whereClause = whereClause;
    }
}
