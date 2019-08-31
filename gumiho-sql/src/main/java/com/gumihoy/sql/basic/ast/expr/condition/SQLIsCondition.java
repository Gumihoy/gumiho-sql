package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * expr IS [ NOT ] expr
 *
 *
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Null-Conditions.html#GUID-657F2BA6-5687-4A00-8C2F-57515FD2DAEB
 *
 * @author kent on 2018/5/11.
 */
public class SQLIsCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLExpr expr;

    protected boolean not;

    protected ISQLExpr value;

    public SQLIsCondition() {
    }

    public SQLIsCondition(ISQLExpr expr) {
        this(expr, null);
    }

    public SQLIsCondition(ISQLExpr expr, ISQLExpr value) {
        this(expr, false, value);
    }

    public SQLIsCondition(ISQLExpr expr, boolean not, ISQLExpr value) {
        setExpr(expr);
        this.not = not;
        setValue(value);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
        }
    }

    @Override
    public SQLIsNanCondition clone() {
        SQLIsNanCondition x = new SQLIsNanCondition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLIsNanCondition x) {
        super.cloneTo(x);

        ISQLExpr exprClone = this.expr.clone();
        x.setExpr(exprClone);

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


    public ISQLExpr getValue() {
        return value;
    }

    public void setValue(ISQLExpr value) {
        setChildParent(value);
        this.value = value;
    }
}
