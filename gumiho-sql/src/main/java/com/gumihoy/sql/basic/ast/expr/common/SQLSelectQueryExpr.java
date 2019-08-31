package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.insert.ISQLInsertValuesClause;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * select statement
 * <p>
 * (select statement)
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Creating-Simple-Queries.html#GUID-DB044D5C-A960-4813-84DA-A1880C913339
 *
 * @author kent on 2018/5/3.
 */
public class SQLSelectQueryExpr extends AbstractSQLExpr implements ISQLInsertValuesClause {

    protected boolean paren;
    protected ISQLSelectQuery subQuery;

    public SQLSelectQueryExpr() {
    }

    public SQLSelectQueryExpr(ISQLSelectQuery subQuery) {
        setSubQuery(subQuery);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, subQuery);
        }
    }

    @Override
    public SQLSelectQueryExpr clone() {
        SQLSelectQueryExpr x = new SQLSelectQueryExpr();

        ISQLSelectQuery subQueryClone = subQuery.clone();
        x.setSubQuery(subQueryClone);

        return x;
    }

    public boolean isParen() {
        return paren;
    }

    public void setParen(boolean paren) {
        this.paren = paren;
    }

    public ISQLSelectQuery getSubQuery() {
        return subQuery;
    }

    public void setSubQuery(ISQLSelectQuery subQuery) {
        setChildParent(subQuery);
        this.subQuery = subQuery;
    }
}
