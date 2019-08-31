package com.gumihoy.sql.basic.ast.expr.condition;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * expr IS [ NOT ] NAN
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Floating-Point-Conditions.html#GUID-D7707649-2C93-4553-BF78-F461F17A634E
 *
 * @author kent on 2018/5/11.
 */
public class SQLIsNanCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLExpr expr;

    protected boolean not;

    public SQLIsNanCondition() {
    }

    public SQLIsNanCondition(ISQLExpr expr) {
        setExpr(expr);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, expr);
//        }
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (expr == source) {
            setExpr(target);
            return true;
        }

        return false;
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
}
