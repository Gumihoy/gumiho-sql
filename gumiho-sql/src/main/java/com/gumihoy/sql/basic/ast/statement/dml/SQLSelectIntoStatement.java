package com.gumihoy.sql.basic.ast.statement.dml;

import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * SELECT [ <set quantifier> ] <select list> [BULK COLLECT] INTO <select target list> <table expression>
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#select%20statement:%20single%20row
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/SELECT-INTO-statement.html#GUID-6E14E04D-4344-45F3-BE80-979DD26C7A90
 *
 * @author kent on 2018/5/2.
 */
public class SQLSelectIntoStatement extends AbstractSQLStatement implements ISQLDMLStatement {


    public SQLSelectIntoStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {

        }
    }

    @Override
    public SQLSelectIntoStatement clone() {
        SQLSelectIntoStatement x = new SQLSelectIntoStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLSelectIntoStatement x) {
        super.cloneTo(x);

    }

    @Override
    public SQLObjectType getObjectType() {
        return null;// SQLObjectType.SELECT_INTO;
    }

}
