package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [DEFAULT] DIRECTORY [=] name
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#collate%20clause
 *
 * @author kent on 2018/5/28.
 */
public class SQLDirectoryOptionExpr extends SQLSetOptionExpr {

    public SQLDirectoryOptionExpr() {
    }

    public SQLDirectoryOptionExpr(boolean default_, boolean eq, ISQLExpr value) {
        setDefault_(default_);
        this.eq = eq;
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, value);
        }
    }

    @Override
    public SQLDirectoryOptionExpr clone() {
        SQLDirectoryOptionExpr x = new SQLDirectoryOptionExpr();
        this.cloneTo(x);
        return x;
    }

}
