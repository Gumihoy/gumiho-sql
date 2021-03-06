package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * RENAME PARTITION FOR (name [,name]... )  TO new_name
 * <p>
 * rename_partition_subpart
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableRenamePartitionForAction extends AbstractSQLAlterTableRenamePartitionAction {

    protected final List<ISQLExpr> names = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, names);
            this.acceptChild(visitor, newName);
        }
    }

    @Override
    public SQLAlterTableRenamePartitionForAction clone() {
        SQLAlterTableRenamePartitionForAction x = new SQLAlterTableRenamePartitionForAction();
        this.cloneTo(x);
        for (ISQLExpr name : this.names) {
            ISQLExpr nameClone = name.clone();
            x.addName(nameClone);
        }
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = super.replace(source, target);
        if (replace) {
            return true;
        }

        replace = replaceInList(names, source, target, this);
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

}
