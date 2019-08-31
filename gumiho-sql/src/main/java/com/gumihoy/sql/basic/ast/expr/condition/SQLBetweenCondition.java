package com.gumihoy.sql.basic.ast.expr.condition;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * expr1 [ NOT ] BETWEEN expr2 AND expr3
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/BETWEEN-Condition.html#GUID-868A7C9D-EDF9-44E7-91B5-C3F69E503CCB
 *
 * @author kent on 2018/5/11.
 */
public class SQLBetweenCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLExpr expr;

    protected boolean not;

    protected ISQLExpr between;

    protected ISQLExpr and;


    public SQLBetweenCondition(ISQLExpr expr, ISQLExpr between, ISQLExpr and) {
        this(expr, false, between, and);
    }

    public SQLBetweenCondition(ISQLExpr expr, boolean not, ISQLExpr between, ISQLExpr and) {
        this.setExpr(expr);
        this.not = not;
        this.setBetween(between);
        this.setAnd(and);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
            this.acceptChild(visitor, between);
            this.acceptChild(visitor, and);
        }
    }

    @Override
    public SQLBetweenCondition clone() {
        ISQLExpr exprClone = this.expr.clone();
        ISQLExpr betweenClone = this.between.clone();
        ISQLExpr andClone = this.and.clone();

        SQLBetweenCondition x = new SQLBetweenCondition(exprClone, betweenClone, andClone);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLBetweenCondition x) {
        super.cloneTo(x);

        x.not = this.not;
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

    public ISQLExpr getBetween() {
        return between;
    }

    public void setBetween(ISQLExpr between) {
        setChildParent(between);
        this.between = between;
    }

    public ISQLExpr getAnd() {
        return and;
    }

    public void setAnd(ISQLExpr and) {
        setChildParent(and);
        this.and = and;
    }
}
