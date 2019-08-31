package com.gumihoy.sql.dialect.gpdb.visitor;

import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.basic.visitor.SQLASTOutputVisitor;
import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLOJTableReference;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLSelectQuery;
import com.gumihoy.sql.dialect.mysql.ast.statement.dml.MySQLInsertStatement;

/**
 * @author kent on 2019-06-14.
 */
public class GPDBASTOutputVisitor extends SQLASTOutputVisitor implements IGPDBASTVisitor {

    public GPDBASTOutputVisitor(StringBuilder appender) {
        super(appender);
    }

    public GPDBASTOutputVisitor(StringBuilder appender, SQLOutputConfig config) {
        super(appender, config);
    }

    // ---------------------------- DML Start ------------------------------------
//    boolean visit(SQLCallStatement x);
//    boolean visit(SQLDeleteStatement x);
//    boolean visit(SQLExplainPlanStatement x);

    @Override
    public boolean visit(MySQLInsertStatement x) {

        return false;
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
        print(SQLKeyWord.SELECT);

        printSpaceAfterValue(x.getSetQuantifier());

        if (x.isHighPriority()) {
            printSpaceAfterValue(SQLKeyWord.HIGH_PRIORITY);
        }

        if (x.isStraightJoin()) {
            printSpaceAfterValue(SQLKeyWord.STRAIGHT_JOIN);
        }

        if (x.isSmallResult()) {
            printSpaceAfterValue(SQLKeyWord.SQL_SMALL_RESULT);
        }

        if (x.isBigResult()) {
            printSpaceAfterValue(SQLKeyWord.SQL_BIG_RESULT);
        }

        if (x.isBufferResult()) {
            printSpaceAfterValue(SQLKeyWord.SQL_BUFFER_RESULT);
        }

        printSpaceAfterValue(x.getCache());

        if (x.isCalcFoundRows()) {
            printSpaceAfterValue(SQLKeyWord.SQL_CALC_FOUND_ROWS);
        }

        printSpace();
        printSelectItems(x.getSelectItems());

        printlnAndAccept(x.getFromClause());

        printlnAndAccept(x.getPartitionExtensionClause());

        printlnAndAccept(x.getWhereClause());

        printlnAndAccept(x.getGroupByClause());

        printlnAndAccept(x.getWindowClause());

        printlnAndAccept(x.getOrderByClause());

        printlnAndAccept(x.getLimitClause());

        printlnAndAccept(x.getLockClause());
        return false;
    }

    @Override
    public boolean visit(MySQLOJTableReference x) {
        print('{');
        printSpaceAfterValue(SQLKeyWord.OJ);
        printSpaceAfterAccept(x.getTableReference());
        printSpaceAfterValue('}');
        return false;
    }

    // ---------------------------- Select expr End ------------------------------------

}
