package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * RENAME SUBPARTITION name TO new_name
 * <p>
 * rename_partition_subpart
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableRenameSubPartitionAction extends AbstractSQLAlterTableRenamePartitionAction {

    protected ISQLExpr name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, newName);
        }
    }

    @Override
    public SQLAlterTableRenameSubPartitionAction clone() {
        SQLAlterTableRenameSubPartitionAction x = new SQLAlterTableRenameSubPartitionAction();
        this.cloneTo(x);
        ISQLExpr nameClone = this.name.clone();
        x.setName(nameClone);
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = super.replace(source, target);
        if (replace) {
            return true;
        }

        if (source == name) {
            setName(target);
            return true;
        }

        return false;
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }
}
