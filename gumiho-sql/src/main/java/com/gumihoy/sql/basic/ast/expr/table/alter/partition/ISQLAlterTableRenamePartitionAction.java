package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

/**
 * @author kent on 2018/7/16.
 */
public interface ISQLAlterTableRenamePartitionAction extends ISQLAlterTablePartitionAction {
    @Override
    ISQLAlterTableRenamePartitionAction clone();

    ISQLExpr getNewName();

    void setNewName(ISQLExpr newName);

}
