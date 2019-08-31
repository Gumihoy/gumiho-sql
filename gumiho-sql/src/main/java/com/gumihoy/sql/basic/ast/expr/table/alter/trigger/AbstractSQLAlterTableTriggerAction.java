package com.gumihoy.sql.basic.ast.expr.table.alter.trigger;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;

/**
 * DISABLE TRIGGER [ trigger_name | ALL | USER ]
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 * @author kent on 2018/6/4.
 */
public abstract class AbstractSQLAlterTableTriggerAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected ISQLExpr name;

    public AbstractSQLAlterTableTriggerAction(ISQLExpr name) {
        setName(name);
    }

    @Override
    public AbstractSQLAlterTableTriggerAction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
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
