package com.gumihoy.sql.basic.ast.expr.table.alter.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * REMOVE PARTITIONING
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableRemovePartitioningAction extends AbstractSQLExpr implements ISQLAlterTablePartitionAction {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLAlterTableRemovePartitioningAction clone() {
        SQLAlterTableRemovePartitioningAction x = new SQLAlterTableRemovePartitioningAction();
        this.cloneTo(x);
        return x;
    }

}
