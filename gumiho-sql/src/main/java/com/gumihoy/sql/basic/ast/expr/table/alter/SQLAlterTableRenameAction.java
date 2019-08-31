package com.gumihoy.sql.basic.ast.expr.table.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * RENAME [TO|AS] new_tbl_name
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 * <p>
 * RENAME name=nameIdentifier TO newName=nameIdentifier
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableRenameAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected ISQLName name;
    protected SQLRenameOperator operator = SQLRenameOperator.TO;
    protected ISQLName newName;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, newName);
        }
    }

    @Override
    public SQLAlterTableRenameAction clone() {
        SQLAlterTableRenameAction x = new SQLAlterTableRenameAction();
        x.operator = this.operator;
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }
        return false;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public SQLRenameOperator getOperator() {
        return operator;
    }

    public void setOperator(SQLRenameOperator operator) {
        this.operator = operator;
    }

    public ISQLName getNewName() {
        return newName;
    }

    public void setNewName(ISQLName newName) {
        setChildParent(newName);
        this.newName = newName;
    }

    public enum SQLRenameOperator implements ISQLASTEnum {
        TO("to", "TO"),
        AS("as", "AS");

        public final String lower;
        public final String upper;


        SQLRenameOperator(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }
}
