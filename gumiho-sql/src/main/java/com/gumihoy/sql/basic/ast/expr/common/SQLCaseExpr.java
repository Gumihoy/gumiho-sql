package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * CASE [expr]
 * { WHEN expr THEN return_expr }...
 * [ELSE else_expr] END
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#simple%20case
 *
 * CASE value WHEN [compare_value] THEN result [WHEN [compare_value] THEN result ...] [ELSE result] END
 * CASE WHEN [condition] THEN result [WHEN [condition] THEN result ...] [ELSE result] END
 * https://dev.mysql.com/doc/refman/8.0/en/control-flow-functions.html#operator_case
 *
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/expression.html#GUID-D4700B45-F2C8-443E-AEE7-2BD20FFD45B8
 *
 * @author kent on 2018/4/25.
 */
public class SQLCaseExpr extends AbstractSQLExpr {

    protected ISQLExpr expr;

    protected final List<SQLCaseExprWhenItem> whenItems = new ArrayList<>();

    protected SQLCaseExprElseClause elseClause;

    public SQLCaseExpr() {
    }

    public SQLCaseExpr(ISQLExpr expr) {
        setExpr(expr);
    }

    public SQLCaseExpr(ISQLExpr expr, SQLCaseExprWhenItem... whenItems) {
        setExpr(expr);
        for (SQLCaseExprWhenItem whenItem : whenItems) {
            this.addWhenItem(whenItem);
        }
    }

    public SQLCaseExpr(ISQLExpr expr, SQLCaseExprElseClause elseClause, SQLCaseExprWhenItem... whenItems) {
        setExpr(expr);
        setElseClause(elseClause);
        for (SQLCaseExprWhenItem whenItem : whenItems) {
            this.addWhenItem(whenItem);
        }

    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
            this.acceptChild(visitor, whenItems);
            this.acceptChild(visitor, elseClause);
        }
    }

    @Override
    public SQLCaseExpr clone() {
        SQLCaseExpr x = new SQLCaseExpr();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCaseExpr x) {
        super.cloneTo(x);

        if (expr != null) {
            ISQLExpr exprClone = this.expr.clone();
            x.setExpr(exprClone);
        }

        for (SQLCaseExprWhenItem whenItem : whenItems) {
            SQLCaseExprWhenItem whenItemClone = whenItem.clone();
            x.addWhenItem(whenItemClone);
        }

        if (elseClause != null) {
            SQLCaseExprElseClause elseClauseClone = this.elseClause.clone();
            x.setElseClause(elseClauseClone);
        }
    }

    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }


    public List<SQLCaseExprWhenItem> getWhenItems() {
        return whenItems;
    }

    public void addWhenItem(SQLCaseExprWhenItem whenItem) {
        if (whenItem == null) {
            return;
        }
        setChildParent(whenItem);
        this.whenItems.add(whenItem);
    }

    public SQLCaseExprElseClause getElseClause() {
        return elseClause;
    }

    public void setElseClause(SQLCaseExprElseClause elseClause) {
        setChildParent(elseClause);
        this.elseClause = elseClause;
    }


    public static class SQLCaseExprWhenItem extends AbstractSQLExpr {

        protected ISQLExpr when;
        protected ISQLExpr then;

        public SQLCaseExprWhenItem() {
        }

        public SQLCaseExprWhenItem(ISQLExpr when, ISQLExpr then) {
            setWhen(when);
            setThen(then);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, when);
                this.acceptChild(visitor, then);
            }
        }

        @Override
        public SQLCaseExprWhenItem clone() {
            SQLCaseExprWhenItem x = new SQLCaseExprWhenItem();

            ISQLExpr whenClone = when.clone();
            x.setWhen(whenClone);

            ISQLExpr thenClone = this.then.clone();
            x.setThen(thenClone);

            return x;
        }

        public ISQLExpr getWhen() {
            return when;
        }

        public void setWhen(ISQLExpr when) {
            setChildParent(when);
            this.when = when;
        }

        public ISQLExpr getThen() {
            return then;
        }

        public void setThen(ISQLExpr then) {
            setChildParent(then);
            this.then = then;
        }
    }


    public static class SQLCaseExprElseClause extends AbstractSQLExpr {

        protected ISQLExpr expr;

        public SQLCaseExprElseClause() {
        }

        public SQLCaseExprElseClause(ISQLExpr expr) {
            setExpr(expr);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, expr);
            }
        }

        @Override
        public SQLCaseExprElseClause clone() {
            SQLCaseExprElseClause x = new SQLCaseExprElseClause();

            ISQLExpr exprClone = this.expr.clone();
            x.setExpr(exprClone);

            return x;
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
        }
    }

}
