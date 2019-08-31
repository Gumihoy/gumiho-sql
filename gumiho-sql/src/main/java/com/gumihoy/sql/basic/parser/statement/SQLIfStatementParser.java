package com.gumihoy.sql.basic.parser.statement;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLLabelStatement;
import com.gumihoy.sql.basic.ast.statement.fcl.SQLIfStatement;
import com.gumihoy.sql.basic.parser.AbstractSQLStatementParser;
import com.gumihoy.sql.basic.parser.SQLExprParser;
import com.gumihoy.sql.basic.parser.SQLToken;

/**
 * @author kent on 2019-06-25.
 */
public class SQLIfStatementParser extends AbstractSQLStatementParser {

    public SQLIfStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }


    public SQLIfStatement parse() {
        this.acceptAndNextToken(SQLToken.TokenKind.IF, true);

        SQLIfStatement x = new SQLIfStatement(this.dbType);

        ISQLExpr condition = exprParser.parseExpr();
        x.setCondition(condition);

        this.acceptAndNextToken(SQLToken.TokenKind.THEN, true);
        for (;;) {
            SQLLabelStatement statement = exprParser.parseLabelStatement();
            if (statement == null) {
                break;
            }
            x.addStatement(statement);
        }


        for (;;) {
            SQLIfStatement.SQLElseIf elseIf = parseElseIf();
            if (elseIf == null) {
                break;
            }
            x.addElseIf(elseIf);
        }

        if (this.acceptAndNextToken(SQLToken.TokenKind.ELSE)) {
            for (;;) {
                SQLLabelStatement statement = exprParser.parseLabelStatement();
                if (statement == null) {
                    break;
                }
                x.addElseStatement(statement);
            }
        }

        this.acceptAndNextToken(SQLToken.TokenKind.END, true);
        this.acceptAndNextToken(SQLToken.TokenKind.IF, true);
        return x;
    }



    public SQLIfStatement.SQLElseIf parseElseIf() {
        if (!this.acceptAndNextToken(SQLToken.TokenKind.ELSEIF)) {
            return null;
        }

        SQLIfStatement.SQLElseIf x = new SQLIfStatement.SQLElseIf();

        ISQLExpr condition = exprParser.parseExpr();
        x.setCondition(condition);

        this.acceptAndNextToken(SQLToken.TokenKind.THEN, true);
        for (;;) {
            SQLLabelStatement statement = exprParser.parseLabelStatement();
            if (statement == null) {
                break;
            }
            x.addStatement(statement);
        }
        return x;
    }

}
