package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.method.aggreate.AbstractSQLAggregateFunction;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * aggregate_function KEEP (DENSE_RANK FIRST ORDER BY expr [ DESC | ASC ] [ NULLS { FIRST | LAST } ] [, expr [ DESC | ASC ] [ NULLS { FIRST | LAST } ] ]... )
 * [ OVER ( [query_partition_clause] ) ]
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/FIRST.html#GUID-85AB9246-0E0A-44A1-A7E6-4E57502E9238
 *
 * @author kent on 2018/5/22.
 */
public class SQLFirstFunction extends AbstractSQLAggregateFunction {

    protected SQLOrderByClause orderByClause;

    public SQLFirstFunction(ISQLExpr name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, nameExpr);
//            this.acceptChild(visitor, orderByClause);
//        }
    }

    public SQLOrderByClause getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(SQLOrderByClause orderByClause) {
        this.orderByClause = orderByClause;
    }
}
