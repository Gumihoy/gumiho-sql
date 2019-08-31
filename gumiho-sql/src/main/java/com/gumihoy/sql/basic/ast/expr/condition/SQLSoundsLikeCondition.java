package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * expr SOUNDS LIKE pat
 * https://dev.mysql.com/doc/refman/8.0/en/expressions.html
 *
 * @author kent on 2018/4/28.
 */
public class SQLSoundsLikeCondition extends AbstractSQLExpr {

    protected ISQLExpr expr;

    protected ISQLExpr pattern;

    public SQLSoundsLikeCondition() {
    }

    public SQLSoundsLikeCondition(ISQLExpr expr, ISQLExpr pattern) {
        setExpr(expr);
        setPattern(pattern);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
            this.acceptChild(visitor, pattern);
        }
    }


    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }

    public ISQLExpr getPattern() {
        return pattern;
    }

    public void setPattern(ISQLExpr pattern) {
        setChildParent(pattern);
        this.pattern = pattern;
    }

}
