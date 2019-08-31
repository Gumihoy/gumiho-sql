package com.gumihoy.sql.basic.ast.expr.select;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#select%20target%20list
 *
 * @author kent on 2018/5/4.
 */
public class SQLSelectTargetItem extends AbstractSQLExpr {

    protected ISQLExpr expr;

    public SQLSelectTargetItem(ISQLExpr expr) {
        setExpr(expr);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
        }
    }

    @Override
    public SQLSelectTargetItem clone() {
        ISQLExpr exprClone = this.expr.clone();
        SQLSelectTargetItem x = new SQLSelectTargetItem(exprClone);
        return x;
    }

    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }
}
