package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [DEFAULT] COLLATE [=] name
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#collate%20clause
 *
 * @author kent on 2018/5/28.
 */
public class SQLCollateOptionExpr extends SQLSetOptionExpr {

    public SQLCollateOptionExpr() {
    }

    public SQLCollateOptionExpr(boolean default_, boolean eq, ISQLExpr value) {
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
    public SQLCollateOptionExpr clone() {
        SQLCollateOptionExpr x = new SQLCollateOptionExpr();
        this.cloneTo(x);
        return x;
    }

}
