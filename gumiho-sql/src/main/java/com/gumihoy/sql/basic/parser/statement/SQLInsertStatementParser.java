package com.gumihoy.sql.basic.parser.statement;

import static com.gumihoy.sql.basic.parser.SQLToken.TokenKind.COMMA;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.insert.ISQLInsertValuesClause;
import com.gumihoy.sql.basic.ast.expr.select.ISQLTableReference;
import com.gumihoy.sql.basic.ast.statement.ISQLStatement;
import com.gumihoy.sql.basic.ast.statement.dml.SQLInsertStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLInsertStatementParser extends AbstractSQLStatementParser {

    public SQLInsertStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public ISQLStatement parse() {

        acceptAndNextToken(SQLToken.TokenKind.INSERT, true);
        acceptAndNextToken(SQLToken.TokenKind.INTO, true);

        SQLInsertStatement x = new SQLInsertStatement(this.dbType);

        ISQLTableReference tableReference = exprParser.parseTableReference();
        x.setTableReference(tableReference);

        if (this.acceptAndNextToken(SQLToken.TokenKind.LPAREN)) {

            for (; ; ) {
                ISQLExpr column = exprParser.parseExpr();
                x.addColumn(column);
                if (this.acceptAndNextToken(SQLToken.TokenKind.COMMA)) {
                    break;
                }
                nextToken();
            }

            acceptAndNextToken(SQLToken.TokenKind.RPAREN, true);
        }

        ISQLInsertValuesClause valuesClause = exprParser.parseInsertValuesClause();
        x.setValuesClause(valuesClause);

        return x;
    }


}
