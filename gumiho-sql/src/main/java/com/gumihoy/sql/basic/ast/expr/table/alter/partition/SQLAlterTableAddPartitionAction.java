package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.SQLDependentTablesClause;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ADD PARTITION (partition_definition)
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 *
 *
 *
 * ADD partitionDefinition (COMMA partitionDefinition)* (BEFORE expr)? dependentTablesClause?
 * add_table_partition
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableAddPartitionAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();

    protected ISQLExpr before;

    protected SQLDependentTablesClause dependentTablesClause;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, partitions);
            this.acceptChild(visitor, before);
            this.acceptChild(visitor, dependentTablesClause);
        }
    }

    @Override
    public SQLAlterTableAddPartitionAction clone() {
        SQLAlterTableAddPartitionAction x = new SQLAlterTableAddPartitionAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableAddPartitionAction x) {
        super.cloneTo(x);

        for (SQLPartitionDefinition partition : partitions) {
            SQLPartitionDefinition partitionClone = partition.clone();
            x.addPartition(partitionClone);
        }

    }

    public List<SQLPartitionDefinition> getPartitions() {
        return partitions;
    }

    public void addPartition(SQLPartitionDefinition partition) {
        if (partition == null) {
            return;
        }
        setChildParent(partition);
        this.partitions.add(partition);
    }

    public ISQLExpr getBefore() {
        return before;
    }

    public void setBefore(ISQLExpr before) {
        setChildParent(before);
        this.before = before;
    }

    public SQLDependentTablesClause getDependentTablesClause() {
        return dependentTablesClause;
    }

    public void setDependentTablesClause(SQLDependentTablesClause dependentTablesClause) {
        setChildParent(dependentTablesClause);
        this.dependentTablesClause = dependentTablesClause;
    }
}
