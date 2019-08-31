package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * expr [NOT] REGEXP pat
 * https://dev.mysql.com/doc/refman/8.0/en/regexp.html
 *
 * @author kent on 2018/4/28.
 */
public class SQLRegexpCondition extends AbstractSQLExpr {

    protected ISQLExpr expr;

    protected boolean not;

    protected ISQLExpr pattern;

    public SQLRegexpCondition() {
    }

    public SQLRegexpCondition(ISQLExpr expr, ISQLExpr pattern) {
        setExpr(expr);
        setPattern(pattern);
    }

    public SQLRegexpCondition(ISQLExpr expr, boolean not, ISQLExpr pattern) {
        this.not = not;
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
        setExpr(expr);
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
        setExpr(pattern);
        this.pattern = pattern;
    }

}
