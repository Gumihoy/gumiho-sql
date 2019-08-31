package com.gumihoy.sql.dialect.gpdb.parser;

import com.gumihoy.sql.basic.ast.enums.SQLBinaryOperator;
import com.gumihoy.sql.basic.ast.enums.SQLSetQuantifier;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLPartitionExtensionClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLAssignmentExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLFromClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLSetClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWhereClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLWindowClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.insert.ISQLInsertValuesClause;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLJoinTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLObjectNameTableReference;
import com.gumihoy.sql.basic.ast.expr.select.SQLParenSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.group.SQLGroupByClause;
import com.gumihoy.sql.basic.ast.expr.select.limit.ISQLLimitClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLDeleteStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLInsertStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLUpdateStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLDatabaseStatementParser;
import com.gumihoy.sql.basic.parser.statement.SQLFunctionStatementParser;
import com.gumihoy.sql.basic.parser.statement.SQLIndexStatementParser;
import com.gumihoy.sql.basic.parser.statement.SQLSchemaStatementParser;
import com.gumihoy.sql.basic.parser.statement.SQLTableStatementParser;
import com.gumihoy.sql.config.SQLParseConfig;
import com.gumihoy.sql.dialect.gpdb.parser.statement.GPDBDatabaseStatementParser;
import com.gumihoy.sql.dialect.gpdb.parser.statement.GPDBIndexStatementParser;
import com.gumihoy.sql.dialect.gpdb.parser.statement.GPDBSchemaStatementParser;
import com.gumihoy.sql.dialect.gpdb.parser.statement.GPDBTableStatementParser;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLOJTableReference;
import com.gumihoy.sql.dialect.mysql.ast.expr.select.MySQLSelectQuery;
import com.gumihoy.sql.dialect.mysql.parser.statement.MySQLDatabaseStatementParser;
import com.gumihoy.sql.dialect.mysql.parser.statement.MySQLIndexStatementParser;
import com.gumihoy.sql.dialect.mysql.parser.statement.MySQLSchemaStatementParser;
import com.gumihoy.sql.dialect.mysql.parser.statement.MySQLTableStatementParser;
import com.gumihoy.sql.enums.DBType;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kent on 2019-06-15.
 */
public class GPDBExprParser extends SQLExprParser {

    public GPDBExprParser(String sql) {
        this(new GPDBLexer(sql));
    }

    public GPDBExprParser(String sql, SQLParseConfig config) {
        this(new GPDBLexer(sql, config), config);
    }

    public GPDBExprParser(SQLLexer lexer) {
        super(lexer);
    }

    public GPDBExprParser(SQLLexer lexer, SQLParseConfig config) {
        super(lexer, config);
    }

    {
        dbType = DBType.GPDB;
    }

    protected static final Set<Long> GPDB_NO_ARGUMENT_FUNCTION = new HashSet<>();
    {
        GPDB_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.CURRENT_DATE.lowerHash);
        GPDB_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.CURRENT_TIME.lowerHash);
        GPDB_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.CURRENT_TIMESTAMP.lowerHash);
        GPDB_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.CURRENT_USER.lowerHash);
        GPDB_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.LOCALTIME.lowerHash);
        GPDB_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.LOCALTIMESTAMP.lowerHash);
        GPDB_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.UTC_DATE.lowerHash);
        GPDB_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.UTC_TIME.lowerHash);
        GPDB_NO_ARGUMENT_FUNCTION.add(SQLKeyWord.UTC_TIMESTAMP.lowerHash);

        noArgumentFunctionLowerHash = GPDB_NO_ARGUMENT_FUNCTION;
    }


    @Override
    protected SQLDatabaseStatementParser createDatabaseStatementParser() {
        return new GPDBDatabaseStatementParser(this);
    }

    @Override
    protected SQLFunctionStatementParser createFunctionStatementParser() {
        return super.createFunctionStatementParser();
    }

    @Override
    protected SQLIndexStatementParser createIndexStatementParser() {
        return new GPDBIndexStatementParser(this);
    }

    @Override
    protected SQLSchemaStatementParser createSchemaStatementParser() {
        return new GPDBSchemaStatementParser(this);
    }

    @Override
    protected SQLTableStatementParser createTableStatementParser() {
        return new GPDBTableStatementParser(this);
    }

    @Override
    public SQLDeleteStatement parseDeleteStatement() {
        acceptAndNextToken(SQLToken.TokenKind.DELETE, true);

        SQLDeleteStatement x = new SQLDeleteStatement(this.dbType);

        if (lexer.token().kind == SQLToken.TokenKind.LOW_PRIORITY
                || lexer.lowerHash() == SQLKeyWord.LOW_PRIORITY.lowerHash) {
            nextToken();
            x.setLowPriority(true);
        }

        if (lexer.token().kind == SQLToken.TokenKind.QUICK
                || lexer.lowerHash() == SQLKeyWord.QUICK.lowerHash) {
            nextToken();
            x.setQuick(true);
        }

        if (lexer.token().kind == SQLToken.TokenKind.IGNORE
                || lexer.lowerHash() == SQLKeyWord.IGNORE.lowerHash) {
            nextToken();
            x.setIgnore(true);
        }

        ISQLTableReference multipleTableReference = null;

        for (; ; ) {
            ISQLName name = parseName();
            if (name == null) {
                break;
            }
            SQLObjectNameTableReference right = new SQLObjectNameTableReference(name);
            if (multipleTableReference == null) {
                multipleTableReference = right;
            } else {
                multipleTableReference = new SQLJoinTableReference(multipleTableReference, SQLJoinTableReference.SQLJoinType.COMMA, right);
            }
            if (lexer.token().kind != SQLToken.TokenKind.COMMA) {
                break;
            }
            nextToken();
        }

        x.setMultipleTableReference(multipleTableReference);

        acceptAndNextToken(SQLToken.TokenKind.FROM, true);


        ISQLTableReference tableReference = null;
        if (multipleTableReference != null) {
            tableReference = parseTableReference();
            x.setTableReference(tableReference);
        } else {
            for (; ; ) {
                ISQLName name = parseName();
                SQLObjectNameTableReference right = new SQLObjectNameTableReference(name);
                if (tableReference == null) {
                    tableReference = right;
                } else {
                    tableReference = new SQLJoinTableReference(tableReference, SQLJoinTableReference.SQLJoinType.COMMA, right);
                }
                if (lexer.token().kind != SQLToken.TokenKind.COMMA) {
                    break;
                }
                nextToken();
            }
        }
        x.setTableReference(tableReference);


        ISQLPartitionExtensionClause partitionExtensionClause = parsePartitionExtensionClause();
        x.setPartitionExtensionClause(partitionExtensionClause);

        if (lexer.token().kind == SQLToken.TokenKind.USING) {
            nextToken();
            ISQLTableReference usingTableReference = parseTableReference();
            x.setUsingTableReference(usingTableReference);
        }

        SQLWhereClause whereClause = parseWhere();
        x.setWhereClause(whereClause);

        SQLOrderByClause orderByClause = parseOrderBy();
        x.setOrderByClause(orderByClause);

        ISQLLimitClause limitClause = parseLimit();
        x.setLimitClause(limitClause);

        return x;
    }

    @Override
    public ISQLStatement parseExplainStatement() {
        return super.parseExplainStatement();
    }

    public ISQLStatement parseExplainableStmt() {
        ISQLStatement x = null;
        switch (lexer.token().kind) {
            case SELECT:
                x = parseSelectStatement();
                break;
            case DELETE:
                x = parseDeleteStatement();
                break;
            case INSERT:
                x = parseInsertStatement();
                break;
            case REPLACE:
//                x = parseSelectStatement();
                break;
            case UPDATE:
                x = parseUpdateStatement();
                break;
            default:
                throw new SQLParserException();
        }

        return x;
    }


    @Override
    public ISQLStatement parseInsertStatement() {
        acceptAndNextToken(SQLToken.TokenKind.INSERT, true);

        SQLInsertStatement x = new SQLInsertStatement(this.dbType);

        SQLInsertStatement.SQLPriority priority = null;
        if (lexer.token().kind == SQLToken.TokenKind.LOW_PRIORITY
                || lexer.lowerHash() == SQLKeyWord.LOW_PRIORITY.lowerHash) {
            nextToken();

            priority = SQLInsertStatement.SQLPriority.LOW_PRIORITY;

        } else if (lexer.token().kind == SQLToken.TokenKind.DELAYED
                || lexer.lowerHash() == SQLKeyWord.DELAYED.lowerHash) {
            nextToken();

            priority = SQLInsertStatement.SQLPriority.DELAYED;

        } else if (lexer.token().kind == SQLToken.TokenKind.HIGH_PRIORITY
                || lexer.lowerHash() == SQLKeyWord.HIGH_PRIORITY.lowerHash) {
            nextToken();

            priority = SQLInsertStatement.SQLPriority.HIGH_PRIORITY;

        }
        x.setPriority(priority);

        if (lexer.token().kind == SQLToken.TokenKind.IGNORE
                || lexer.lowerHash() == SQLKeyWord.IGNORE.lowerHash) {
            nextToken();
            x.setIgnore(true);
        }

        boolean into = acceptAndNextToken(SQLToken.TokenKind.INTO);
        x.setInto(into);

        ISQLName name = parseName();
        SQLObjectNameTableReference tableReference = new SQLObjectNameTableReference(name);
        x.setTableReference(tableReference);

        ISQLPartitionExtensionClause partitionExtensionClause = parsePartitionExtensionClause();
        x.setPartitionExtensionClause(partitionExtensionClause);

        if (lexer.token().kind == SQLToken.TokenKind.LPAREN) {
            nextToken();

            for (; ; ) {
                ISQLExpr column = parseExpr();
                x.addColumn(column);
                if (lexer.token().kind != SQLToken.TokenKind.COMMA) {
                    break;
                }
                nextToken();
            }

            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        ISQLInsertValuesClause valuesClause = parseInsertValuesClause();
        x.setValuesClause(valuesClause);

        // ON DUPLICATE KEY UPDATE assignment_list
        if (lexer.token().kind == SQLToken.TokenKind.ON
                || lexer.lowerHash() == SQLKeyWord.ON.lowerHash) {
            nextToken();

            if (lexer.token().kind == SQLToken.TokenKind.DUPLICATE
                    || lexer.lowerHash() == SQLKeyWord.DUPLICATE.lowerHash) {
                nextToken();

                if (lexer.token().kind == SQLToken.TokenKind.KEY
                        || lexer.lowerHash() == SQLKeyWord.KEY.lowerHash) {
                    nextToken();

                    if (lexer.token().kind == SQLToken.TokenKind.UPDATE
                            || lexer.lowerHash() == SQLKeyWord.UPDATE.lowerHash) {
                        nextToken();

                        for (; ; ) {
                            SQLAssignmentExpr item = (SQLAssignmentExpr) parseAssignmentOperatorExpr();
                            x.addOnDuplicateKeyUpdateAssignment(item);
                            if (lexer.token().kind != SQLToken.TokenKind.COMMA) {
                                break;
                            }
                            nextToken();
                        }
                    } else {
                        throw new SQLParserException();
                    }

                } else {
                    throw new SQLParserException();
                }

            } else {
                throw new SQLParserException();
            }
        }


        return x;
    }

    @Override
    public ISQLInsertValuesClause parseInsertValuesClause() {
        ISQLInsertValuesClause x = null;

        if ((lexer.token().kind == SQLToken.TokenKind.VALUES
                || lexer.lowerHash() == SQLKeyWord.VALUES.lowerHash)
                || (lexer.token().kind == SQLToken.TokenKind.VALUE
                || lexer.lowerHash() == SQLKeyWord.VALUE.lowerHash)) {

            x = parseValuesClause();

        } else if (lexer.token().kind == SQLToken.TokenKind.SET
                || lexer.lowerHash() == SQLKeyWord.SET.lowerHash) {

            x = parseSetClause();

        } else if (lexer.token().kind == SQLToken.TokenKind.SELECT) {

            x = parseSelectQueryExpr();

        }

        return x;

    }

    @Override
    public ISQLStatement parseLockStatement() {
        return super.parseLockStatement();
    }


    @Override
    public SQLUpdateStatement parseUpdateStatement() {
        acceptAndNextToken(SQLToken.TokenKind.UPDATE, true);

        SQLUpdateStatement x = new SQLUpdateStatement(this.dbType);

        if (lexer.token().kind == SQLToken.TokenKind.LOW_PRIORITY
                || lexer.lowerHash() == SQLKeyWord.LOW_PRIORITY.lowerHash) {
            nextToken();

            x.setLowPriority(true);
        }

        if (lexer.token().kind == SQLToken.TokenKind.IGNORE
                || lexer.lowerHash() == SQLKeyWord.IGNORE.lowerHash) {
            nextToken();

            x.setIgnore(true);
        }

        ISQLTableReference tableReference = parseTableReference();
        x.setTableReference(tableReference);

        SQLSetClause setClause = parseSetClause();
        x.setUpdateSetClause(setClause);

        SQLWhereClause whereClause = parseWhere();
        x.setWhereClause(whereClause);

        SQLOrderByClause orderByClause = parseOrderBy();
        x.setOrderByClause(orderByClause);

        ISQLLimitClause limitClause = parseLimit();
        x.setLimitClause(limitClause);


        return x;
    }

    /**
     * E: T OR  T OR T
     * T: X XOR X
     * OR、||
     */
    @Override
    public ISQLExpr parseOrOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        switch (token.kind) {
            case OR:
                nextToken();
                ISQLExpr right = parseXorOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.OR, right);
                expr = parseOrOperatorExpr(expr);
                break;
            case BARBAR:
                nextToken();
                right = parseXorOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.LOGIC_OR, right);
                expr = parseOrOperatorExpr(expr);
                break;
            default:
        }
        return expr;
    }

    /**
     * E: T (+/-/||) T (+/-/||) T ...
     * T: X (*, /, DIV, %, MOD) X
     * <p>
     * +, -
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    @Override
    public ISQLExpr parseAdditiveOperatorExpr(ISQLExpr expr) {
        SQLToken token = token();
        switch (token.kind) {
            case PLUS:
                nextToken();
                ISQLExpr right = parseMultiplicativeOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.Add, right);
                expr = parseAdditiveOperatorExpr(expr);
                break;
            case SUB:
                nextToken();
                right = parseMultiplicativeOperatorExpr();
                expr = new SQLBinaryOperatorExpr(expr, SQLBinaryOperator.Sub, right);
                expr = parseAdditiveOperatorExpr(expr);
                break;
            default:

        }
        return expr;
    }


    /**
     * column (= | :=) expr
     * <p>
     * https://dev.mysql.com/doc/refman/8.0/en/operator-precedence.html
     */
    @Override
    public ISQLExpr parseAssignmentOperatorExpr() {
        ISQLExpr expr = parseName();
        expr = parseAssignmentOperatorExpr(expr);
        return expr;
    }

    @Override
    public ISQLSelectQuery parseQueryBlockPrimary() {
        if (lexer.token().kind == SQLToken.TokenKind.LPAREN) {
            nextToken();

            ISQLSelectQuery selectQuery = parseSelectQuery();
            SQLParenSelectQuery query = new SQLParenSelectQuery(selectQuery);
            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            return query;
        }

        MySQLSelectQuery query = new MySQLSelectQuery();

        boolean accept = acceptAndNextToken(SQLToken.TokenKind.SELECT);
        if (!accept) {
            return null;
        }

        SQLSetQuantifier setQuantifier = parseSetQuantifier();
        query.setSetQuantifier(setQuantifier);

        if (lexer.token().kind == SQLToken.TokenKind.HIGH_PRIORITY
                || lexer.lowerHash() == SQLKeyWord.HIGH_PRIORITY.lowerHash) {
            nextToken();
            query.setHighPriority(true);
        }
        if (lexer.token().kind == SQLToken.TokenKind.STRAIGHT_JOIN
                || lexer.lowerHash() == SQLKeyWord.STRAIGHT_JOIN.lowerHash) {
            nextToken();
            query.setStraightJoin(true);
        }
        if (lexer.token().kind == SQLToken.TokenKind.SQL_BUFFER_RESULT
                || lexer.lowerHash() == SQLKeyWord.SQL_BUFFER_RESULT.lowerHash) {
            nextToken();
            query.setBufferResult(true);
        }

        if (lexer.token().kind == SQLToken.TokenKind.SQL_CACHE
                || lexer.lowerHash() == SQLKeyWord.SQL_CACHE.lowerHash) {
            nextToken();
            query.setCache(MySQLSelectQuery.SQLCache.SQL_CACHE);
        } else if (lexer.token().kind == SQLToken.TokenKind.SQL_NO_CACHE
                || lexer.lowerHash() == SQLKeyWord.SQL_NO_CACHE.lowerHash) {
            nextToken();
            query.setCache(MySQLSelectQuery.SQLCache.SQL_NO_CACHE);
        }

        if (lexer.token().kind == SQLToken.TokenKind.SQL_CALC_FOUND_ROWS
                || lexer.lowerHash() == SQLKeyWord.SQL_CALC_FOUND_ROWS.lowerHash) {
            nextToken();
            query.setCalcFoundRows(true);
        }


        parseSelectItems(query);
        parseSelectTargetItems(query);

        SQLFromClause fromClause = parseFrom();
        query.setFromClause(fromClause);

        ISQLPartitionExtensionClause partitionExtensionClause = parsePartitionExtensionClause();
        query.setPartitionExtensionClause(partitionExtensionClause);

        SQLWhereClause whereClause = parseWhere();
        query.setWhereClause(whereClause);

        SQLGroupByClause groupByClause = parseGroupBy();
        query.setGroupByClause(groupByClause);

        SQLWindowClause windowClause = parseWindow();
        query.setWindowClause(windowClause);

        return query;
    }

    @Override
    public ISQLTableReference parseTableReferencePrimary() {

        ISQLTableReference tableReference = null;
        if (lexer.token().kind == SQLToken.TokenKind.LBRACE) {
            nextToken();

            if (lexer.token().kind == SQLToken.TokenKind.OJ
                    || lexer.lowerHash() == SQLKeyWord.OJ.lowerHash) {

                nextToken();
            } else {
                throw new SQLParserException("");
            }

            ISQLTableReference ojTableReference = super.parseTableReference();

            acceptAndNextToken(SQLToken.TokenKind.RBRACE, true);

            tableReference = new MySQLOJTableReference(ojTableReference);

        } else {
            tableReference = super.parseTableReferencePrimary();
        }

        return tableReference;
    }


}
