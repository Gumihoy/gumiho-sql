package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/11.
 */
public abstract class AbstractSQLAlterTablePartitionAction extends AbstractSQLExpr implements ISQLAlterTablePartitionAction {

//    protected SQLInvalidationType invalidation;

    @Override
    public AbstractSQLAlterTablePartitionAction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

//    public void cloneTo(AbstractSQLAlterTablePartitionAction x) {
//        super.cloneTo(x);
//        x.invalidation = this.invalidation;
//    }
//
//    public SQLInvalidationType getInvalidation() {
//        return invalidation;
//    }
//
//    public void setInvalidation(SQLInvalidationType invalidation) {
//        this.invalidation = invalidation;
//    }
}
