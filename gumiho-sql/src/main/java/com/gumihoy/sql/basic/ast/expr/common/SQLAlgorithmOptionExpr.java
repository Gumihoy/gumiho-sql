package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ALGORITHM [=] name
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#collate%20clause
 *
 * @author kent on 2018/5/28.
 */
public class SQLAlgorithmOptionExpr extends SQLSetOptionExpr {

    public SQLAlgorithmOptionExpr() {
    }

    public SQLAlgorithmOptionExpr(ISQLExpr value) {
        this(true, value);
    }

    public SQLAlgorithmOptionExpr(boolean eq, ISQLExpr value) {
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
    public SQLAlgorithmOptionExpr clone() {
        SQLAlgorithmOptionExpr x = new SQLAlgorithmOptionExpr();
        this.cloneTo(x);
        return x;
    }

}
