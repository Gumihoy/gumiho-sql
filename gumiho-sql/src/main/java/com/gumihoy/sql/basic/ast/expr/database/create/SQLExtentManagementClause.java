package com.gumihoy.sql.basic.ast.expr.database.create;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSizeClause;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * EXTENT MANAGEMENT LOCAL
 * [ AUTOALLOCATE
 * | UNIFORM [ SIZE size_clause ]
 * ]
 * <p>
 * extent_management_clause
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/extent_management_clause.html
 *
 * @author kent on 2019-07-21.
 */
public class SQLExtentManagementClause  extends AbstractSQLExpr  {

    protected ISQLExpr expr;

    public SQLExtentManagementClause() {
    }

    public SQLExtentManagementClause(ISQLExpr expr) {
        setExpr(expr);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, expr);
        }
    }

    @Override
    public AbstractSQLExpr clone() {
        return super.clone();
    }


    public ISQLExpr getExpr() {
        return expr;
    }

    public void setExpr(ISQLExpr expr) {
        setChildParent(expr);
        this.expr = expr;
    }

    public static class SQLAutoAllocateExpr extends AbstractSQLExpr {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }
    }


    public static class SQLUniformExpr extends AbstractSQLExpr {

        protected SQLSizeClause sizeClause;

        public SQLUniformExpr() {
        }

        public SQLUniformExpr(SQLSizeClause sizeClause) {
            setSizeClause(sizeClause);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, sizeClause);
            }
        }

        public SQLSizeClause getSizeClause() {
            return sizeClause;
        }

        public void setSizeClause(SQLSizeClause sizeClause) {
            setChildParent(sizeClause);
            this.sizeClause = sizeClause;
        }
    }
}
