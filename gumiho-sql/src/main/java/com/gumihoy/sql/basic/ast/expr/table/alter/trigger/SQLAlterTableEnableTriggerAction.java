package com.gumihoy.sql.basic.ast.expr.table.alter.trigger;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ENABLE TRIGGER [ trigger_name | ALL | USER ]
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableEnableTriggerAction extends AbstractSQLAlterTableTriggerAction implements ISQLAlterTableAction {

    public SQLAlterTableEnableTriggerAction(ISQLExpr name) {
        super(name);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLAlterTableEnableTriggerAction clone() {
        ISQLExpr nameClone = this.name.clone();
        return new SQLAlterTableEnableTriggerAction(nameClone);
    }


}
