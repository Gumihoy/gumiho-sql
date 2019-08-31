package com.gumihoy.sql.basic.ast.expr.condition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * { expr [ NOT ] IN ({ expression_list | subquery }) | ( expr [, expr ]... )[ NOT ] IN ({ expression_list [, expression_list ]...| subquery})}
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#in%20predicate
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/IN-Condition.html#GUID-C7961CB3-8F60-47E0-96EB-BDCF5DB1317C
 *
 * @author kent on 2018/5/15.
 */
public class SQLInCondition extends AbstractSQLExpr implements ISQLCondition {

    protected ISQLExpr expr;

    protected boolean not;

    protected final List<ISQLExpr> values = new ArrayList<>();

    public SQLInCondition() {
    }

    public SQLInCondition(ISQLExpr expr) {
        setExpr(expr);
    }

    public SQLInCondition(ISQLExpr expr, ISQLExpr... values) {
        this(expr, false, values);
    }

    public SQLInCondition(ISQLExpr expr, boolean not, ISQLExpr... values) {
        setExpr(expr);
        this.not = not;
        for (ISQLExpr value : values) {
            this.addValue(value);
        }
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
            this.acceptChild(visitor, values);
        }
    }

    @Override
    public SQLInCondition clone() {
        SQLInCondition x = new SQLInCondition();
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLInCondition x) {
        super.cloneTo(x);

        ISQLExpr exprClone = this.expr.clone();
        x.setExpr(exprClone);

        for (ISQLExpr value : values) {
            ISQLExpr valueClone = value.clone();
            this.addValue(valueClone);
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

    public List<ISQLExpr> getValues() {
        return values;
    }

    public void addValue(ISQLExpr value) {
        if (value == null) {
            return;
        }
        setChildParent(value);
        this.values.add(value);
    }

}
