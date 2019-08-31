package com.gumihoy.sql.basic.ast.expr.method;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * CUBE_TABLE ( ' { schema.cube [ {HIERARCHY | HRR} dimension hierarchy ]...| schema.dimension [ {HIERARCHY | HRR} [dimension] hierarchy ] }'
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CUBE_TABLE.html#GUID-55CDE2F2-14ED-4F8F-B5BF-1566C0E18727
 *
 * @author kent on 2018/5/21.
 */
public class SQLCubeTableFunction extends AbstractSQLFunction {


    public SQLCubeTableFunction() {
//        super(SQLReserved.CUBE_TABLE.ofExpr());
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    public static class SQLArgumentExpr extends AbstractSQLExpr {

        protected ISQLExpr expr;

        protected final List<SQLCubeTableOptionExpr> optionExprs = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, expr);
//                this.acceptChild(visitor, optionExprs);
//            }
        }

        public ISQLExpr getExpr() {
            return expr;
        }

        public void setExpr(ISQLExpr expr) {
            setChildParent(expr);
            this.expr = expr;
        }

        public List<SQLCubeTableOptionExpr> getOptionExprs() {
            return optionExprs;
        }

        public void addOptionExpr(SQLCubeTableOptionExpr optionExpr) {
            if (optionExpr == null) {
                return;
            }

        }
    }


    public static class SQLCubeTableOptionExpr extends AbstractSQLExpr {

//        protected SQLReserved hrr;
        protected ISQLExpr dimension;
        protected ISQLExpr hierarchy;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, dimension);
//                this.acceptChild(visitor, hierarchy);
//            }
        }

//        public SQLReserved getHrr() {
//            return hrr;
//        }
//
//        public void setHrr(SQLReserved hrr) {
//            this.hrr = hrr;
//        }

        public ISQLExpr getDimension() {
            return dimension;
        }

        public void setDimension(ISQLExpr dimension) {
            setChildParent(dimension);
            this.dimension = dimension;
        }

        public ISQLExpr getHierarchy() {
            return hierarchy;
        }

        public void setHierarchy(ISQLExpr hierarchy) {
            setChildParent(hierarchy);
            this.hierarchy = hierarchy;
        }
    }


}
