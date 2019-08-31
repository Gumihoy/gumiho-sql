package com.gumihoy.sql.basic.ast.expr.table.alter.constraint;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.alter.ISQLAlterTableAction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ALTER CONSTRAINT constraint_name [ DEFERRABLE | NOT DEFERRABLE ] [ INITIALLY DEFERRED | INITIALLY IMMEDIATE ]
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableAlterConstraintAction extends AbstractSQLExpr implements ISQLAlterTableAction {

    protected ISQLName name;
//    protected SQLDeferrableType deferrable;
//    protected SQLInitiallyType initially;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//        }
    }

    @Override
    public SQLAlterTableAlterConstraintAction clone() {
        return null;
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

//    public SQLDeferrableType getDeferrable() {
//        return deferrable;
//    }
//
//    public void setDeferrable(SQLDeferrableType deferrable) {
//        this.deferrable = deferrable;
//    }
//
//    public SQLInitiallyType getInitially() {
//        return initially;
//    }
//
//    public void setInitially(SQLInitiallyType initially) {
//        this.initially = initially;
//    }
}
