package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SPLIT SUBPARTITION name=nameIdentifier iAlterTableSplitPartitionActionItem
 * splitNestedTablePart? filterCondition? dependentTablesClause? updateIndexClause?
 * parallelClause? allowDisallowClustering? ONLINE?
 * <p>
 * <p>
 * split_table_subpartition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableSplitSubPartitionAction extends AbstractSQLAlterTableSplitSubPartitionAction {

    protected ISQLExpr name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, item);
        }
    }

    @Override
    public SQLAlterTableSplitSubPartitionAction clone() {
        SQLAlterTableSplitSubPartitionAction x = new SQLAlterTableSplitSubPartitionAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableSplitSubPartitionAction x) {
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
