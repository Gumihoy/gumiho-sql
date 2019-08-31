package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DEFINER [=] name
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#collate%20clause
 *
 * @author kent on 2018/5/28.
 */
public class SQLDefinerOptionExpr extends SQLSetOptionExpr {

    public SQLDefinerOptionExpr() {
    }

    public SQLDefinerOptionExpr(ISQLExpr value) {
        this(true, value);
    }

    public SQLDefinerOptionExpr(boolean eq, ISQLExpr value) {
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
    public SQLDefinerOptionExpr clone() {
        SQLDefinerOptionExpr x = new SQLDefinerOptionExpr();
        this.cloneTo(x);
        return x;
    }

}
