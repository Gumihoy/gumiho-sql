package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 *
 * TRUNCATE PARTITION {partition_names | ALL}
 * https://dev.mysql.com/doc/refman/5.6/en/alter-table.html
 *
 * truncate_partition_subpart
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableTruncatePartitionsAction extends AbstractSQLAlterTableTruncatePartitionAction implements ISQLAlterTableAction {

//    protected SQLPartitionType type = SQLPartitionType.PARTITION;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
            this.acceptChild(visitor, updateIndexClause);
//            this.acceptChild(visitor, parallelClause);
        }
    }

    @Override
    public SQLAlterTableTruncatePartitionsAction clone() {
        SQLAlterTableTruncatePartitionsAction x = new SQLAlterTableTruncatePartitionsAction();
        this.cloneTo(x);
//        x.type = this.type;
        return x;
    }

//    public SQLPartitionType getType() {
//        return type;
//    }
//
//    public void setType(SQLPartitionType type) {
//        this.type = type;
//    }
}
