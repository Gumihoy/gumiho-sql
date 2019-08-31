package com.gumihoy.sql.basic.ast.expr.view.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP UNIQUE (column [, column ]...)
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-VIEW.html#GUID-0DEDE960-B481-4B55-8027-EA9E4C863625
 * @author kent on 2018/7/13.
 */
public class SQLAlterViewDropUniqueConstraintAction extends AbstractSQLExpr implements ISQLAlterViewAction {

    protected final List<ISQLExpr> columns = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, columns);
//        }
    }

    @Override
    public SQLAlterViewDropUniqueConstraintAction clone() {
        SQLAlterViewDropUniqueConstraintAction x = new SQLAlterViewDropUniqueConstraintAction();
        for (ISQLExpr column : columns) {
            ISQLExpr columnClone = column.clone();
            x.addColumn(columnClone);
        }
        return x;
    }

    public List<ISQLExpr> getColumns() {
        return columns;
    }

    public void addColumn(ISQLExpr column) {
        if (column == null) {
            return;
        }
        setChildParent(column);
        this.columns.add(column);
    }

}
