package com.gumihoy.sql.basic.ast.expr.table.partition;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * Partition By
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * <p>
 * PARTITION BY CONSISTENT HASH (column [, column ]...)
 * { subpartition_by_range
 * | subpartition_by_list
 * | subpartition_by_hash
 * }
 * [ PARTITIONS AUTO ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/4/10.
 */
public class SQLPartitionByConsistentHash extends AbstractSQLPartitionBy {

    protected boolean partitionsAuto;

//    protected SQLTablespaceOptionExpr tableSpaceClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, partitionsNum);
//            this.acceptChild(visitor, storeInClause);
            this.acceptChild(visitor, subPartitionBy);
            this.acceptChild(visitor, partitions);
        }
    }

    @Override
    public SQLPartitionByConsistentHash clone() {
        SQLPartitionByConsistentHash x = new SQLPartitionByConsistentHash();
        this.cloneTo(x);
        return x;
    }

    public boolean isPartitionsAuto() {
        return partitionsAuto;
    }

    public void setPartitionsAuto(boolean partitionsAuto) {
        this.partitionsAuto = partitionsAuto;
    }

//    public SQLTablespaceOptionExpr getTableSpaceClause() {
//        return tableSpaceClause;
//    }
//
//    public void setTableSpaceClause(SQLTablespaceOptionExpr tableSpaceClause) {
//        this.tableSpaceClause = tableSpaceClause;
//    }
}
