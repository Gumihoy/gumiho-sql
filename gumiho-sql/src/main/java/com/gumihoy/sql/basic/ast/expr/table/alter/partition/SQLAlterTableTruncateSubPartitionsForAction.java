package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * TRUNCATE (SUBPARTITION|SUBPARTITIONS) alterTablePartitionItem (COMMA alterTablePartitionItem)*
 * ((DROP ALL? | REUSE) STORAGE)? updateIndexClause? parallelClause? CASCADE?
 * truncate_partition_subpart
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableTruncateSubPartitionsForAction extends AbstractSQLAlterTableTruncatePartitionAction implements ISQLAlterTableAction {

//    protected SQLSubPartitionType type = SQLSubPartitionTypenType.SUBPARTITION;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
            this.acceptChild(visitor, updateIndexClause);
//            this.acceptChild(visitor, parallelClause);
        }
    }

    @Override
    public SQLAlterTableTruncateSubPartitionsForAction clone() {
        SQLAlterTableTruncateSubPartitionsForAction x = new SQLAlterTableTruncateSubPartitionsForAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableTruncateSubPartitionsForAction x) {
        super.cloneTo(x);
//        x.type = this.type;
    }


//    public SQLSubPartitionType getType() {
//        return type;
//    }
//
//    public void setType(SQLSubPartitionType type) {
//        this.type = type;
//    }
}
