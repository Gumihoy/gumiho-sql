package com.gumihoy.sql.basic.ast.expr.method.window;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLWindowSpecification;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * OVER (analytic_clause)
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/window-functions-usage.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Analytic-Functions.html
 *
 * @author kent on 2018/4/24.
 */
public class SQLOverClause extends AbstractSQLExpr implements ISQLOverClause {

    protected SQLWindowSpecification expr;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, expr);
//        }
    }

    @Override
    public SQLOverClause clone() {
        SQLOverClause x = new SQLOverClause();

        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLOverClause x) {
        super.cloneTo(x);

        if (this.expr != null) {
            SQLWindowSpecification exprClone = this.expr.clone();
            x.setExpr(exprClone);
        }
    }

    public SQLWindowSpecification getExpr() {
        return expr;
    }

    public void setExpr(SQLWindowSpecification expr) {
        setChildParent(expr);
        this.expr = expr;
    }

}
