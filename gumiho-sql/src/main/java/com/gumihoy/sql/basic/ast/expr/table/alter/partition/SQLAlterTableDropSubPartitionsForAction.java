package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DROP PARTITION partition_names
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 * <p>
 * <p>
 * <p>
 * drop_table_subpartition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableDropSubPartitionsForAction extends AbstractSQLAlterTableDropSubPartitionAction implements ISQLAlterTableAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//        }
    }

    @Override
    public SQLAlterTableDropSubPartitionsForAction clone() {
        SQLAlterTableDropSubPartitionsForAction x = new SQLAlterTableDropSubPartitionsForAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableDropSubPartitionsForAction x) {
        super.cloneTo(x);

    }

}
