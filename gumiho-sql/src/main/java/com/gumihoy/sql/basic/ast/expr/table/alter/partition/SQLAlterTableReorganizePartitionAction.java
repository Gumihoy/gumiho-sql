package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLPartitionDefinition;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * REORGANIZE PARTITION partition_names INTO (partition_definitions)
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableReorganizePartitionAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected final List<ISQLExpr> names = new ArrayList<>();
    protected final List<SQLPartitionDefinition> partitions = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, names);
//            this.acceptChild(visitor, partitions);
//        }
    }

    @Override
    public SQLAlterTableReorganizePartitionAction clone() {
        SQLAlterTableReorganizePartitionAction x = new SQLAlterTableReorganizePartitionAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableReorganizePartitionAction x) {
        super.cloneTo(x);
        for (ISQLExpr name : this.names) {
            ISQLExpr nameClone = name.clone();
            x.addName(nameClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(names, source, target, this);
        if (replace) {
            return true;
        }

        return false;
    }

    public List<ISQLExpr> getNames() {
        return names;
    }

    public void addName(ISQLExpr name) {
        if (name == null) {
            return;
        }
        setChildParent(name);
        this.names.add(name);
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
}
