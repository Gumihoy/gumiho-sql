package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * RENAME {INDEX|KEY} old_index_name TO new_index_name
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableRenameKeyConstraintAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected ISQLName name;
    protected ISQLName newName;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, newName);
//        }
    }

    @Override
    public SQLAlterTableRenameKeyConstraintAction clone() {
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
