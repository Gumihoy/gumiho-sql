package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ALTER INDEX index_name {VISIBLE | INVISIBLE}
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 *
 * @author kent on 2018/7/29.
 */
public class SQLAlterTableAlterIndexConstraintAction extends AbstractSQLExpr implements ISQLAlterTableConstraintAction {

    protected ISQLExpr name;
//    protected SQLVisibleType visible;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLAlterTableAlterIndexConstraintAction clone() {
        SQLAlterTableAlterIndexConstraintAction x = new SQLAlterTableAlterIndexConstraintAction();

        return x;
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

//    public SQLVisibleType getVisible() {
//        return visible;
//    }
//
//    public void setVisible(SQLVisibleType visible) {
//        this.visible = visible;
//    }
}
