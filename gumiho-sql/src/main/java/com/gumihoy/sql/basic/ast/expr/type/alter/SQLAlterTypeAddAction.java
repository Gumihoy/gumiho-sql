package com.gumihoy.sql.basic.ast.expr.type.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ADD { map_order_function_spec | subprogram_spec } [, ADD { map_order_function_spec | subprogram_spec }]
 * <p>
 * alter_method_spec
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/ALTER-TYPE-statement.html#GUID-A8B449E7-E3A8-48F4-A4C6-5BB87B1841CD
 *
 * @author kent on 2018/9/11.
 */
public class SQLAlterTypeAddAction extends AbstractSQLAlterTypeAction implements ISQLAlterTypeAction {

    protected ISQLAlterTypeAddActionExpr expr;

    public SQLAlterTypeAddAction(ISQLAlterTypeAddActionExpr expr) {
        setExpr(expr);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
        }
    }

    @Override
    public SQLAlterTypeAddAction clone() {
        ISQLAlterTypeAddActionExpr exprClone = this.expr.clone();
        SQLAlterTypeAddAction x = new SQLAlterTypeAddAction(exprClone);
        return x;
    }

    public ISQLAlterTypeAddActionExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLAlterTypeAddActionExpr expr) {
        this.expr = expr;
    }

    public static interface ISQLAlterTypeAddActionExpr extends ISQLExpr {
        @Override
        ISQLAlterTypeAddActionExpr clone();
    }
}
