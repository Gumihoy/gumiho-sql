package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * RENAME CONSTRAINT constraint_name TO new_constraint_name
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 *
 * RENAME CONSTRAINT old_name TO new_name
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableRenameConstraintAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected ISQLName name;
    protected ISQLName newName;

    public SQLAlterTableRenameConstraintAction(ISQLName name, ISQLName newName) {
        setName(name);
        setNewName(newName);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, newName);
        }
    }

    @Override
    public SQLAlterTableRenameConstraintAction clone() {
        return null;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLName getNewName() {
        return newName;
    }

    public void setNewName(ISQLName newName) {
        setChildParent(newName);
        this.newName = newName;
    }
}
