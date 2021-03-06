package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DROP PARTITION name [,name]
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 * <p>
 * <p>
 * DROP (PARTITION | PARTITIONS) dropPartitionActionItem (COMMA dropPartitionActionItem)*
 * nameIdentifier | FOR LEFT_PAREN names+=nameIdentifier (COMMA names+=nameIdentifier)
 * drop_table_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableDropPartitionsForAction extends AbstractSQLAlterTableDropPartitionAction implements ISQLAlterTablePartitionAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//            this.acceptChild(visitor, updateIndex);
//            this.acceptChild(visitor, parallel);
//        }
    }

    @Override
    public SQLAlterTableDropPartitionsForAction clone() {
        SQLAlterTableDropPartitionsForAction x = new SQLAlterTableDropPartitionsForAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableDropPartitionsForAction x) {
        super.cloneTo(x);
    }

}
