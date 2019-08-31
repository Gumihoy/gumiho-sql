package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SPLIT PARTITION name=nameIdentifier iAlterTableSplitPartitionActionItem
 * splitNestedTablePart? filterCondition? dependentTablesClause? updateIndexClause?
 * parallelClause? allowDisallowClustering? ONLINE?
 * <p>
 * split_table_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableSplitPartitionAction extends AbstractSQLAlterTableSplitPartitionAction {

    protected ISQLExpr name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, item);
        }
    }

    @Override
    public SQLAlterTableSplitPartitionAction clone() {
        SQLAlterTableSplitPartitionAction x = new SQLAlterTableSplitPartitionAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableSplitPartitionAction x) {
        super.cloneTo(x);


    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }
}
