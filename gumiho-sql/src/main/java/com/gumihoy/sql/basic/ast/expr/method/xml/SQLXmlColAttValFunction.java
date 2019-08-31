package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * XMLCOLATTVAL (value_expr [ AS { c_alias  | EVALNAME value_expr } ] [, value_expr [ AS { c_alias  | EVALNAME value_expr } ] ]...)
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XMLCOLATTVAL.html#GUID-AE3B6441-74D8-4033-900B-A578A79E5F0A
 *
 * @author kent on 2018/5/24.
 */
public class SQLXmlColAttValFunction extends AbstractSQLFunction {

    public SQLXmlColAttValFunction() {
//        super(SQLReserved.XMLCOLATTVAL.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    /**
     * value_expr AS EVALNAME value_expr
     */
    public static class SQLArgumentExpr extends AbstractSQLExpr {
        protected ISQLExpr expr;
        protected ISQLExpr alias;

        public SQLArgumentExpr(ISQLExpr expr, ISQLExpr alias) {
            setExpr(expr);
            setAlias(alias);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, expr);
//                this.acceptChild(visitor, alias);
//            }
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
        }

        public ISQLExpr getAlias() {
            return alias;
        }

        public void setAlias(ISQLExpr alias) {
            setChildParent(alias);
            this.alias = alias;
        }
    }
}
