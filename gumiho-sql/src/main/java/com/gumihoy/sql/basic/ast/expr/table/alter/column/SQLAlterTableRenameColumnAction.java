package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * RENAME COLUMN old_col_name TO new_col_name
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 * <p>
 * RENAME [ COLUMN ] column_name TO new_column_name
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 * <p>
 * <p>
 * RENAME COLUMN old_name TO new_name
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableRenameColumnAction extends AbstractSQLExpr implements ISQLAlterTableColumnAction {

    protected boolean column = true;
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
    public SQLAlterTableRenameColumnAction clone() {
        SQLAlterTableRenameColumnAction x = new SQLAlterTableRenameColumnAction();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLAlterTableRenameColumnAction x) {
        super.cloneTo(x);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            this.setName((ISQLName) target);
            return true;
        }

        if (source == newName
                && target instanceof ISQLName) {
            this.setNewName((ISQLName) target);
            return true;
        }
        return false;
    }


    public boolean isColumn() {
        return column;
    }

    public void setColumn(boolean column) {
        this.column = column;
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
