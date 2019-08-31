package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * expr RLIKE pat
 * expr NOT RLIKE pat
 * https://dev.mysql.com/doc/refman/8.0/en/regexp.html
 *
 * @author kent on 2018/4/28.
 */
public class SQLRlikeCondition extends AbstractSQLExpr {

    protected ISQLExpr expr;

    protected boolean not;

    protected ISQLExpr pattern;

    public SQLRlikeCondition() {
    }

    public SQLRlikeCondition(ISQLExpr expr, ISQLExpr pattern) {
        setExpr(expr);
        setPattern(pattern);
    }

    public SQLRlikeCondition(ISQLExpr expr, boolean not, ISQLExpr pattern) {
        setExpr(expr);
        setPattern(pattern);
        this.not = not;
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

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public ISQLExpr getPattern() {
        return pattern;
    }

    public void setPattern(ISQLExpr pattern) {
        setChildParent(pattern);
        this.pattern = pattern;
    }

}
