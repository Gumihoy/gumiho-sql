package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableMergeSubPartitionsToAction extends AbstractSQLAlterTableMergeSubPartitionsAction implements ISQLAlterTableAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//            this.acceptChild(visitor, subPartition);
//            this.acceptChild(visitor, filterCondition);
//            this.acceptChild(visitor, dependentTables);
//            this.acceptChild(visitor, updateIndex);
//            this.acceptChild(visitor, parallel);
//        }
    }

    @Override
    public SQLAlterTableMergeSubPartitionsToAction clone() {
        SQLAlterTableMergeSubPartitionsToAction x = new SQLAlterTableMergeSubPartitionsToAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableMergeSubPartitionsToAction x) {
        super.cloneTo(x);

    }
}
