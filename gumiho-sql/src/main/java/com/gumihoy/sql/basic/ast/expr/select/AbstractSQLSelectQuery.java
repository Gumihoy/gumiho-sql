package com.gumihoy.sql.basic.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.select.limit.ISQLLimitClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent on 2018/5/8.
 */
public abstract class AbstractSQLSelectQuery extends AbstractSQLExpr implements ISQLSelectQuery {

    protected SQLOrderByClause orderByClause;

    protected ISQLLimitClause limitClause;

    protected ISQLLockClause lockClause;

    @Override
    public AbstractSQLSelectQuery clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }


    public void cloneTo(AbstractSQLSelectQuery x) {
        super.cloneTo(x);
        SQLOrderByClause orderByClauseClone = orderByClause.clone();
        x.setOrderByClause(orderByClauseClone);

        ISQLLimitClause limitClauseClone = limitClause.clone();
        x.setLimitClause(limitClauseClone);


    }


    public SQLOrderByClause getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(SQLOrderByClause orderByClause) {
        setChildParent(orderByClause);
        this.orderByClause = orderByClause;
    }

    public ISQLLimitClause getLimitClause() {
        return limitClause;
    }

    public void setLimitClause(ISQLLimitClause limitClause) {
        setChildParent(limitClause);
        this.limitClause = limitClause;
    }

    @Override
    public ISQLLockClause getLockClause() {
        return lockClause;
    }

    @Override
    public void setLockClause(ISQLLockClause lockClause) {
        setChildParent(lockClause);
        this.lockClause = lockClause;
    }
}
