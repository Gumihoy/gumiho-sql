package com.gumihoy.sql.dialect.mysql.visitor;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLOJTableReference;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLSelectQuery;
import com.gumihoy.sql.dialect.mysql.ast.statement.dml.MySQLInsertStatement;

/**
 * @author kent on 2019-06-14.
 */
public interface IMySQLASTVisitor extends ISQLASTVisitor {


    void preVisit(ISQLObject x);

    void postVisit(ISQLObject x);


    // ---------------------------- DML Start ------------------------------------
//    boolean visit(SQLCallStatement x);
//    boolean visit(SQLDeleteStatement x);
//    boolean visit(SQLExplainPlanStatement x);
    boolean visit(MySQLInsertStatement x);
//    boolean visit(SQLMultiInsertStatement x);
//    boolean visit(SQLLockTableStatement x);
//    boolean visit(SQLMergeStatement x);
//    boolean visit(SQLSelectStatement x);
//    boolean visit(SQLSelectIntoStatement x);
//    boolean visit(SQLUpdateStatement x);

    // ---------------------------- DML End ------------------------------------


    // ---------------------------- Select expr Start ------------------------------------
    boolean visit(MySQLSelectQuery x);

    boolean visit(MySQLOJTableReference x);
    // ---------------------------- Select expr End ------------------------------------
}
