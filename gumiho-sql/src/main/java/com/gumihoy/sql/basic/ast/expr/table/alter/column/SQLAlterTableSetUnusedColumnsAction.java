package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * SET UNUSED { COLUMN column | (column [, column ]...) } [ { CASCADE CONSTRAINTS | INVALIDATE }... ] [ ONLINE ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableSetUnusedColumnsAction extends AbstractSQLAlterTableDropColumnAction {

    protected boolean column;
    protected boolean paren = true;
    protected final List<ISQLExpr> columns = new ArrayList<>();
    protected boolean online;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, columns);
//        }
    }

    @Override
    public SQLAlterTableSetUnusedColumnsAction clone() {
        SQLAlterTableSetUnusedColumnsAction x = new SQLAlterTableSetUnusedColumnsAction();
        x.column = this.column;

        for (ISQLExpr column : this.columns) {
            ISQLExpr columnClone = column.clone();
            x.addColumn(columnClone);
        }

        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(columns, source, target, this);
        if (replace) {
            return true;
        }


        return false;
    }


    public boolean isColumn() {
        return column;
    }

    public void setColumn(boolean column) {
        this.column = column;
    }

    public boolean isParen() {
        return paren;
    }

    public void setParen(boolean paren) {
        this.paren = paren;
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


    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }


}
