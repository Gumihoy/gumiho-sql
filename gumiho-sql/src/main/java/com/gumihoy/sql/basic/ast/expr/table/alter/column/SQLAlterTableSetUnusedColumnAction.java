package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * SET UNUSED { COLUMN column | (column [, column ]...) } [ { CASCADE CONSTRAINTS | INVALIDATE }... ] [ ONLINE ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public class SQLAlterTableSetUnusedColumnAction extends AbstractSQLAlterTableDropColumnAction {

    protected boolean column;
    protected boolean paren = true;
    protected ISQLExpr name;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
        }
    }

    @Override
    public SQLAlterTableSetUnusedColumnAction clone() {
        SQLAlterTableSetUnusedColumnAction x = new SQLAlterTableSetUnusedColumnAction();
        x.column = this.column;

        ISQLExpr nameClone = name.clone();
        x.setName(nameClone);

        return x;
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

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

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }


}
