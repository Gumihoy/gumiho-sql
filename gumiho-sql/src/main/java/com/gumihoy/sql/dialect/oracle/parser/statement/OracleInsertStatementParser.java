package com.gumihoy.sql.dialect.oracle.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLReturningClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLErrorLoggingClause;
import com.gumihoy.sql.basic.ast.expr.insert.ISQLInsertValuesClause;
import com.gumihoy.sql.basic.ast.expr.select.ISQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLInsertStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLMultiInsertStatement;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.basic.parser.statement.SQLInsertStatementParser;
import com.gumihoy.sql.dialect.oracle.parser.OracleExprParser;

/**
 * @author kent on 2019-06-13.
 */
public class OracleInsertStatementParser extends SQLInsertStatementParser {


    public OracleInsertStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }

    @Override
    public ISQLStatement parse() {

        this.acceptAndNextToken(SQLToken.TokenKind.INSERT, true);

        if (this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {
            SQLInsertStatement x = new SQLInsertStatement(this.dbType);

            ISQLTableReference tableReference = exprParser.parseTableReference();
            x.setTableReference(tableReference);

            if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
                for (; ; ) {
                    ISQLExpr column = exprParser.parseExpr();
                    x.addColumn(column);
                    if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                        break;
                    }
                }
                this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
            }


            ISQLInsertValuesClause valuesClause = exprParser.parseInsertValuesClause();
            x.setValuesClause(valuesClause);

            ISQLReturningClause returningClause = exprParser.parseReturningClause();
            x.setReturningClause(returningClause);

            SQLErrorLoggingClause errorLoggingClause = exprParser.parseErrorLoggingClause();
            x.setErrorLoggingClause(errorLoggingClause);

            return x;
        } else {
            SQLMultiInsertStatement x = new SQLMultiInsertStatement(this.dbType);

            SQLMultiInsertStatement.SQLType type = null;
            if (this.acceptAndNextToken(SQLToken.TokenKind.ALL)) {
                type = SQLMultiInsertStatement.SQLType.ALL;

            } else if (this.acceptAndNextToken(SQLToken.TokenKind.FIRST)) {
                type = SQLMultiInsertStatement.SQLType.FIRST;

            }

            SQLMultiInsertStatement.ISQLInsertClause insertClause = null;
            if (this.accept(SQLToken.TokenKind.INTO)) {
                insertClause = parseAllInsertClause();

            } else if (this.accept(SQLToken.TokenKind.WHEN)) {
                insertClause = parseConditionalInsertClause(type);

            } else {
                throw new SQLParserException();

            }
            x.setInsertClause(insertClause);

            ISQLSelectQuery subQuery = exprParser.parseSelectQuery();
            x.setSubQuery(subQuery);

            return x;
        }
    }


    public SQLMultiInsertStatement.SQLAllInsertClause parseAllInsertClause() {
        if (!this.accept(SQLToken.TokenKind.INTO)) {
            return null;
        }

        SQLMultiInsertStatement.SQLAllInsertClause x = new SQLMultiInsertStatement.SQLAllInsertClause();
        for (;;) {
            SQLMultiInsertStatement.SQLInsertIntoClauseItem item = parseInsertIntoClauseItem();
            if (item == null) {
                break;
            }
            x.addItem(item);
        }
        return x;
    }


    public SQLMultiInsertStatement.SQLConditionalInsertClause parseConditionalInsertClause(SQLMultiInsertStatement.SQLType type) {
        if (!this.accept(SQLToken.TokenKind.WHEN)) {
            return null;
        }

        SQLMultiInsertStatement.SQLConditionalInsertClause x = new SQLMultiInsertStatement.SQLConditionalInsertClause();
        x.setType(type);
        for (;;) {
            SQLMultiInsertStatement.SQLConditionalInsertWhenClause whenClause = parseConditionalInsertWhenClause();
            if (whenClause == null) {
                break;
            }
            x.addWhenClause(whenClause);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ELSE)) {
            for (;;) {
                SQLMultiInsertStatement.SQLInsertIntoClauseItem elseItem = parseInsertIntoClauseItem();
                if (elseItem == null) {
                    break;
                }
                x.addElseItem(elseItem);
            }
        }

        return x;
    }

    public SQLMultiInsertStatement.SQLConditionalInsertWhenClause parseConditionalInsertWhenClause() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.WHEN)) {
            return null;
        }
        SQLMultiInsertStatement.SQLConditionalInsertWhenClause x = new SQLMultiInsertStatement.SQLConditionalInsertWhenClause();

        ISQLExpr condition = exprParser.parseExpr();
        x.setCondition(condition);

        this.acceptAndNextToken(SQLToken.TokenKind.THEN, true);

        for (;;) {
            SQLMultiInsertStatement.SQLInsertIntoClauseItem thenItem = parseInsertIntoClauseItem();
            if (thenItem == null) {
                break;
            }
            x.addThenItem(thenItem);
        }

        return x;
    }

    public SQLMultiInsertStatement.SQLInsertIntoClauseItem parseInsertIntoClauseItem() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.INTO)) {
            return null;
        }

        SQLMultiInsertStatement.SQLInsertIntoClauseItem x = new SQLMultiInsertStatement.SQLInsertIntoClauseItem();
        ISQLTableReference tableReference = exprParser.parseTableReference();
        x.setTableReference(tableReference);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {
            for (; ; ) {
                ISQLExpr column = exprParser.parseExpr();
                x.addColumn(column);
                if (!this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
            }
            this.acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        ISQLInsertValuesClause valuesClause = exprParser.parseInsertValuesClause();
        x.setValuesClause(valuesClause);

        SQLErrorLoggingClause errorLoggingClause = exprParser.parseErrorLoggingClause();
        x.setErrorLoggingClause(errorLoggingClause);
        return x;
    }

    @Override
    public OracleExprParser getExprParser() {
        return (OracleExprParser) exprParser;
    }
}
