package com.gumihoy.sql.basic.ast.expr.view.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * READ WRITE
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-VIEW.html#GUID-0DEDE960-B481-4B55-8027-EA9E4C863625
 * @author kent on 2018/7/13.
 */
public class SQLAlterViewReadWriteAction extends AbstractSQLExpr implements ISQLAlterViewAction {


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLAlterViewReadWriteAction clone() {
        SQLAlterViewReadWriteAction x = new SQLAlterViewReadWriteAction();
        return x;
    }

}
