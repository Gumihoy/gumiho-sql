package com.gumihoy.sql.dialect.mysql.visitor;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.visitor.SQLASTVisitorAdapter;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLOJTableReference;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLSelectQuery;
import com.gumihoy.sql.dialect.mysql.ast.statement.dml.MySQLInsertStatement;

/**
 * @author kent on 2019-06-14.
 */
public class MySQLASTVisitorAdapter extends SQLASTVisitorAdapter implements IMySQLASTVisitor {


    @Override
    public void preVisit(ISQLObject x) {

    }

    @Override
    public void postVisit(ISQLObject x) {

    }


    // ---------------------------- DML Start ------------------------------------
//    boolean visit(SQLCallStatement x);
//    boolean visit(SQLDeleteStatement x);
//    boolean visit(SQLExplainPlanStatement x);

    @Override
    public boolean visit(MySQLInsertStatement x) {
        return true;
    }

//    boolean visit(SQLMultiInsertStatement x);
//    boolean visit(SQLLockTableStatement x);
//    boolean visit(SQLMergeStatement x);
//    boolean visit(SQLSelectStatement x);
//    boolean visit(SQLSelectIntoStatement x);
//    boolean visit(SQLUpdateStatement x);

    // ---------------------------- DML End ------------------------------------


    // ---------------------------- Select expr Start ------------------------------------

    @Override
    public boolean visit(MySQLSelectQuery x) {
        return true;
    }

    @Override
    public boolean visit(MySQLOJTableReference x) {
        return true;
    }


    // ---------------------------- Select expr End ------------------------------------

}
