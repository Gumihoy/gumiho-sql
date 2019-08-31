package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;

/**
 * @author kent on 2018/7/11.
 */
public interface ISQLAlterTableConstraintAction extends ISQLAlterTableAction {
    @Override
    ISQLAlterTableConstraintAction clone();
}
