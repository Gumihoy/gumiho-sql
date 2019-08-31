package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * VALIDATE CONSTRAINT constraint_name
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableValidateTableConstraintAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected ISQLName name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if(visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLAlterTableValidateTableConstraintAction clone() {
        SQLAlterTableValidateTableConstraintAction x = new SQLAlterTableValidateTableConstraintAction();
        ISQLName nameClone = this.name.clone();
        x.setName(nameClone);
        return x;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName)target);
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

}
