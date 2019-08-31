package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://www.postgresql.org/docs/current/static/sql-createsequence.html
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLOwnedToOption extends AbstractSQLExpr {

    protected ISQLExpr newName;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, newName);
//        }
    }

    @Override
    public SQLOwnedToOption clone() {
        SQLOwnedToOption x = new SQLOwnedToOption();

        ISQLExpr newNameClone = this.newName.clone();
        x.setNewName(newNameClone);

        return x;
    }


    public ISQLExpr getNewName() {
        return newName;
    }

    public void setNewName(ISQLExpr newName) {
        setChildParent(newName);
        this.newName = newName;
    }
}
