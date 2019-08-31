package com.gumihoy.sql.basic.ast.expr.type.alter;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ADD { map_order_function_spec | subprogram_spec } [, ADD { map_order_function_spec | subprogram_spec }]
 * <p>
 * alter_method_spec
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-TYPE-statement.html#GUID-A8B449E7-E3A8-48F4-A4C6-5BB87B1841CD
 *
 * @author kent on 2018/9/11.
 */
public class SQLAlterTypeDropAction extends AbstractSQLAlterTypeAction implements ISQLAlterTypeAction {

    protected ISQLAlterTypeDropActionExpr expr;

    public SQLAlterTypeDropAction(ISQLAlterTypeDropActionExpr expr) {
        setExpr(expr);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
        }
    }

    @Override
    public SQLAlterTypeDropAction clone() {
        ISQLAlterTypeDropActionExpr exprClone = this.expr.clone();
        SQLAlterTypeDropAction x = new SQLAlterTypeDropAction(exprClone);
        return x;
    }

    public ISQLAlterTypeDropActionExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLAlterTypeDropActionExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }


    public interface ISQLAlterTypeDropActionExpr extends ISQLExpr {
        @Override
        ISQLAlterTypeDropActionExpr clone();
    }

}
