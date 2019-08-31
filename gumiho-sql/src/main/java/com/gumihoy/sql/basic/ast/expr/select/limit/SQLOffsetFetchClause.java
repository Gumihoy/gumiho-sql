package com.gumihoy.sql.basic.ast.expr.select.limit;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [ OFFSET start [ ROW | ROWS ] ] [ FETCH { FIRST | NEXT } [ count ] { ROW | ROWS } ONLY ]
 * [ OFFSET offset { ROW | ROWS } ] [ FETCH { FIRST | NEXT } [ { rowcount | percent PERCENT } ] { ROW | ROWS } { ONLY | WITH TIES } ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 * <p>
 * https://www.postgresql.org/docs/devel/static/sql-values.html
 *
 * @author kent onCondition 2018/3/21.
 */
public class SQLOffsetFetchClause extends AbstractSQLExpr implements ISQLLimitClause {

    protected ISQLExpr offsetExpr;
    protected SQLRowType offSetRowType;

    protected SQLFetchType fetchType;
    protected ISQLExpr rowCountExpr;
    protected boolean percent;
    protected SQLRowType fetchRowType;
    protected SQLFetchOnlyType onlyType;


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, offsetExpr);
            this.acceptChild(visitor, rowCountExpr);
        }
    }

    @Override
    public SQLOffsetFetchClause clone() {

        SQLOffsetFetchClause x = new SQLOffsetFetchClause();

        if (this.offsetExpr != null) {
            ISQLExpr offsetExprClone = this.offsetExpr.clone();
            x.setOffsetExpr(offsetExprClone);
        }
//        x.offSetRowType = this.offSetRowType;

        x.fetchType = this.fetchType;
        if (this.rowCountExpr != null) {
            ISQLExpr rowCountExprClone = this.rowCountExpr.clone();
            x.setRowCountExpr(rowCountExprClone);
        }
        x.percent = this.percent;

//        x.fetchRowType = this.fetchRowType;
        x.onlyType = this.onlyType;

        return x;
    }

    public void cloneTo(SQLOffsetFetchClause x) {
        super.cloneTo(x);
    }


    public ISQLExpr getOffsetExpr() {
        return offsetExpr;
    }

    public void setOffsetExpr(ISQLExpr offsetExpr) {
        setChildParent(offsetExpr);
        this.offsetExpr = offsetExpr;
    }

    public SQLRowType getOffSetRowType() {
        return offSetRowType;
    }

    public void setOffSetRowType(SQLRowType offSetRowType) {
        this.offSetRowType = offSetRowType;
    }

    public SQLFetchType getFetchType() {
        return fetchType;
    }

    public void setFetchType(SQLFetchType fetchType) {
        this.fetchType = fetchType;
    }

    public ISQLExpr getRowCountExpr() {
        return rowCountExpr;
    }

    public void setRowCountExpr(ISQLExpr rowCountExpr) {
        setChildParent(rowCountExpr);
        this.rowCountExpr = rowCountExpr;
    }

    public boolean isPercent() {
        return percent;
    }

    public void setPercent(boolean percent) {
        this.percent = percent;
    }

    public SQLRowType getFetchRowType() {
        return fetchRowType;
    }

    public void setFetchRowType(SQLRowType fetchRowType) {
        this.fetchRowType = fetchRowType;
    }

    public SQLFetchOnlyType getOnlyType() {
        return onlyType;
    }

    public void setOnlyType(SQLFetchOnlyType onlyType) {
        this.onlyType = onlyType;
    }

    public enum SQLFetchType implements ISQLASTEnum {
        FIRST("", ""),
        NEXT("", "");

        public final String lower;
        public final String upper;

        SQLFetchType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }

        @Override
        public String toString() {
            return upper;
        }
    }

    public enum SQLFetchOnlyType implements ISQLASTEnum {
        ONLY("ONLY", "ONLY"),
        WITH_TIES("WITH_TIES", "WITH_TIES");

        public final String lower;
        public final String upper;

        SQLFetchOnlyType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }

        @Override
        public String toString() {
            return upper;
        }
    }
}
