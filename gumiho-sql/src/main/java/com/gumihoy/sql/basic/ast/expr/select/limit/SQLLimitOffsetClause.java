package com.gumihoy.sql.basic.ast.expr.select.limit;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [LIMIT {[offset,] row_count | row_count OFFSET offset}]
 * https://dev.mysql.com/doc/refman/8.0/en/select.html
 * <p>
 * [ LIMIT { count | ALL } ] [ OFFSET start [ ROW | ROWS ] ]
 * https://www.postgresql.org/docs/devel/static/sql-select.html
 * <p>
 *
 * @author kent on 2018/5/2.
 */
public class SQLLimitOffsetClause extends AbstractSQLExpr implements ISQLLimitClause {

    protected boolean offset;
    protected ISQLExpr offsetExpr;
    protected ISQLExpr rowCountExpr;
    private SQLRowType offSetRowType;

    public SQLLimitOffsetClause(ISQLExpr rowCountExpr) {
        this.rowCountExpr = rowCountExpr;
    }

    public SQLLimitOffsetClause(ISQLExpr offsetExpr, ISQLExpr rowCountExpr) {
        this(false, offsetExpr, rowCountExpr);
    }

    public SQLLimitOffsetClause(boolean offset, ISQLExpr offsetExpr, ISQLExpr rowCountExpr) {
        this.offset = offset;
        this.setOffsetExpr(offsetExpr);
        this.setRowCountExpr(rowCountExpr);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, offsetExpr);
            this.acceptChild(visitor, rowCountExpr);
        }
    }

    @Override
    public SQLLimitOffsetClause clone() {
        ISQLExpr rowCountClone = this.rowCountExpr.clone();
        SQLLimitOffsetClause x = new SQLLimitOffsetClause(rowCountClone);

        x.offset = this.offset;
        if (this.offsetExpr != null) {
            ISQLExpr offsetExprClone = this.offsetExpr.clone();
//            x.setOffsetExpr(offsetExprClone);
        }

//        x.offSetRowType = this.offSetRowType;

        return x;
    }

    public void cloneTo(SQLLimitOffsetClause x) {
        super.cloneTo(x);
    }


    public boolean isOffset() {
        return offset;
    }

    public void setOffset(boolean offset) {
        this.offset = offset;
    }

    public ISQLExpr getOffsetExpr() {
        return offsetExpr;
    }

    public void setOffsetExpr(ISQLExpr offsetExpr) {
        setChildParent(offsetExpr);
        this.offsetExpr = offsetExpr;
    }

    //
    public ISQLExpr getRowCountExpr() {
        return rowCountExpr;
    }

    public void setRowCountExpr(ISQLExpr rowCountExpr) {
        setChildParent(rowCountExpr);
        this.rowCountExpr = rowCountExpr;
    }

    public SQLRowType getOffSetRowType() {
        return offSetRowType;
    }

    public void setOffSetRowType(SQLRowType offSetRowType) {
        this.offSetRowType = offSetRowType;
    }
}
