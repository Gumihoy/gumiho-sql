package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * rename_partition_subpart
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/11.
 */
public abstract class AbstractSQLAlterTableRenamePartitionAction extends AbstractSQLExpr implements ISQLAlterTableRenamePartitionAction {

    protected ISQLExpr newName;

    @Override
    public AbstractSQLAlterTableRenamePartitionAction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == newName) {
            setNewName(target);
            return true;
        }
        return false;
    }

    public ISQLExpr getNewName() {
        return newName;
    }

    public void setNewName(ISQLExpr newName) {
        setChildParent(newName);
        this.newName = newName;
    }
}
